package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Seller;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface SellerDao {

    Seller create(long userid, String phone, String address, long areaId);
    Optional<Seller> findById(long id);
    Optional<Seller> findByUserId(long userId);
    Optional<Seller> findByMail(String mail);
    List<Seller> findByName(String name);
    Optional<Seller> findByPhone(String phone);
    List<Seller> getAll();

}
