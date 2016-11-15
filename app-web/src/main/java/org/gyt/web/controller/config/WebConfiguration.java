package org.gyt.web.controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
    public static String GALLERY = "/var/opt/joshua/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/images/gallery/**").addResourceLocations("file:" + GALLERY);
    }

    @Value("${joshua.dir.gallery}")
    public void setROOT(String root) {
        WebConfiguration.GALLERY = root;
    }
}
