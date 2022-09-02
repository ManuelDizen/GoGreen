package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerDao {

    Seller create(long id, String name, String mail, String phone, String address);
    Optional<Seller> findById(long id);
    Optional<Seller> findByMail(String mail);
    Optional<List<Seller>> findByName(String name);
    Optional<Seller> findByPhone(String phone);
    List<Seller> getAll();

}
