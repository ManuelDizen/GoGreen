package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Seller;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface SellerService {

    Seller create(long userid, String phone, String address, long areaId);
    Optional<Seller> findById(long id);
    Optional<Seller> findByUserId(long userId);
    Optional<Seller> findByMail(String mail);
    List<Seller> findByName(String name);
    Optional<Seller> findByPhone(String phone);
    List<Seller> getAll();
    String getEmail(long userid);
    public String getName(long userid);
    public String getSurname(long userid);

    Locale getLocale(long userid);

}
