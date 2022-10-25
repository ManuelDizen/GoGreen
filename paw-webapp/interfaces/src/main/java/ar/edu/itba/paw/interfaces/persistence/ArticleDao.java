package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Seller;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ArticleDao {
    Article create(Seller seller, String message, Image image, LocalDateTime dateTime) ;
    void delete(Long id);
    Optional<Article> getById(Long id);
}
