package com.dmbb.steward.controller;

import com.dmbb.steward.service.RestRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final RestRequestService restRequestService;

    @GetMapping
    public String getInto() {
        return "Info response from spring-app-c";
    }


}
