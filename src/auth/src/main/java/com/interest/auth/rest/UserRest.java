package com.interest.auth.rest;

import com.interest.auth.Constant;
import com.interest.auth.bean.UserBean;
import com.interest.auth.dao.UserRepository;
import com.interest.auth.daobean.User;
import com.interest.auth.util.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserRest
{
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/user",method = RequestMethod.POST )
    public boolean addUser(@RequestBody UserBean payload)
    {

        // TODO 校验入口参数
        System.out.println("input value " + payload);

        String username = payload.getUsername();
        String password = payload.getPassword();
        String usermark = payload.getUsermark();
        String telephone = payload.getTelephone();


        String salt = Security.generateSalt();
        String encryptPWD = Security.encrypt(password, salt);

        if(encryptPWD==null || encryptPWD.isEmpty() || encryptPWD == password){
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setSalt(salt);
        user.setEncrypt(encryptPWD);
        user.setRole(Constant.ROLE_TYPE.NORMAL.ordinal());
        user.setUsermark(usermark);
        user.setTelephone(telephone);
        try{
            userRepository.save(user);
        }catch(Exception ex){
            System.out.println("save user failed " + ex);
            return false;
        }
        return true;
    }
}