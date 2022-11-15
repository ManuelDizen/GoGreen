package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Seller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Image parseByteArrayToImage(byte[] image);
    Article create(String message, byte[] image, LocalDateTime dateTime);
    void edit(Long id, String newMessage, byte[] newImage);
    void delete(Long id);

    List<Article> getBySellerId(Long sellerId);
    List<Article> getForLoggedUser();
    Optional<Article> getById(Long id);
}
