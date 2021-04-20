package com.travel.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created on 2021/4/8.
 *
 * @author Zhouyong Tan
 */
public class JwtToken implements AuthenticationToken {
    public String token;
    public JwtToken(String jwt){
        this.token=jwt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
