package com.heuman.common.config.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * Created by heuman on 2018/3/23.
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        char[] input = (char[]) token.getCredentials();
        char[] secret = (char[]) info.getCredentials();
        // TODO
        return input != null && input.length > 0 && secret != null && secret.length > 0;
    }
}
