package com.directory.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    /**
     * Allow acces to template pages
     * @return
     */
    @GetMapping()
    public String showV2() {
        return "index";
    }
}
