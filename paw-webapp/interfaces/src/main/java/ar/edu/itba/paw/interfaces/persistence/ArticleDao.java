package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Seller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleDao {
    Article create(Seller seller, String message, Image image, LocalDateTime dateTime) ;
    void delete(Long id);
    Optional<Article> getById(Long id);
    List<Article> getBySellerId(Long sellerId);
    Pagination<Article> getBySellerId(Long sellerId, int page);
    Pagination<Article> getForUser(long userId, int page);
}
