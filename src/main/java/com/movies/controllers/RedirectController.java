package com.movies.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RedirectController {
    @GetMapping("/swagger-ui.html")
    public void redirectToSwaggerUI() {
        
    }
}
