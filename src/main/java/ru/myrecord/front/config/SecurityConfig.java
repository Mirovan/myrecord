package ru.myrecord.front.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.myrecord.front.service.UserDetailsServiceImpl;

/**
 * Created by max on 12.11.2017.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailService")
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        //authManagerBuilder.authenticationProvider(new CustomAuthentificationProvider());
        authManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/users/").permitAll()
                    .antMatchers("/admin/").permitAll()
                    .antMatchers("/register/").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .csrf().disable()
                    .formLogin()
                    .loginPage("/login/")
                    .failureUrl("/login/?error=true")
                    .defaultSuccessUrl("/cabinet/")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .permitAll()
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/").and().exceptionHandling()
                    .accessDeniedPage("/access-denied");
//                    and().
//                formLogin().
//                    loginPage("/login/").
//                    permitAll().
//                    and().
//                logout().
//                    permitAll();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

}
