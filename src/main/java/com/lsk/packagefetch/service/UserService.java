package com.lsk.packagefetch.service;

import com.lsk.packagefetch.model.User;

import java.util.Map;

public interface UserService {
    User queryUserByName(String username);
    User queryUserById(Integer uid);
    void createUser(String username, String password);
    Map<String, Integer> myOrderStatistics();
}
