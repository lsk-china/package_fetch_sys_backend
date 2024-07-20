package com.lsk.packagefetch.service;

import com.lsk.packagefetch.model.User;

public interface UserService {
    User queryUserByName(String username);
    User queryUserById(Integer uid);
    void createUser(String username, String password);
}
