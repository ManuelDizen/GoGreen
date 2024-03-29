package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageService {
    Image create(final byte[] source);
    Optional<Image> getById(final long id);
}
