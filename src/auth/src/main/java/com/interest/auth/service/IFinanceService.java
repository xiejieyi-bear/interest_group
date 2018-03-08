package com.interest.auth.service;

import com.interest.auth.bean.ActivityBean;
import com.interest.auth.bean.JoinActivityBean;
import com.interest.auth.bean.UserFinanceBean;
import com.interest.auth.daobean.Activity;
import com.interest.auth.util.HGException;

/**
 * 类描述：
 *
 * @author
 */
public interface IFinanceService
{
    void userExpenditure(UserFinanceBean userFinanceBean) throws HGException;
}

