package com.interest.auth.rest;

import com.interest.auth.Constant;
import com.interest.auth.bean.ResultBean;
import com.interest.auth.bean.UserBean;
import com.interest.auth.dao.UserFinanceRepository;
import com.interest.auth.dao.UserRepository;
import com.interest.auth.daobean.User;
import com.interest.auth.util.HGException;
import com.interest.auth.util.Security;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RestController
public class UserRest
{
    protected Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserFinanceRepository userFinanceRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/userinfo",method = RequestMethod.GET )
    public @ResponseBody ResultBean queryUserInfo(HttpSession session) throws HGException
    {
        String username = (String)session.getAttribute("username");
        System.out.println("username="+username);

        //TODO 调试用，防呆，可删除
        if(username == null ){
            username = "admin";
        }
        //
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new HGException(Constant.RET_CODE_INPUT_ILLEGAL, "queryUserInfo no user");
        }

        Map<String,Object> result = new HashMap<String,Object>(10);
        result.put("nickname",user.getNickname());
        result.put("username",username);
        Integer role = user.getRole();
        String roles = "";
        switch (role){
           case Constant.ROLE_TYPE_ADMIN:
                roles = "admin";
                break;
            case Constant.ROLE_TYPE_NORMAL:
                roles = "normal";
                break;
        }
        result.put("roles",roles);
        return new ResultBean(Constant.SUCCESS, result);
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST )
    public boolean addUser(@RequestBody UserBean payload)
    {
        // TODO 校验入口参数
        System.out.println("input value " + payload);
        String username = payload.getUsername();
        String password = payload.getPassword();
        String nickname = payload.getNickname();
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
        user.setRole(Constant.ROLE_TYPE_NORMAL);
        user.setNickname(nickname);
        user.setTelephone(telephone);
        try{
            userRepository.save(user);
        }catch(Exception ex){
            System.out.println("save user failed " + ex);
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET )
    public @ResponseBody ResultBean queryAllUser()
    {

        final List<UserBean> resultBean = new ArrayList<UserBean>();

        Consumer<User> consumerUsers = new Consumer<User>() {
            public void accept(User user) {
                UserBean temp = new UserBean();
                temp.setBalance(userFinanceRepository.getBalanceByUsername(user.getUsername()));
                temp.setNickname(user.getNickname());
                temp.setRole(user.getRole());
                temp.setUsername(user.getUsername());
                temp.setTelephone(user.getTelephone());
                resultBean.add(temp);
            }
        };

        Iterable users = userRepository.findAll();
        users.forEach(consumerUsers);

        ResultBean result = new ResultBean(Constant.SUCCESS,resultBean);
        return result;
    }

    @RequestMapping(value = "/user/{username}",method = RequestMethod.DELETE )
    public @ResponseBody ResultBean delete(@PathVariable String username)
    {
        Integer retCode = 1;
        try{
            retCode = userRepository.deleteByUsername(username);
        }catch(Exception ex){
            System.out.println("delete user failed ,ex = " + ex);
        }
        ResultBean result = new ResultBean(retCode,null);
        return result;
    }
}
