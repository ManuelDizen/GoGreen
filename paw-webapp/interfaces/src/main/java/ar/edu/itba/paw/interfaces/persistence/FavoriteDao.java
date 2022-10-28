package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface FavoriteDao {
    void addFavorite(User user, Seller seller);
    void removeFavorite(User user, Seller seller);
    List<Favorite> getByUserId(long userId);

}
