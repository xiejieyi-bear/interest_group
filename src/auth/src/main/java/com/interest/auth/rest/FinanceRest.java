package com.interest.auth.rest;

import com.interest.auth.Constant;
import com.interest.auth.bean.*;
import com.interest.auth.dao.ActivityRepository;
import com.interest.auth.dao.UserFinanceRepository;
import com.interest.auth.dao.UserRepository;
import com.interest.auth.daobean.User;
import com.interest.auth.daobean.UserFinance;
import com.interest.auth.util.HGException;
import com.interest.auth.util.ValidateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * 类描述：场地管理类
 *
 * @author
 */
@RestController
public class FinanceRest
{
    protected Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserFinanceRepository userFinanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;


    // 用户充值
    @RequestMapping(value = "/finance/recharge/user", method = RequestMethod.POST)
    public @ResponseBody
    ResultBean userCharge(@RequestBody UserFinanceBean payload) throws HGException
    {
        String username = payload.getUsername();

        UserFinance userFinance = new UserFinance();
        userFinance.setType(Constant.FINANCE_TYPE_RECHARGE);
        userFinance.setUsername(username);
        userFinance.setRemark(payload.getRemark());
        userFinance.setAmount(payload.getAmount());

        Calendar now = Calendar.getInstance();
        userFinance.setTime(new Timestamp(now.getTimeInMillis()));

        //校验用户名是否存在
        User user = userRepository.findByUsername(username);
        if (user == null)
        {
            throw new HGException(Constant.RETCODE_NO_RECORD, "usercharge -can not find user");
        }

        userFinanceRepository.save(userFinance);
        logger.info("user charge success");
        return new ResultBean(Constant.SUCCESS, null);
    }


    //用户支出
    @RequestMapping(value = "/finance/expenditure/user", method = RequestMethod.POST)
    public @ResponseBody
    ResultBean userExpenditure(@RequestBody UserFinanceBean payload) throws HGException
    {
        String username = payload.getUsername();
        UserFinance userFinance = new UserFinance();

        userFinance.setType(Constant.FINANCE_TYPE_EXPENDITURE);
        userFinance.setUsername(payload.getUsername());
        userFinance.setRemark(payload.getRemark());
        Calendar now = Calendar.getInstance();
        userFinance.setTime(new Timestamp(now.getTimeInMillis()));
        Integer amount = payload.getAmount();
        amount = 0-amount;

        //判断活动是否存在
        Long activityID = payload.getActivityID();
        if(!activityRepository.exists(activityID)){
            throw new HGException(Constant.RET_CODE_INPUT_ILLEGAL, "userExpenditure can not find activity ");
        }
        userFinance.setActivityID(payload.getActivityID());

        userFinance.setAmount(amount);
        //校验用户名是否存在
        User user = userRepository.findByUsername(username);
        if (user == null)
        {
            throw new HGException(Constant.RETCODE_NO_RECORD, "userExpenditure -can not find user");
        }

        userFinanceRepository.save(userFinance);
        logger.info("user expenditure success");
        return new ResultBean(Constant.SUCCESS, null);
    }

    //查询某个用户的所有充值记录
    @RequestMapping(value = "/finance/recharge/user/{username}", method = RequestMethod.GET)
    public @ResponseBody
    ResultBean queryUserREChargeRecords(@PathVariable String username) throws
            HGException
    {
        ValidateUtil.validateStringNull(username,"username");
        Iterable<UserFinance> userFinances = userFinanceRepository.findAllByUsernameAndType(username,Constant.FINANCE_TYPE_RECHARGE);
        return new ResultBean(Constant.SUCCESS, userFinances);
    }

    //查询某个用户的所有支出记录
    @RequestMapping(value = "/finance/expenditure/user/{username}", method = RequestMethod.GET)
    public @ResponseBody
    ResultBean queryExpenditureRecords(@PathVariable String username) throws
            HGException
    {
        ValidateUtil.validateStringNull(username,"username");
        Iterable<UserFinance> userFinances = userFinanceRepository.findAllByUsernameAndType(username,Constant
                .FINANCE_TYPE_EXPENDITURE);
        return new ResultBean(Constant.SUCCESS, userFinances);
    }

}
