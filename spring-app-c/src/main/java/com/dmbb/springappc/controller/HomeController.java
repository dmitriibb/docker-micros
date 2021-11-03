package com.dmbb.springappc.controller;

import com.dmbb.springappc.service.RestRequestService;
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
