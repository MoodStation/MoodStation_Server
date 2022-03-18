package com.moodstation.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class MoodstationApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoodstationApplication.class, args);
    }
}
