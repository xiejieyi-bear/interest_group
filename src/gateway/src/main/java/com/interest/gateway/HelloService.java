package com.interest.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

//@Service
//public class HelloService
//{
//    @Autowired
//    RestTemplate restTemplate;
//
//    public String hiService(String name) {
//        return restTemplate.getForObject("http://service-auth/hi?name="+name,String.class);
//    }
//}

@FeignClient(value = "service-auth")
public interface HelloService{
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
