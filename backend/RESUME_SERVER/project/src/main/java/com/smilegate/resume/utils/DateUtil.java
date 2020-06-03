package com.smilegate.resume.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {

    public String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String changeFormat(String origin) {
        LocalDateTime date = LocalDateTime.parse(origin, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
    }

}
