package com.lsk.packagefetch.security;

import com.lsk.packagefetch.util.ResponseUtil;
import com.lsk.packagefetch.util.StatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.getWriter().write(ResponseUtil.error(exception).build());
        response.getWriter().flush();
    }
}
