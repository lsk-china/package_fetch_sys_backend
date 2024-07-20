package com.lsk.packagefetch.security;

import com.lsk.packagefetch.helper.RedisHelper;
import com.lsk.packagefetch.model.User;
import com.lsk.packagefetch.service.UserService;
import com.lsk.packagefetch.util.ResponseUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private UserService userService;

    @Resource
    private RedisHelper redisHelper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) authentication.getDetails();
            User user = userService.queryUserByName(userDetails.getUsername());
            redisHelper.cacheUser(user);
            redisHelper.bindUsernameAndUid(user.getUsername(), user.getId());
            response.getWriter().write(ResponseUtil.ok().build());
            response.getWriter().flush();
        } catch (Exception e) {
            response.getWriter().write(ResponseUtil.error(e).build());
            response.getWriter().flush();
        }
    }
}
