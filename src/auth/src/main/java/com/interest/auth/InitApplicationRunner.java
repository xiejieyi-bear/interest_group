package com.interest.auth;

import com.interest.auth.dao.ActivityRepository;
import com.interest.auth.dao.UserRepository;
import com.interest.auth.daobean.Activity;
import com.interest.auth.daobean.ActivityParticipate;
import com.interest.auth.daobean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


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

    @Autowired
    private ActivityRepository activityRepository;


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


            // Activity activity = new Activity();
            // activity.setState(0);
            // activity.setChargeTotal(40);
            // activity.setRemark("demo");
            // ActivityParticipate participate = new ActivityParticipate();
            // participate.setActivity(activity);
            // participate.setUsername("admin");
            //
            // ActivityParticipate participate1 = new ActivityParticipate();
            // participate1.setActivity(activity);
            // participate1.setUsername("xiejieyi");
            //
            // Set participates =  new HashSet<ActivityParticipate>();
            // participates.add(participate);
            // participates.add(participate1);
            // activity.setParticipates(participates);
            //
            // activityRepository.save(activity);
            //
            // for(Activity activity1: activityRepository.findAllByState(0)){
            //     System.out.println(activity1.toString());
            // }
            userRepository.save(user);
            System.out.println("init admin user success!" + user);
        }catch(Exception ex){
            System.out.println("init admin user failed " + ex);
        }


    }
}