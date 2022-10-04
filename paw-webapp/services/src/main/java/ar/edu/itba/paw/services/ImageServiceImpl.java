package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ImageDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageDao imageDao;

    @Autowired
    public ImageServiceImpl(ImageDao imageDao){
        this.imageDao = imageDao;
    }

    @Transactional
    @Override
    public Image create(byte[] source) {
        return imageDao.create(source);
    }

    @Override
    public Optional<Image> getById(long id) {
        return imageDao.getById(id);
    }
}
