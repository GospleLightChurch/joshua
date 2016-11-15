package org.gyt.web.api.ws;

import org.gyt.web.api.service.SlidePictureService;
import org.gyt.web.model.SlidePicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 首页大图web接口
 * Created by Administrator on 2016/9/18.
 */
@RestController
public class SlidePictureWebServiceAPI {

    @Autowired
    private SlidePictureService slidePictureService;

    @RequestMapping(value = "/api/gallery/static/add", method = RequestMethod.POST)
    public String add(@RequestParam MultipartFile file, @RequestParam String link) {
        SlidePicture slidePicture = new SlidePicture();

        try {
            slidePicture.setContent(file.getBytes());
            slidePicture.setLink(link);
        } catch (IOException e) {
            e.printStackTrace();
            return "读书大图信息出错";
        }

        if (slidePictureService.add(slidePicture)) {
            return "success";
        } else {
            return "添加大图失败";
        }
    }

    @RequestMapping(value = "/api/gallery/static/remove", method = RequestMethod.POST)
    public String remove(@RequestParam Long id) {
        if (slidePictureService.remove(id)) {
            return "success";
        } else {
            return "删除大图失败";
        }
    }

    @RequestMapping(value = "/gallery/{id}", method = RequestMethod.GET)
    public byte[] get(@PathVariable Long id) {
        SlidePicture slidePicture = slidePictureService.get(id);
        return slidePicture == null ? null : slidePicture.getContent();
    }
}
