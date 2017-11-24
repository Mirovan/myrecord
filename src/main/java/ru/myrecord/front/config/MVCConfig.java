package ru.myrecord.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by max on 12.11.2017.
 */

@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

//    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String templatesDir = "default";
        templatesDir = "/" + templatesDir;
        templatesDir = "/";
//        registry.addViewController(templatesDir + "/").setViewName("index");
//        registry.addViewController(templatesDir + "/users/").setViewName("users");
//        registry.addViewController(templatesDir + "/about/").setViewName("about");
//        registry.addViewController(templatesDir + "/contact/").setViewName("contact");
//        registry.addViewController(templatesDir + "/login/").setViewName("login");
    }

}
