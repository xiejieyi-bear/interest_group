package com.interest.auth.rest;

import com.interest.auth.Constant;
import com.interest.auth.bean.ActivityBean;
import com.interest.auth.bean.JoinActivityBean;
import com.interest.auth.bean.ResultBean;
import com.interest.auth.dao.ActivityRepository;
import com.interest.auth.dao.CourtRepository;
import com.interest.auth.daobean.Activity;
import com.interest.auth.daobean.ActivityCourt;
import com.interest.auth.daobean.Court;
import com.interest.auth.service.IActivityService;
import com.interest.auth.util.HGException;
import com.interest.auth.util.ValidateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private IActivityService activityService;

    //查询最近上线的活动
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
    public @ResponseBody
    ResultBean joinActivity(@Validated @RequestBody JoinActivityBean payload) throws HGException
    {
        activityService.handlerJoinActivity(payload);
        return new ResultBean(Constant.SUCCESS,null);
    }

    //取消报名
    @RequestMapping(value = "/activity/join/{username}/{activityID}", method = RequestMethod.DELETE)
    public @ResponseBody
    ResultBean cancelParticipateActivity(@PathVariable String username, @PathVariable String activityID) throws HGException
    {
        if (username == null || username.isEmpty())
        {
            throw new HGException(Constant.RET_CODE_INPUT_ILLEGAL, "cancelParticipateActivity username is empty");
        }
        if (activityID == null || !ValidateUtil.isNum(activityID))
        {
            throw new HGException(Constant.RET_CODE_INPUT_ILLEGAL, "cancelParticipateActivity activityID is error");
        }

        activityService.cancelJoinActivity(username,activityID);
        logger.info("cancel join activity success = " + username);
        return new ResultBean(Constant.SUCCESS,null);
    }

    /**
     * 方法描述：创建活动
     *
     * @author xiejieyi
     * @date 3/7/2018
     */
    @RequestMapping(value = "/activity", method = RequestMethod.POST)
    public @ResponseBody ResultBean createActivity(@RequestBody ActivityBean payload) throws HGException
    {
        activityService.createActivity(payload);
        logger.info("create activity success");
        return new ResultBean(Constant.SUCCESS,null);
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
