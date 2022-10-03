package ar.edu.itba.paw.interfaces.services;


import ar.edu.itba.paw.models.Ecotag;
import ar.edu.itba.paw.models.Product;

import java.util.List;

public interface EcotagService {

    public void addTag(Ecotag tag, long productId);

    public List<Ecotag> getTagFromProduct(long productId);

    public List<Ecotag> filterByTags(String[] ecoStrings, boolean[] ecotags);

}
