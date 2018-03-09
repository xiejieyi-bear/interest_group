package com.interest.auth.service;

import com.interest.auth.bean.ActivityBean;
import com.interest.auth.bean.JoinActivityBean;
import com.interest.auth.daobean.Activity;
import com.interest.auth.util.HGException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 类描述：
 *
 * @author
 */
public interface IActivityService
{
    Activity getActivityGoing();

    void handlerJoinActivity(JoinActivityBean payload) throws HGException;

    void createActivity(ActivityBean payload) throws HGException;

    void cancelJoinActivity(String username,String activityID) throws HGException;

    @Transactional
    void handleSettlement(Activity activity) throws HGException;
}

