package com.interest.auth.rest;

import com.interest.auth.Constant;
import com.interest.auth.bean.ResultBean;
import com.interest.auth.dao.ActivityRepository;
import com.interest.auth.dao.CourtRepository;
import com.interest.auth.dao.UserRepository;
import com.interest.auth.daobean.Activity;
import com.interest.auth.daobean.Court;
import com.interest.auth.daobean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 类描述：场地管理类
 *
 * @author
 */
@RestController
public class activityRest
{
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="/activity/join",method = RequestMethod.POST)
    public boolean joinActivity(@RequestBody Map<String, String> payload){

        Long activityID = Long.valueOf(payload.get("activity_id"));
        String userMark = payload.get("usermark");
        Integer participateNum = Integer.valueOf(payload.get("nums"));

        //根据id找活动信息
        Activity activity = activityRepository.findOne(activityID);
        if(activity != null){
            //    添加报名用户信息
            String username  = userRepository.getUsernameByUsermark(userMark);
            String username2activity = username;
            if(participateNum > 1){
                username2activity = username+"*"+String.valueOf(participateNum);
            }
            String participateUsers = activity.getParticipateUsers();
            if(null == participateUsers || participateUsers.isEmpty()){
                activity.setParticipateUsers(username2activity);
            }else{
                activity.setParticipateUsers(participateUsers+","+username2activity);
            }
            activityRepository.save(activity);
            return true;
        }
        return false;

    }

    @RequestMapping(value = "/activity",method = RequestMethod.POST )
    public boolean createActivity(@RequestBody Map<String, String> payload)
    {
        // TODO 校验入口参数
        System.out.println("input value " + payload);
        String beginTime = payload.get("begin_time");
        Timestamp timestamp = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date parsedDate = dateFormat.parse(beginTime);
            timestamp = new Timestamp(parsedDate.getTime());
        } catch(Exception e) { //this generic but you can control another types of exception
            System.out.println("trans datetime  failed " + e);
            return false;
        }

        Integer duration = Integer.valueOf(payload.get("duration"));
        Integer charge = Integer.valueOf(payload.get("charge"));

        //根据courtid查找名称
        Long courtID = Long.valueOf(payload.get("court_id"));
        Court court = courtRepository.findOne(courtID);
        String courtName = court.getName();

        Activity activity = new Activity();
        activity.setBeginTime(timestamp);
        activity.setDuration(duration);
        activity.setChargeTotal(charge);
        activity.setCourtID(courtID);
        activity.setCourtName(courtName);

        try{
            activityRepository.save(activity);
        }catch(Exception ex){
            System.out.println("save activity  failed " + ex);
            return false;
        }
        return true;
    }

    ////编辑场地信息
    //@RequestMapping(value = "/court",method = RequestMethod.PUT )
    //public @ResponseBody ResultBean editCourtByid(@RequestBody Map<String, String> payload){
    //
    //    String courtName = payload.get("name");
    //    String address = payload.get("addr");
    //    String telephone = payload.get("telephone");
    //
    //    Map<String,Object> retMap = new HashMap<String,Object>(10);
    //    ResultBean result = new ResultBean(Constant.SUCCESS,retMap);
    //    try{
    //        Long id = Long.valueOf(payload.get("id"));
    //        if(!courtRepository.exists(id)){
    //            result = new ResultBean(1,retMap);
    //            return result;
    //        }
    //        Court court = new Court();
    //        court.setAddr(address);
    //        court.setName(courtName);
    //        court.setTelephone(telephone);
    //        court.setId(id);
    //        courtRepository.save(court);
    //    }catch(Exception ex){
    //        System.out.println("save court  failed " + ex);
    //        result = new ResultBean(1,retMap);
    //        return result;
    //    }
    //    return result;
    //}


    //查询所有的活动信息
    @RequestMapping(value = "/activity",method = RequestMethod.GET )
    public @ResponseBody
    ResultBean queryAllActivities()
    {
        Iterable activities = activityRepository.findAll();
        ResultBean result = new ResultBean(Constant.SUCCESS,activities);
        return result;
    }

    //已经结束的活动不允许删除
    @RequestMapping(value = "/activity/{id}",method = RequestMethod.DELETE )
    public @ResponseBody ResultBean delete(@PathVariable Long id)
    {
        Integer retCode = 1;
        try{
            activityRepository.delete(id);
            retCode = Constant.SUCCESS;
        }catch(Exception ex){
            System.out.println("delete court failed ,ex = " + ex);
        }
        ResultBean result = new ResultBean(retCode,null);
        return result;
    }
}
