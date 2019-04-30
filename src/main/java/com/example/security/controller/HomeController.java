package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author shiyuan
 * @create 2019/4/30
 */
@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
