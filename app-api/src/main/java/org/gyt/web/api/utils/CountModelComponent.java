package org.gyt.web.api.utils;

import org.gyt.web.api.model.ArticleCountModel;
import org.gyt.web.api.model.MessageCountModel;
import org.gyt.web.api.model.NotificationCountModel;
import org.gyt.web.api.model.UserCountModel;
import org.gyt.web.api.service.ArticleService;
import org.gyt.web.api.service.MessageService;
import org.gyt.web.api.service.NotificationService;
import org.gyt.web.api.service.UserService;
import org.gyt.web.model.ArticleStatus;
import org.gyt.web.model.MessageType;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CountModelComponent {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MessageService messageService;

    public UserCountModel getUserCountModel() {
        UserCountModel countModel = new UserCountModel();

        countModel.setAdminCount(userService.getAll().stream().filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))).count());
        countModel.setAdminFSCount(userService.getAll().stream().filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("FS_ADMIN"))).count());
        countModel.setAdminRECount(userService.getAll().stream().filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("RE_ADMIN"))).count());
        countModel.setEditorCount(userService.getAll().stream().filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("EDITOR"))).count());
        countModel.setMemberCount(userService.getAll().stream().filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("MEMBER"))).count());
        countModel.setUserCount(userService.getAll().stream().filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("USER"))).count());

        return countModel;
    }

    public ArticleCountModel getArticleCountModel() {
        ArticleCountModel articleCountModel = new ArticleCountModel();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (null != user) {
            articleCountModel.setMyArticleCount(articleService.getFromUser(user.getUsername()).stream().count());
            articleCountModel.setRawArticleCount(articleService.getFromUser(user.getUsername()).stream().filter(article -> article.getStatus().equals(ArticleStatus.RAW)).count());
            if (user.getRoles().stream().anyMatch(role -> role.getAuthorities().stream().anyMatch(s -> s.equals("ROLE_MANAGE_ARTICLE")))) {
                articleCountModel.setAuditArticleCount(articleService.getAll().stream().filter(article -> article.getStatus().equals(ArticleStatus.AUDITING)).count());
                articleCountModel.setPublishArticleCount(articleService.getAll().stream().filter(article -> article.getStatus().equals(ArticleStatus.PUBLISHED)).count());
                articleCountModel.setRejectArticleCount(articleService.getAll().stream().filter(article -> article.getStatus().equals(ArticleStatus.REJECTED)).count());
            } else {
                articleCountModel.setAuditArticleCount(articleService.getFromUser(user.getUsername()).stream().filter(article -> article.getStatus().equals(ArticleStatus.AUDITING)).count());
                articleCountModel.setPublishArticleCount(articleService.getFromUser(user.getUsername()).stream().filter(article -> article.getStatus().equals(ArticleStatus.PUBLISHED)).count());
                articleCountModel.setRejectArticleCount(articleService.getFromUser(user.getUsername()).stream().filter(article -> article.getStatus().equals(ArticleStatus.REJECTED)).count());
            }
        }

        return articleCountModel;
    }

    public NotificationCountModel getNotificationCountModel() {
        NotificationCountModel notificationCountModel = new NotificationCountModel();
        notificationCountModel.setAllCount(notificationService.getAll().stream().count());
        notificationCountModel.setCurrentCount(notificationService.getActivate().stream().count());
        notificationCountModel.setExpiredCount(notificationService.getExpired().stream().count());
        return notificationCountModel;
    }

    public MessageCountModel getMessageCountModel() {
        MessageCountModel messageCountModel = new MessageCountModel();
        messageCountModel.setAllCount(messageService.getAll().stream().count());
        messageCountModel.setAdviseCount(messageService.getByType(MessageType.ADVICE).stream().count());
        messageCountModel.setQuestionCount(messageService.getByType(MessageType.QUESTION).stream().count());
        messageCountModel.setSuffrageCount(messageService.getByType(MessageType.SUFFRAGE).stream().count());
        return messageCountModel;
    }
}
