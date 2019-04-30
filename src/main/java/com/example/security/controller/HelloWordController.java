package com.example.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shiyuan
 * @create 2019/4/30
 */
@RestController
public class HelloWordController {

    @GetMapping("hello")
    @PreAuthorize("hasAnyRole('admin')")
    public String hello() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Hello Word Security";
    }

}
