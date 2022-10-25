package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ArticleDao;
import ar.edu.itba.paw.interfaces.services.ArticleService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ImageService imageService;
    private final ArticleDao articleDao;

    @Autowired
    public ArticleServiceImpl(final ImageService imageService, final ArticleDao articleDao) {
        this.imageService = imageService;
        this.articleDao = articleDao;
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

    @Transactional
    @Override
    public void edit(Long id, String newMessage, byte[] newImage) {
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
