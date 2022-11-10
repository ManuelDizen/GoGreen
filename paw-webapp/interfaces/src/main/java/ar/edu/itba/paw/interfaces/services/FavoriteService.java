package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface FavoriteService {
    void toggleFavorite(long sellerId, boolean add);
    List<Favorite> getByUserId(long userId);
    boolean isFavorite(User user, Seller seller);
    boolean isFavorite(Seller seller);

    List<Seller> getFavoriteSellersByUserId(long userId);
    List<User> getSubscribedUsers(Seller seller);

    List<Long> getFavIdsByUser(User user);
}
