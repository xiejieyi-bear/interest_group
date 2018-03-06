package com.interest.auth.service;

import com.interest.auth.bean.JoinActivityBean;
import com.interest.auth.daobean.Activity;
import com.interest.auth.util.HGException;

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
}

