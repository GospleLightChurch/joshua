package org.gyt.web.api.ws;

import org.gyt.web.api.service.UserService;
import org.gyt.web.api.utils.LoginUtils;
import org.gyt.web.api.utils.ModelAndViewUtils;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户web接口
 * Created by Administrator on 2016/9/18.
 */
@RestController
@RequestMapping("/api/user")
public class UserWebServiceAPI {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ModelAndView changeInfo(@ModelAttribute User user) {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("redirect:/info?publishSuccess=true");

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        currentUser.setNickname(user.getNickname());
        currentUser.setTelephone(user.getTelephone());
        currentUser.setEmail(user.getEmail());
        currentUser.setName(user.getName());
        currentUser.setSex(user.getSex());
        currentUser.setAddress(user.getAddress());

        userService.update(currentUser);

        return modelAndView;
    }

    @RequestMapping(value = "/lock/{username}", method = RequestMethod.POST)
    public boolean lock(@PathVariable String username) {
        return userService.lock(username);
    }

    @RequestMapping(value = "/unlock/{username}", method = RequestMethod.POST)
    public boolean unlock(@PathVariable String username) {
        return userService.unlock(username);
    }

    @RequestMapping(value = "/enable/{username}", method = RequestMethod.POST)
    public boolean enable(@PathVariable String username) {
        return userService.enable(username);
    }

    @RequestMapping(value = "/disable/{username}", method = RequestMethod.POST)
    public boolean disable(@PathVariable String username) {
        return userService.disable(username);
    }
}
