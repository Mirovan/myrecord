package ru.myrecord;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by max on 12.11.2017.
 */

@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String templatesDir = "default";
        templatesDir = "/" + templatesDir;
        registry.addViewController(templatesDir + "/about/").setViewName("about");
        registry.addViewController(templatesDir + "/contact/").setViewName("contact");
        registry.addViewController(templatesDir + "/login/").setViewName("login");
    }

}
