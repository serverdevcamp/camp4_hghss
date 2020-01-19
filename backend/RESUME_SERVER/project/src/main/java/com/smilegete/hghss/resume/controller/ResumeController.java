package com.smilegete.hghss.resume.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResumeController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
