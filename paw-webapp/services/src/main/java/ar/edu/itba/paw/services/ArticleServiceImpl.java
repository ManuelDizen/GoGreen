package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ArticleDao;
import ar.edu.itba.paw.interfaces.services.ArticleService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.SecurityService;
import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.ForbiddenActionException;
import ar.edu.itba.paw.models.exceptions.UnauthorizedRoleException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ImageService imageService;
    private final ArticleDao articleDao;
    private final SecurityService securityService;
    private final SellerService sellerService;

    @Autowired
    public ArticleServiceImpl(final ImageService imageService, final ArticleDao articleDao,
                              final SecurityService securityService, final SellerService sellerService) {
        this.imageService = imageService;
        this.articleDao = articleDao;
        this.securityService = securityService;
        this.sellerService = sellerService;
    }

    private Image parseByteArrayToImage(byte[] image){
        Image img = null;
        if (image != null && image.length > 0) {
            img = imageService.create(image);
        } else {
            Optional<Image> maybeImg = imageService.getById(0);
            if (maybeImg.isPresent()) img = maybeImg.get();
        }
        return img;
    }

    @Transactional
    @Override
    public Article create(Seller seller, String message, byte[] image, LocalDateTime dateTime) {
        Image img = parseByteArrayToImage(image);
        return articleDao.create(seller, message, img, dateTime);
    }

    @Transactional
    @Override
    public Optional<Article> getById(Long id){
        return articleDao.getById(id);
    }

    public Boolean checkForArticleOwnership(long id) {
        User user = securityService.getLoggedUser();
        if(user == null) throw new UnauthorizedRoleException();
        Optional<Seller> maybeSeller = sellerService.findByUserId(user.getId());
        if(!maybeSeller.isPresent()) throw new UserNotFoundException();
        Seller seller = maybeSeller.get();
        Optional<Article> maybeArticle = getById(id);
        if(maybeArticle.isPresent()){
            Article article = maybeArticle.get();
            return article.getSeller().getId().equals(seller.getId());
        }
        return false;
    }
    @Transactional
    @Override
    public void edit(Long id, String newMessage, byte[] newImage) {
        if(!checkForArticleOwnership(id)){throw new ForbiddenActionException();}

        Optional<Article> maybeArticle = articleDao.getById(id);
        if(!maybeArticle.isPresent()) throw new ArticleNotFoundException();
        Article article = maybeArticle.get();

        article.setMessage(newMessage);
        article.setImage(parseByteArrayToImage(newImage));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        // Unlike deleting products, this will produce a physical deletion
        articleDao.delete(id);
    }
}
