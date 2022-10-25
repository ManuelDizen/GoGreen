package ar.edu.itba.paw.interfaces.persistence;

public interface ArticleDao {
    void create(Long sellerId, String message, Long imageId);
    void edit(Long id, String newMessage, Long newImageId);
    void delete(Long id);
}
