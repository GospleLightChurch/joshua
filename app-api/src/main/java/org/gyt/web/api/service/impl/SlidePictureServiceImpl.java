package org.gyt.web.api.service.impl;

import org.gyt.web.api.repository.SlidePictureRepository;
import org.gyt.web.api.service.SlidePictureService;
import org.gyt.web.model.SlidePicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlidePictureServiceImpl implements SlidePictureService {

    @Autowired
    private SlidePictureRepository slidePictureRepository;

    @Override
    public List<SlidePicture> list() {
        return slidePictureRepository.findAll();
    }

    @Override
    public SlidePicture get(Long id) {
        return slidePictureRepository.findOne(id);
    }

    @Override
    public boolean add(SlidePicture slidePicture) {
        return slidePictureRepository.save(slidePicture) != null;
    }

    @Override
    public boolean remove(Long id) {

        SlidePicture slidePicture = slidePictureRepository.findOne(id);

        if (slidePicture == null) {
            return false;
        }

        slidePictureRepository.delete(id);

        return true;
    }
}
