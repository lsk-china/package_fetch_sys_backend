package com.lsk.packagefetch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lsk.packagefetch.helper.RedisHelper;
import com.lsk.packagefetch.mapper.UserMapper;
import com.lsk.packagefetch.model.User;
import com.lsk.packagefetch.service.UserService;
import com.lsk.packagefetch.util.StatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisHelper redisHelper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public User queryUserByName(String username) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            throw new StatusCode(404, "User not found");
        }
        return user;
    }

    @Override
    public User queryUserById(Integer uid) {
        User user = redisHelper.queryCachedUser(uid);
        if (user != null) {
            return user;
        }
        user = userMapper.selectOne(new QueryWrapper<User>().eq("id", uid));
        if (user == null) {
            throw new StatusCode(404, "User not found");
        }
        redisHelper.cacheUser(user);
        return user;
    }

    @Override
    public void createUser(String username, String password) {
        User check = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (check != null) {
            throw new StatusCode(405, "Username has already been used");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userMapper.insert(user);
    }
}
