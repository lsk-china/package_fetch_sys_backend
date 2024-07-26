package com.lsk.packagefetch.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public final class SecurityUtil {
    public static final String currentUsername() {
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return userDetails.getUsername();
    }
}
