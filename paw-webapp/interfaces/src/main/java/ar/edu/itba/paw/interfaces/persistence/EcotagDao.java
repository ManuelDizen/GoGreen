package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Ecotag;

import java.util.List;
import java.util.Set;

public interface EcotagDao {

    void add(Ecotag tag, long productId);

    List<Ecotag> getTagsFromProduct(long productId);

}
