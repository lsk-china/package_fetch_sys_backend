package com.lsk.packagefetch.controller;

import com.lsk.packagefetch.aspect.annotation.RequireCaptcha;
import com.lsk.packagefetch.helper.CaptchaHelper;
import com.lsk.packagefetch.model.User;
import com.lsk.packagefetch.service.UserService;
import com.lsk.packagefetch.util.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("/api/public")
public class PublicController {
    @Resource
    private UserService userService;

    @Resource
    private CaptchaHelper captchaHelper;

    @GetMapping("/generateCaptcha")
    public String generateCaptcha() {
        return ResponseUtil
                .ok(captchaHelper.prepareCaptcha())
                .build();
    }

    @GetMapping("/getCaptcha")
    public String getCaptcha(@RequestParam("code_id") String codeId) {
        return ResponseUtil
                .ok(captchaHelper.getCaptcha(codeId))
                .build();
    }

    @RequireCaptcha
    @PostMapping("/register")
    public String register(
            @RequestParam("code_id") String codeID,
            @RequestParam("code_content") String codeContent,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        userService.createUser(username, password);
        return ResponseUtil.ok().build();
    }
}
