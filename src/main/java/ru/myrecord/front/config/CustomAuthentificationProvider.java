package ru.myrecord.front.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthentificationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String pass = authentication.getCredentials().toString();

        System.out.print("login = " + login + "; pass = " + pass);

        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals( UsernamePasswordAuthenticationToken.class );
    }
}
