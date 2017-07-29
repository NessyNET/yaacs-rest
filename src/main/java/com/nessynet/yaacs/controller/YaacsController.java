package com.nessynet.yaacs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YaacsController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello!";
    }
}
