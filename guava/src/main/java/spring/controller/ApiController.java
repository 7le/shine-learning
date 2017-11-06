package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.annotation.EventBusService;

/**
 * 发布消息
 * Created by 7le on 2017/7/21
 */
@Controller
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    EventBusService eventBusService;

    @RequestMapping(value = "log")
    public void log(){
        /*eventBusService.register(new ApiHandler());*/
        eventBusService.postEvent("yoyo");
        eventBusService.postEvent(1);
        eventBusService.postEvent(2L);
    }

}
