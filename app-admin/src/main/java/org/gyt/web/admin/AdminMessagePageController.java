package org.gyt.web.admin;

import org.gyt.web.core.utils.ModelAndViewUtils;
import org.gyt.web.core.utils.PaginationComponent;
import org.gyt.web.model.Message;
import org.gyt.web.model.MessageType;
import org.gyt.web.repository.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin/message")
public class AdminMessagePageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @Autowired
    private PaginationComponent paginationComponent;

    @RequestMapping(value = "/suffrage", method = RequestMethod.GET)
    public ModelAndView getSuffrage(Pageable pageable) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-message");
        Page<Message> messagePage = messageRepository.findByTypeOrderByIsReadAscCreatedDateDesc(pageable, MessageType.SUFFRAGE);
        modelAndView.addObject("items", messagePage.getContent());
        paginationComponent.addPagination(modelAndView, messagePage, "/admin/message/suffrage");
        return modelAndView;
    }

    @RequestMapping(value = "/advise", method = RequestMethod.GET)
    public ModelAndView getAdvise(Pageable pageable) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-message");
        Page<Message> messagePage = messageRepository.findByTypeOrderByIsReadAscCreatedDateDesc(pageable, MessageType.ADVICE);
        modelAndView.addObject("items", messagePage.getContent());
        paginationComponent.addPagination(modelAndView, messagePage, "/admin/message/advise");
        return modelAndView;
    }

    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public ModelAndView getQuestion(Pageable pageable) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-message");
        Page<Message> messagePage = messageRepository.findByTypeOrderByIsReadAscCreatedDateDesc(pageable, MessageType.QUESTION);
        modelAndView.addObject("items", messagePage.getContent());
        paginationComponent.addPagination(modelAndView, messagePage, "/admin/message/question");
        return modelAndView;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView tablePage(
            @RequestParam(required = false) String type, Pageable pageable
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-message");
        Page<Message> messagePage = messageRepository.findAllByOrderByIsReadAscCreatedDateDesc(pageable);
        modelAndView.addObject("items", messagePage.getContent());
        paginationComponent.addPagination(modelAndView, messagePage, "/admin/message/all");
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView detailsPage(@PathVariable Long id) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-message-details");

        Message message = messageRepository.findOne(id);

        if (message == null) {
            modelAndView.setViewName("404");
            modelAndView.addObject("message", "没有找到指定的消息");
        } else {
            modelAndView.addObject("message", message);
        }

        return modelAndView;
    }

}
