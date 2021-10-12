package com.k8s.basics.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/health")
    public String healthCheck() {
        return "I'm good";
    }

    @PostMapping(value = "/post")
    public String post(@RequestBody final String message) {
        System.out.println(message);
        return message;
    }

}
