package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Area;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface SellerDao {

    Seller create(User user, String phone, String address, Area area);

    Optional<Seller> findById(long id);
    Optional<Seller> findByUserId(long userId);
    Optional<Seller> findByMail(String mail);
    List<Seller> getAll();
    Pagination<Seller> filter(String name, Area area, boolean favorite, int page, long userId);
}
