package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {

    Seller create(String mail, String phone, String address, String name);
    Optional<Seller> findById(long id);
    Optional<Seller> findByMail(String mail);
    List<Seller> findByName(String name);
    Optional<Seller> findByPhone(String phone);
    List<Seller> getAll();

}
