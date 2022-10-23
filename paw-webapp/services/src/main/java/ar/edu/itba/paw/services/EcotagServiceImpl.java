package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.interfaces.services.EcotagService;
import ar.edu.itba.paw.models.Ecotag;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Service
public class EcotagServiceImpl implements EcotagService {

    private final ProductDao productDao;

    @Autowired
    public EcotagServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    @Override
    public void addTag(Ecotag tag, long productId) {
        Optional<Product> product = productDao.getById(productId);
        if(!product.isPresent())
            throw new ProductNotFoundException();
        product.get().addEcotag(tag);
    }

    @Override
    public List<Ecotag> getTagsFromProduct(long productId) {
        Optional<Product> product = productDao.getById(productId);
        if(!product.isPresent())
            throw new ProductNotFoundException();
        return product.get().getTagList();
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
