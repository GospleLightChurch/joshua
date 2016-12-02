package org.gyt.web.api;

import org.gyt.web.core.utils.OperationResponse;
import org.gyt.web.core.utils.OperationResponseFactory;
import org.gyt.web.model.Image;
import org.gyt.web.model.User;
import org.gyt.web.repository.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/image")
public class ImageWebService {

    @Autowired
    private ImageRepository imageRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public byte[] getImage(@PathVariable String id) {
        Image image = imageRepository.findOne(id);

        if (StringUtils.isEmpty(id) || image == null) {
            return null;
        }

        return image.getContent();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public OperationResponse uploadImage(Principal principal, @RequestParam MultipartFile file) {
        User user = (User) principal;
        Image image = new Image();
        image.setOwner(user);

        try {
            image.setContent(file.getBytes());

            image = imageRepository.save(image);
            if (image != null) {
                return OperationResponseFactory.ok(image.getId());
            } else {
                return OperationResponseFactory.error("保存图片失败");
            }
        } catch (IOException e) {
            return OperationResponseFactory.error(e.getMessage());
        }
    }
}
