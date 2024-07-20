package com.lsk.packagefetch.security;

import com.lsk.packagefetch.helper.RedisHelper;
import com.lsk.packagefetch.util.RedisKeys;
import com.lsk.packagefetch.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class MyLogoutHandler implements LogoutHandler {
    @Resource
    private RedisHelper redisHelper;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            User userDetails = (User) authentication.getDetails();
            Integer uid = redisHelper.getUidByUsername(userDetails.getUsername());
            redisHelper.delete(RedisKeys.UID.of(userDetails.getUsername()));
            redisHelper.delete(RedisKeys.USERINFO.of(uid));
            response.getWriter().write(ResponseUtil.ok().build());
            response.getWriter().flush();
        } catch (Throwable t) {
            try {
                response.getWriter().write(ResponseUtil.error(t).build());
                response.getWriter().flush();
            } catch (IOException e) {
                log.error("Error handling logout error", e);
            }
        }
    }
}
