package org.gyt.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

/**
 * Nokia ChengDu Engine Team
 * Created by y27chen on 2016/7/12.
 */
@SpringBootApplication
public class Application {

    @Bean
    EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {
        return (ConfigurableEmbeddedServletContainer container) -> {
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
                tomcat.addConnectorCustomizers(
                        (connector) -> {
                            connector.setMaxPostSize(20000000); // 20 MB
                        }
                );
            }
        };
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        ApplicationResourceHelper.createDefaultRoles(applicationContext);
        ApplicationResourceHelper.createRootUser(applicationContext);
        ApplicationResourceHelper.createDefaultFellowship(applicationContext);

        if (args.length > 0 && args[0].equals("test")) {
            ApplicationTestResourceHelper.createTestUsers(applicationContext);
            ApplicationTestResourceHelper.createTestArticles(applicationContext);
            ApplicationTestResourceHelper.createTestMessage(applicationContext);
        }

        Locale.setDefault(new Locale("zh_CN"));
    }
}
