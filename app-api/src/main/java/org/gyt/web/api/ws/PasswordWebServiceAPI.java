package org.gyt.web.api.ws;

import org.gyt.web.api.service.MailService;
import org.gyt.web.api.service.UserService;
import org.gyt.web.api.utils.LoginUtils;
import org.gyt.web.api.utils.StringGeneratorUtils;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/**
 * 重置密码接口
 * Created by Administrator on 2016/9/18.
 */
@RestController
public class PasswordWebServiceAPI {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String changePassword(@RequestParam String password) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (LoginUtils.isValidPassword(password)) {
            currentUser.setPassword(new BCryptPasswordEncoder().encode(password));
            userService.update(currentUser);
        } else {
            return "密码格式不正确";
        }

        return "success";
    }

    @RequestMapping(value = "/forget", method = RequestMethod.POST)
    public String changeInfo(@RequestParam String username, @RequestParam String email) {

        User user = userService.get(username);

        if (null != user && user.getEmail().equals(email)) {
            String password = StringGeneratorUtils.nextNumberLetterString();

            user.setPassword(new BCryptPasswordEncoder().encode(password));
            userService.update(user);

            try {
                mailService.sendText(email, "重置您的密码，请妥善保管", String.format("您的光音堂账号%s新的密码为：【%s】", username, password));
                return "success";
            } catch (MessagingException e) {
                return "邮件发送失败";
            }
        }

        return "用户验证失败";
    }
}
