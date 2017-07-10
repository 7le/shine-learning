package com.shine.web;

import com.shine.annotation.mvc.Add;
import com.shine.annotation.spring.InjectClass;
import com.shine.annotation.spring.InjectClass2;
import com.shine.annotation.spring.InjectClass3;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    InjectClass injectClass;

    @Autowired
    InjectClass2 injectClass2;

    @Autowired
    InjectClass3 injectClass3;

    @RequestMapping(value = "/one")
    @ResponseBody
    public String test(@Add Integer[] batch){
        injectClass.print();
        injectClass2.fly();
        injectClass3.smile();
        return "ok";
    }
}
