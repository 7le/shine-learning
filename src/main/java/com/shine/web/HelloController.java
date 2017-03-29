package com.shine.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by hq on 2017/3/28 0028.
 */
@Controller
public class HelloController {

    @Value("${com.7le.name}")
    private  String name;
    @Value("${com.7le.want}")
    private  String want;
    @Value("${application.message:1234556677}")
    private String message = "hi,hello world......";

    @RequestMapping("/hello")
    public String index(){
        return "Hello Spring Boot";
    }

    @RequestMapping("/7le")
    public String le(){
        return name+" "+want;
    }

    @RequestMapping("/")
    public String web(ModelMap model){
        model.put("time",new Date());
        model.put("message",this.message);
        return "web";//返回的内容就是templetes下面文件的名称
    }
}
