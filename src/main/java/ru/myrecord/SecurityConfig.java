package ru.myrecord;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by max on 12.11.2017.
 */

@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                    antMatchers("/").permitAll().
                    anyRequest().authenticated().
                    and().
                formLogin().
                    loginPage("/login/").
                    permitAll().
                    and().
                logout().
                    permitAll();
    }
//ToDo: настроить Spring Security
}
