package com.interest.auth.rest;

import com.interest.auth.Constant;
import com.interest.auth.bean.JoinActivityBean;
import com.interest.auth.bean.ResultBean;
import com.interest.auth.dao.ActivityRepository;
import com.interest.auth.dao.CourtRepository;
import com.interest.auth.dao.UserRepository;
import com.interest.auth.daobean.Activity;
import com.interest.auth.daobean.Court;
import com.interest.auth.daobean.User;
import com.interest.auth.service.IActivityService;
import com.interest.auth.util.HGException;
import com.interest.auth.util.ValidateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class ActivityRest
{
    protected Log logger = LogFactory.getLog(getClass());
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private IActivityService activityService;

    //查询最近上限的活动
    @RequestMapping(value = "/activity/join", method = RequestMethod.GET)
    public @ResponseBody
    ResultBean getActivity()
    {
        Activity onGoingActivity = activityService.getActivityGoing();
        return new ResultBean(Constant.SUCCESS, onGoingActivity);
    }

    //暂时从页面接收报名的人，校验放在网关层，活动应该无状态
    //public void joinActivity(@RequestBody Map<String, String> payload) throws HGException
    @RequestMapping(value = "/activity/join", method = RequestMethod.POST)
    public void joinActivity(@Validated @RequestBody JoinActivityBean payload) throws HGException
    {
        activityService.handlerJoinActivity(payload);
        return;
    }

    //取消报名
    @RequestMapping(value = "/activity/join/{username}/{activityID}", method = RequestMethod.DELETE)
    public void cancelParticipateActivity(@PathVariable String username, @PathVariable String activityID) throws HGException
    {
        if (username == null || username.isEmpty())
        {
            throw new HGException(Constant.RET_CODE_INPUT_ILLEGAL, "cancelParticipateActivity username is empty");
        }
        if (activityID == null || !ValidateUtil.isNum(activityID))
        {
            throw new HGException(Constant.RET_CODE_INPUT_ILLEGAL, "cancelParticipateActivity activityID is error");
        }
        //查找活动
        //根据id找活动信息
        Activity activity = activityRepository.findOne(Long.valueOf(activityID));
        if (activity == null)
        {
            throw new HGException(Constant.RETCODE_NO_RECORD, "cancelParticipateActivity -can not find activity");
        }

        //确认是否有用户信息
        String participateUsernames = activity.getParticipateUsers();
        if(participateUsernames == null || participateUsernames.isEmpty()){
            throw new HGException(Constant.RETCODE_USR_NO_JOIN_ACTIVITY,"no user join to activity = " + activityID);
        }

        String[] subStrs = participateUsernames.split(",");
        ArrayList<String> filterRemoveUserList = new ArrayList<String>(10);
        for(String subStr:subStrs){
            String[] starStrings = subStr.split("*");
            if(!starStrings[0].equals(username)){
                filterRemoveUserList.add(subStr);
            }
        }

        String filterRemoveUsetString =String.join(", ", filterRemoveUserList);
        activity.setParticipateUsers(filterRemoveUsetString);
    }

    @RequestMapping(value = "/activity", method = RequestMethod.POST)
    public boolean createActivity(@RequestBody Map<String, String> payload)
    {

        // TODO 校验入口参数
        System.out.println("input value " + payload);
        String beginTime = payload.get("begin_time");
        Timestamp timestamp = null;

        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date parsedDate = dateFormat.parse(beginTime);
            timestamp = new Timestamp(parsedDate.getTime());
        } catch (Exception e)
        { //this generic but you can control another types of exception
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
        activity.setState(Constant.ACTIVITY_STATE.ONGOING.ordinal());

        try
        {
            activityRepository.save(activity);
        } catch (Exception ex)
        {
            System.out.println("save activity  failed " + ex);
            return false;
        }
        return true;
    }


    //查询所有的活动信息
    @RequestMapping(value = "/activity", method = RequestMethod.GET)
    public @ResponseBody
    ResultBean queryAllActivities()
    {
        Iterable activities = activityRepository.findAll();
        ResultBean result = new ResultBean(Constant.SUCCESS, activities);
        return result;
    }

    //已经结束的活动不允许删除
    @RequestMapping(value = "/activity/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    ResultBean delete(@PathVariable Long id)
    {
        Integer retCode = 1;
        try
        {
            activityRepository.delete(id);
            retCode = Constant.SUCCESS;
        } catch (Exception ex)
        {
            System.out.println("delete court failed ,ex = " + ex);
        }
        ResultBean result = new ResultBean(retCode, null);
        return result;
    }
}
