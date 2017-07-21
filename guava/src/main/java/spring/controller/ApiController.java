package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.annotation.EventBusService;
import spring.handler.ApiHandler;

@Controller
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    EventBusService eventBusService;

    @RequestMapping(value = "log")
    public void log(){
        /*eventBusService.register(new ApiHandler());*/
        eventBusService.postEvent("yoyo");
    }

}
