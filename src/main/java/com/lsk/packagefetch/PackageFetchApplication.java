package com.lsk.packagefetch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan
@SpringBootApplication
public class PackageFetchApplication {

    public static void main(String[] args) {
        SpringApplication.run(PackageFetchApplication.class, args);
    }

}
