package com.sonet.storage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "This is Home page";
    }

    @GetMapping("/user")
    public String user() {
        return "This is User page";
    }

    @GetMapping("/admin")
    public String admin() {
        return "This is Admin page";
    }

}
