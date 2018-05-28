package com.heuman.common.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * Created by heuman on 2018/3/22.
 */
public class StatelessToken implements AuthenticationToken {

    private String username;
    private String token;
    private Map<String, ?> params;

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, ?> getParams() {
        return params;
    }

    public void setParams(Map<String, ?> params) {
        this.params = params;
    }
}
