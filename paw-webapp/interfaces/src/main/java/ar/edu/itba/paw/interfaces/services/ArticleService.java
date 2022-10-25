package ar.edu.itba.paw.interfaces.services;

public interface ArticleService {
    void create(Long sellerId, String message, Long imageId);
    void edit(Long id, String newMessage, Long newImageId);
    void delete(Long id);
}
