package com.shine.web;

import com.shine.annotation.Add;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 7le on 2017/7/5.
 * 测试控制器
 */
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String test(@Add Integer[] batch){
        return "ok";
    }
}
