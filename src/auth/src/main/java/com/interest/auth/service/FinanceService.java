package com.interest.auth.service;

import com.interest.auth.Constant;
import com.interest.auth.bean.ActivityBean;
import com.interest.auth.bean.JoinActivityBean;
import com.interest.auth.bean.UserFinanceBean;
import com.interest.auth.dao.ActivityRepository;
import com.interest.auth.dao.CourtRepository;
import com.interest.auth.dao.UserFinanceRepository;
import com.interest.auth.dao.UserRepository;
import com.interest.auth.daobean.*;
import com.interest.auth.util.HGException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 类描述：
 *
 * @author
 */
@Component
public class FinanceService implements IFinanceService
{
    protected Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;


    @Autowired
    private UserFinanceRepository userFinanceRepository;

    @Override
    public void userExpenditure(UserFinanceBean payload) throws HGException
    {

        UserFinance userFinance = new UserFinance();

        //判断活动是否存在
        Long activityID = payload.getActivityID();
        if(!activityRepository.exists(activityID)){
            throw new HGException(Constant.RET_CODE_INPUT_ILLEGAL, "userExpenditure can not find activity ");
        }
        userFinance.setActivityID(payload.getActivityID());

        //校验用户名是否存在
        String username = payload.getUsername();
        User user = userRepository.findByUsername(username);
        if (user == null)
        {
            throw new HGException(Constant.RETCODE_NO_RECORD, "userExpenditure -can not find user");
        }
        userFinance.setUsername(payload.getUsername());

        userFinance.setType(Constant.FINANCE_TYPE_EXPENDITURE);

        userFinance.setRemark(payload.getRemark());
        Calendar now = Calendar.getInstance();
        userFinance.setTime(new Timestamp(now.getTimeInMillis()));
        float amount = payload.getAmount();
        amount = 0-amount;
        userFinance.setAmount(amount);

        userFinanceRepository.save(userFinance);
        return;
    }
}
