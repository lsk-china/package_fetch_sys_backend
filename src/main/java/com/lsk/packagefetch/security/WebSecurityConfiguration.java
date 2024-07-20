package com.lsk.packagefetch.security;

import com.lsk.packagefetch.util.ResponseUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private MyUserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Resource
    private MyLogoutHandler myLogoutHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/api/public/*").permitAll()
                    .antMatchers("/api/authorized/*").authenticated()
                    .anyRequest().denyAll()
                .and().formLogin()
                    .loginProcessingUrl("/api/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                    .permitAll()
                .and().exceptionHandling()
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.getWriter()
                                .println(ResponseUtil.error(401, "Authentication Required"));
                    })
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.getWriter()
                                .println(ResponseUtil.error(403, "Forbidden"));
                    })
                .and().logout()
                    .addLogoutHandler(myLogoutHandler)
        ;
    }
}
