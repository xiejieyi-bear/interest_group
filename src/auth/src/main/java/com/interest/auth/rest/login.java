package com.interest.auth.rest;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 类描述：用户登录鉴权操作
 *
 * @author
 */
@RestController
public class login
{
    @RequestMapping(path="/authenticate",method = RequestMethod.POST)
    public boolean authenticate(@RequestBody Map<String, String> payload){
        // 获取参数
        String name = payload.get("name");
        String pwd = payload.get("pwd");
        System.out.println("name="+ name+ "pwd="+pwd);
        return true;
    }
}
