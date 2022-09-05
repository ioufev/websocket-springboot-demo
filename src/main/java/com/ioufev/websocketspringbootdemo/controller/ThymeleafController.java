package com.ioufev.websocketspringbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ThymeleafController {
    @GetMapping("/thymeleaf")
    public String hello(HttpServletRequest request, @RequestParam(value = "description", required = false,
            defaultValue = "还没想好默认值") String description1){
        request.setAttribute("description_value", description1);
        return "index";
    }

    @GetMapping("/ws")
    public String ws(){
        return "ws-demo";
    }

}
