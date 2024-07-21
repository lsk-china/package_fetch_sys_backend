package com.lsk.packagefetch.aspect;

import com.lsk.packagefetch.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandleAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String response = ResponseUtil.error(e).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
