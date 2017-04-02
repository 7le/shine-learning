package com.shine.web;


import com.shine.constant.ResultBean;
import com.shine.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 7le on 2017/4/3.
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录界面
     */
    @RequestMapping(value = {"/","login"})
    public String loginView(HttpServletRequest request) {
        return "login";
    }

    /**
     * 注册页面
     * @param request
     * @return
     */
    @RequestMapping(value ="register")
    public String registerView(HttpServletRequest request) {
        return "register";
    }

    /**
     * 注册帐号密码
     */
    @RequestMapping(value = "doRegister")
    @ResponseBody
    public ResultBean doRegister(String loginName,String password){

        //执行登录
        String errorReport = loginService.doAdminRegister(loginName, password);
        if (errorReport != null) {
            return new ResultBean(false, errorReport);
        } else {
            return new ResultBean(true, "注册成功");
        }
    }
}
