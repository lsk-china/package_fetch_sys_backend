package com.lsk.packagefetch.aspect;

import com.lsk.packagefetch.helper.CaptchaHelper;
import com.lsk.packagefetch.helper.RedisHelper;
import com.lsk.packagefetch.util.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

@Slf4j
@Aspect
@Order(10)
@Component
public class CaptchaAspect {
    @Resource
    private CaptchaHelper captchaHelper;

    @Pointcut("@annotation(com.lsk.packagefetch.aspect.annotation.RequireCaptcha)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String codeID = (String) getArg(pjp, "codeID");
        String userAnswer = (String) getArg(pjp, "codeContent");
        if (captchaHelper.checkCaptcha(codeID, userAnswer)) {
            throw new StatusCode(403, "Captcha required");
        }
        return pjp.proceed();
    }

    private Object getArg(ProceedingJoinPoint pjp, String arg) {
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(arg)) {
                return pjp.getArgs()[i];
            }
        }
        throw new RuntimeException("Argument " + arg + " not found");
    }
}
