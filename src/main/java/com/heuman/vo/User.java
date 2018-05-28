package com.heuman.vo;

import com.heuman.common.base.BaseVO;
import org.apache.ibatis.javassist.SerialVersionUID;

/**
 * Created by heuman on 2018/3/23.
 */
public class User extends BaseVO {

    private final static long serialVersionUID = 1L;

    private String userId;
    private String username;
    private String password;
    private boolean locked;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
