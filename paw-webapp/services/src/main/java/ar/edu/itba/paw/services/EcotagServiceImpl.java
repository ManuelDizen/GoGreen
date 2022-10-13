package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.EcotagDao;
import ar.edu.itba.paw.interfaces.services.EcotagService;
import ar.edu.itba.paw.models.Ecotag;
import ar.edu.itba.paw.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;

@Service
public class EcotagServiceImpl implements EcotagService {

    private final EcotagDao ecotagDao;

    @Autowired
    public EcotagServiceImpl(EcotagDao ecotagDao) {
        this.ecotagDao = ecotagDao;
    }

    @Transactional
    @Override
    public void addTag(Ecotag tag, long productId) {
        ecotagDao.add(tag, productId);
    }

    @Override
    public Set<Ecotag> getTagFromProduct(long productId) {
        return ecotagDao.getTagsFromProduct(productId);
    }

    @Override
    public List<Ecotag> filterByTags(String[] ecoStrings, boolean[] ecotags) {
        List<Ecotag> toReturn = new ArrayList<>();
        if(!ecoStrings[0].equals("null")) {
            for(String s : ecoStrings) {
                toReturn.add(Ecotag.getById(parseInt(s)));
                ecotags[parseInt(s)-1] = true;
            }
        }
        return toReturn;
    }

}
