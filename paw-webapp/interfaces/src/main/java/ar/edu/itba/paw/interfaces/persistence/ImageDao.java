package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageDao {
    Image create(final byte[] source);
    Optional<Image> getById(final long id);
}
