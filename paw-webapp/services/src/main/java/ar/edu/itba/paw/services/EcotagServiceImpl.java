package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.EcotagDao;
import ar.edu.itba.paw.interfaces.services.EcotagService;
import ar.edu.itba.paw.models.Ecotag;
import ar.edu.itba.paw.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcotagServiceImpl implements EcotagService {

    private final EcotagDao ecotagDao;

    @Autowired
    public EcotagServiceImpl(EcotagDao ecotagDao) {
        this.ecotagDao = ecotagDao;
    }

    @Override
    public void addTag(Ecotag tag, long productId) {
        ecotagDao.add(tag, productId);
    }

    @Override
    public void removeTag(Ecotag tag, long productId) {

    }

    @Override
    public List<Ecotag> getTagFromProduct(long productId) {
        return ecotagDao.getTagsFromProduct(productId);
    }
}
