package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Area;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Seller;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface SellerService {

    Seller create(long userid, String phone, String address, Area area);
    Optional<Seller> findById(long id);
    Optional<Seller> findByUserId(long userId);
    Optional<Seller> findByMail(String mail);
    List<Seller> getAll();
    String getEmail(long userid);
    String getName(long userid);
    String getSurname(long userid);

    Locale getLocale(long userid);
    void registerSeller(String firstName, String surname,
                                  String email, String password, Locale locale, String phone,
                                  String address, Area area);
    Pagination<Seller> filter(String name, long areaId, boolean favorite, int page,
                              int sort, int direction);
}
