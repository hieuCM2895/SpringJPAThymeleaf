package com.fpt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @GetMapping("/hello")
    public String homePage() {
        return "Hello";
    }

    @GetMapping("/admin")
    public String adminPage() {return "Hello"; }

    @GetMapping("/admin/product")
    public String adminPage1() {return "Hello"; }

    @GetMapping("/admin/product/add")
    public String adminPage2() {return "Hello"; }
}
