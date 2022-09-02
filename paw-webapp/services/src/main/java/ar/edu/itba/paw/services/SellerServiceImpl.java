package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.SellerDao;
import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.models.Seller;

import java.util.List;
import java.util.Optional;

public class SellerServiceImpl implements SellerService {
    private final SellerDao sd;

    public SellerServiceImpl(final SellerDao sd) {
        this.sd = sd;

    }

    @Override
    public Seller create(String mail, String phone, String address, String name) {
        return sd.create(mail, phone, address, name);
    }

    @Override
    public Optional<Seller> findById(long id) {
        return sd.findById(id);
    }

    @Override
    public Optional<Seller> findByMail(String mail) {
        return sd.findByMail(mail);
    }

    @Override
    public List<Seller> findByName(String name) {
        return sd.findByName(name);
    }

    @Override
    public Optional<Seller> findByPhone(String phone) {
        return sd.findByPhone(phone);
    }

    @Override
    public List<Seller> getAll() {
        return sd.getAll();
    }
}
