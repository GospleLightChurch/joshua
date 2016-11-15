package org.gyt.web;

import org.gyt.web.api.service.*;
import org.gyt.web.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Date;
import java.util.Random;

class ApplicationTestResourceHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationTestResourceHelper.class);

    private static final String[] FELLOWSHIPS = {
            "abz",
            "cjtq",
            "dgh",
            "firstlove",
            "fyds",
            "jdz",
            "jntq",
            "metq",
            "qntq",
            "scz",
            "slnxtq",
            "tmttq",
            "ygtq",
            "ykz",
            "zztq"
    };

    private static final int TEST_USER_COUNT = 100;
    private static final int TEST_ARTICLE_COUNT = 100;
    private static final int TEST_MESSAGE_COUNT = 100;

    static void createTestUsers(ApplicationContext applicationContext) {
        UserService userService = applicationContext.getBean(UserService.class);
        RoleService roleService = applicationContext.getBean(RoleService.class);

        for (int i = 0; i < TEST_USER_COUNT; i++) {
            String username = String.format("testUser%03d", i + 1);

            if (null == userService.get(username)) {
                User user = new User();
                user.setUsername(username);
                user.setPassword("12345678");
                user.setNickname(String.format("测试用户昵称%03d", i + 1));
                user.setName(String.format("测试用户名字%03d", i + 1));
                user.setTelephone(String.format("13588880%03d", i + 1));
                user.setEmail(String.format("testUser%03d@gyt.com", i + 1));
                user.setSex(new Random().nextInt(3));

                if (new Random().nextInt(100) <= 2) {
                    user.getRoles().add(roleService.get("ADMIN"));
                }
                if (new Random().nextInt(100) <= 2) {
                    user.getRoles().add(roleService.get("EDITOR"));
                }
                if (new Random().nextInt(100) <= 2) {
                    user.getRoles().add(roleService.get("FS_ADMIN"));
                }
                if (new Random().nextInt(100) <= 2) {
                    user.getRoles().add(roleService.get("RE_ADMIN"));
                }
                if (new Random().nextInt(100) <= 2) {
                    user.getRoles().add(roleService.get("MEMBER"));
                }

                user.getRoles().add(roleService.get("USER"));

                userService.create(user);
                LOGGER.info(String.format("创建测试用户成功：%s", userService.get(username)));
            }
        }
    }

    static void createTestArticles(ApplicationContext applicationContext) {
        UserService userService = applicationContext.getBean(UserService.class);
        FellowshipService fellowshipService = applicationContext.getBean(FellowshipService.class);
        ArticleService articleService = applicationContext.getBean(ArticleService.class);

        for (int i = 0; i < TEST_ARTICLE_COUNT; i++) {
            if (articleService.get((long) (i + 1)) == null) {
                Article article = new Article();
                article.setTitle(String.format("测试文章%03d", i + 1));
                article.setDescription(String.format("测试文章%03d描述信息", i + 1));
                article.setAuthor(userService.get("administrator"));
                article.setFellowship(fellowshipService.get(FELLOWSHIPS[new Random().nextInt(FELLOWSHIPS.length)]));
                article.setCreatedDate(new Date());
                article.setLastModifiedTime(new Date());

                int status = new Random().nextInt(4);

                if (status == 0) {
                    article.setStatus(ArticleStatus.RAW);
                } else if (status == 1) {
                    article.setStatus(ArticleStatus.AUDITING);
                } else if (status == 2) {
                    article.setStatus(ArticleStatus.REJECTED);
                    article.setAuditor(userService.get("administrator"));
                } else {
                    article.setStatus(ArticleStatus.PUBLISHED);
                    article.setAuditor(userService.get("administrator"));
                }

                if (articleService.createOrUpdate(article) != null) {
                    LOGGER.info(String.format("创建测试文章成功：%s", articleService.get((long) (i + 1))));
                }
            }
        }
    }

    static void createTestMessage(ApplicationContext applicationContext) {
        UserService userService = applicationContext.getBean(UserService.class);
        MessageService messageService = applicationContext.getBean(MessageService.class);

        for (int i = 0; i < TEST_MESSAGE_COUNT; i++) {
            if (messageService.get((long) (i + 1)) == null) {
                Message message = new Message();

                message.setOwner(userService.get("administrator"));
                message.setCreatedDate(new Date());
                message.setContact("消息联系人" + i + 1);
                message.setContent("消息内容" + i + 1);

                int type = new Random().nextInt(3);

                if (type == 0) {
                    message.setType(MessageType.SUFFRAGE);
                } else if (type == 1) {
                    message.setType(MessageType.ADVICE);
                } else {
                    message.setType(MessageType.QUESTION);
                }

                messageService.createOrUpdate(message);
                LOGGER.info(String.format("创建测试消息成功：%s", messageService.get((long) (i + 1))));
            }
        }
    }
}
