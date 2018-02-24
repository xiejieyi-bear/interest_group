package com.interest.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//public class HelloController
//{
//    @Autowired
//    HelloService helloService;
//    @RequestMapping(value = "/getuser")
//    public String hi(@RequestParam String name){
//        return helloService.hiService(name);
//    }
//}

@RestController
public class HelloController{
    @Autowired
    HelloService helloService;
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String sayHi(@RequestParam String name){
        return helloService.sayHiFromClientOne(name);
    }
}
