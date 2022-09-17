package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.EcotagDao;
import ar.edu.itba.paw.interfaces.services.EcotagService;
import ar.edu.itba.paw.models.Ecotag;
import ar.edu.itba.paw.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Ecotag> getTagFromProduct(long productId) {
        return ecotagDao.getTagsFromProduct(productId);
    }

    @Override
    public List<Ecotag> filterByTags(boolean[] ecotags) {
        List<Ecotag> toReturn = new ArrayList<>();
        for(int i=0; i<ecotags.length; i++) {
            if(ecotags[i])
                toReturn.add(Ecotag.getById(i));
        }
        return toReturn;
    }
}
