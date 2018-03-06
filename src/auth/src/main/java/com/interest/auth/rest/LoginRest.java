package com.interest.auth.rest;

import com.interest.auth.Constant;
import com.interest.auth.bean.ResultBean;
import com.interest.auth.daobean.User;
import com.interest.auth.service.IUserService;
import com.interest.auth.util.HGException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;


/**
 * 类描述：用户登录鉴权操作
 *
 * @author
 */
@RestController
public class LoginRest
{
    protected Log logger = LogFactory.getLog(getClass());

    @Autowired
    private IUserService userService;

    @RequestMapping(path="/authenticate",method = RequestMethod.POST)
    public @ResponseBody ResultBean authenticate(@RequestBody Map<String, String> payload, HttpServletRequest
            request) throws HGException
    {
        // 获取参数
        String name = payload.get("username");
        String pwd = payload.get("password");
        System.out.println("name="+ name+ "\npwd="+pwd);

        //异常会统一处理，直接抛出去
        User user = userService.getUserByUsername(name);
        userService.validateAuthenticate(pwd,user);
        logger.info("user =" + name +" login success.");

        request.getSession().invalidate();
        request.getSession().setAttribute("isLogin",true);
        request.getSession().setAttribute("username",name);
        logger.info("isLogin="+ String.valueOf(request.getSession().getAttribute("isLogin")));
        logger.info("username="+ String.valueOf(request.getSession().getAttribute("username")));

        return new ResultBean(Constant.SUCCESS, null);
    }
}


