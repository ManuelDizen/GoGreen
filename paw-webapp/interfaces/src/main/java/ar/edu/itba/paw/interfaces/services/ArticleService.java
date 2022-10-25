package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Seller;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ArticleService {
    Article create(Seller seller, String message, byte[] image, LocalDateTime dateTime);
    void edit(Long id, String newMessage, byte[] newImage);
    void delete(Long id);

    Optional<Article> getById(Long id);
}
