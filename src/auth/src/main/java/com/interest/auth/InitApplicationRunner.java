package com.interest.auth;

import com.interest.auth.dao.UserRepository;
import com.interest.auth.daobean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 在Spring boot环境准备好后，初始化admin数据。
 * @author xiejieyi
 * @date 2/26/2018
 */
@Component
@Order(value = 1)
public class InitApplicationRunner implements ApplicationRunner
{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments var1) throws Exception{
        System.out.println("InitApplicationRunner!");
        String username = "admin";
        String salt= "151de8fe02b2d60beec0864b97d08603f6ec94df455d402f";
        String encryptPWD = "53ca639632d193a8e92d569042e812bdc2804e9b9a175668";
        User user = new User();
        user.setUsername(username);
        user.setNickname("admin");
        user.setSalt(salt);
        user.setEncrypt(encryptPWD);
        user.setRole(Constant.ROLE_TYPE_ADMIN);
        try{
            userRepository.save(user);
            System.out.println("init admin user success!" + user);
        }catch(Exception ex){
            System.out.println("init admin user failed " + ex);
        }
    }
}