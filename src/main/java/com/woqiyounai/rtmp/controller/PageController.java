package com.woqiyounai.rtmp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/page/test")
    public String toTest(){
        return "vlcTest";
    }
}
