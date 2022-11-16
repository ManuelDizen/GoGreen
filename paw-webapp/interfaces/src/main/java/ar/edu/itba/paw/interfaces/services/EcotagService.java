package ar.edu.itba.paw.interfaces.services;


import ar.edu.itba.paw.models.Ecotag;
import ar.edu.itba.paw.models.Product;

import java.util.List;
import java.util.Set;

public interface EcotagService {
    void addTag(Ecotag tag, long productId);
    List<Ecotag> getTagsFromProduct(long productId);
    List<Ecotag> filterByTags(String[] ecoStrings, boolean[] ecotags);


}
