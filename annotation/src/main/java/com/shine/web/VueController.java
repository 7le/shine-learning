package com.shine.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器--学习vue的一些操作
 * Created by 7le on 2017/5/10 0010.
 */
@Controller
public class VueController {

    /**
     * 登录界面
     */
    @RequestMapping(value = "vue")
    public String vue(HttpServletRequest request) {
        return "vue";
    }
}
