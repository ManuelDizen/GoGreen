package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ArticleDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ImageService imageService;
    private final ArticleDao articleDao;
    private final UserService userService;
    private final SellerService sellerService;
    private final FavoriteService favoriteService;
    private final EmailService emailService;

    @Autowired
    public ArticleServiceImpl(final ImageService imageService, final ArticleDao articleDao,
                              final UserService userService, final SellerService sellerService,
                              final FavoriteService favoriteService, final EmailService emailService) {
        this.imageService = imageService;
        this.articleDao = articleDao;
        this.userService = userService;
        this.sellerService = sellerService;
        this.favoriteService = favoriteService;
        this.emailService = emailService;
    }

    @Override
    public Image parseByteArrayToImage(byte[] image){
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
    public Article create(String message, byte[] image, LocalDateTime dateTime) {

        User logged = userService.getLoggedUser();
        if(logged == null) throw new UnauthorizedRoleException();
        Optional<Seller> maybeSeller = sellerService.findByUserId(logged.getId());
        if(!maybeSeller.isPresent()) throw new UserNotFoundException();
        Seller seller = maybeSeller.get();

        Image img = parseByteArrayToImage(image);
        Article article = articleDao.create(seller, message, img, dateTime);
        if(article == null) throw new ArticleCreationException();

        List<User> subscribed = favoriteService.getSubscribedUsers(seller);
        emailService.newArticleFromSeller(seller, subscribed, message);
        return article;
    }

    @Transactional
    @Override
    public Optional<Article> getById(Long id){
        return articleDao.getById(id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        articleDao.delete(id);
    }

    @Override
    public Pagination<Article> getBySellerId(Long sellerId, int page) {
        return articleDao.getBySellerId(sellerId, page);
    }

    @Transactional
    @Override
    public Pagination<Article> getForLoggedUser(int page) {
        User user = userService.getLoggedUser();
        if(user == null) throw new UnauthorizedRoleException();
        return articleDao.getForUser(user.getId(), page);
    }
}
