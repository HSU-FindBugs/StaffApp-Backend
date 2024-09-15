package com.findbugs.findbugstaff.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainPageAPI {


    @GetMapping("main/{id}")
    public String getMainPage(@PathVariable("id") Long id) {

        return "ok";
    }

}
