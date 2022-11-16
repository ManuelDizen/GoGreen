package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Seller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Image parseByteArrayToImage(byte[] image);
    Article create(String message, byte[] image, LocalDateTime dateTime);
    void delete(Long id);
    Pagination<Article> getBySellerId(Long sellerId, int page);
    Pagination<Article> getForLoggedUser(int page);
    Optional<Article> getById(Long id);
}
