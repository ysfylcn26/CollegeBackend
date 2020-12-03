package com.poland.college;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CollegeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegeApplication.class, args);
    }

}
