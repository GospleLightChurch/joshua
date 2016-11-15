package org.gyt.web.api.service;

import javax.mail.MessagingException;

/**
 * 邮件API，提供一下接口
 * - 发送文本邮件到指定邮箱
 * Created by y27chen on 2016/10/10.
 */
public interface MailService {

    void sendText(String to, String subject, String content) throws MessagingException;
}
