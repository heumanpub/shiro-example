package com.heuman.service;

import com.heuman.mapper.UserMapper;
import com.heuman.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by heuman on 2018/3/26.
 */
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    public User findByUsernameAndPassword(String username, String password) {
        return userMapper.findByUsernameAndPassword(username, password);
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public Set<String> findRolesByUserId(String userId) {
        return userMapper.findRolesByUserId(userId);
    }

}
