package com.rest.recruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class RecruitApplication {

    public static void main(String[] args){
        SpringApplication.run(RecruitApplication.class, args);
    }

}
