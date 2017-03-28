package com.shine.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hq on 2017/3/28 0028.
 */
@RestController
public class HelloController {

    @Value("${com.7le.name}")
    private  String name;
    @Value("${com.7le.want}")
    private  String want;

    @RequestMapping("/hello")
    public String index(){
        return "Hello Spring Boot";
    }

    @RequestMapping("/7le")
    public String le(){
        return name+" "+want;
    }
}
