package com.shine.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 7le on 2017/4/3.
 */
@Controller
public class IndexController {

    /**
     * 登录首页
     */
    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {
        return "index";
    }

    /**
     * 主页部分
     */
    @RequestMapping(value = "index_v5")
    public String index_v5(HttpServletRequest request) {
        return "index_v5";
    }


}
