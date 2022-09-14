package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value="/image/{imageId:[0-9]+}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] image(@PathVariable final long imageId){
        Optional<Image> image = imageService.getById(imageId);
        if(!image.isPresent()) throw new IllegalStateException("La imagen no fue encontrada");
        return image.get().getSource();
    }
}
