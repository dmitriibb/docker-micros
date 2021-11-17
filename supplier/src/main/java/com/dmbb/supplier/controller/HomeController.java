package com.dmbb.supplier.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String getInto() {
        return "Info response from Supplier";
    }

}
