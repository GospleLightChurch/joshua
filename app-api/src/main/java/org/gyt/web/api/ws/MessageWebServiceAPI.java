package org.gyt.web.api.ws;

import org.gyt.web.api.service.MessageService;
import org.gyt.web.api.utils.ModelAndViewUtils;
import org.gyt.web.model.Message;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * 留言消息web接口
 * Created by Administrator on 2016/9/18.
 */
@RestController
@RequestMapping("/message")
public class MessageWebServiceAPI {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public ModelAndView publish(@ModelAttribute Message message) {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("staticPage/contactPage");

        message.setCreatedDate(new Date());
        message.setOwner((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (messageService.createOrUpdate(message)) {
            modelAndView.setViewName("redirect:/contact?publishSuccess=true");
        } else {
            modelAndView.setViewName("redirect:/contact?publishFailed=true");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public String read(@RequestParam Long id) {
        Message message = messageService.get(id);

        if (message != null) {
            message.setRead(true);
            if (messageService.createOrUpdate(message)) {
                return "success";
            }

            return "更新状态失败";
        }

        return "留言消息不存在";
    }

    @RequestMapping(value = "/unread", method = RequestMethod.POST)
    public String unread(@RequestParam Long id) {
        Message message = messageService.get(id);

        if (message != null) {
            message.setRead(false);
            if (messageService.createOrUpdate(message)) {
                return "success";
            }

            return "更新状态失败";
        }

        return "留言消息不存在";
    }
}
