package com.parentapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {

    @GetMapping("/api/auth")
    @ResponseBody
    public String index() {
        return "Hello World!";
    }

}
