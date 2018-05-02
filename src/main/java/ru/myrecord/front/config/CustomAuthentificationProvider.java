package ru.myrecord.front.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
//import ru.myrecord.front.service.SysUserService;

@Component
public class CustomAuthentificationProvider implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    SysUserService sysUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String pass = authentication.getCredentials().toString();

        //SysUser sysUser = sysUserService.getByAuthEmail()


        System.out.print("login = " + login + "; pass = " + pass);

        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals( UsernamePasswordAuthenticationToken.class );
    }
}
