package com.heuman.common.config.shiro;

import com.heuman.service.UserService;
import com.heuman.vo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by heuman on 2018/3/21.
 */
public class CustomRealm extends AuthorizingRealm {

    private final Logger logger = LoggerFactory.getLogger(CustomRealm.class);

    private UserService userService;

/*    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessToken;
    }*/

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        String username = (String) principal.getPrimaryPrincipal();
        User user = userService.findByUsername(username);
        if (user == null) {
            logger.error("Account not found, username:{}", username);
            return null;
        } else if (user.isLocked()) {
            logger.error("This account is locked, username:{}", username);
            return null;
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = userService.findRolesByUserId(user.getUserId());
        info.setRoles(roles);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        char[] password = (char[]) token.getCredentials();
        User user = userService.findByUsernameAndPassword(username, new String(password));
        // validate user TODO - 登录失败次数限制
        if (user == null) {
            throw new UnknownAccountException("Username or password error.");
        } else if (user.isLocked()) {
            throw new DisabledAccountException("This account is locked, username:" + username);
        }
        return new SimpleAuthenticationInfo(username, password, getName());
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
