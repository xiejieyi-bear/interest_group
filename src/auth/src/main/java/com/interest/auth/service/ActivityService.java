package com.interest.auth.service;

import com.interest.auth.Constant;
import com.interest.auth.bean.ActivityBean;
import com.interest.auth.bean.JoinActivityBean;
import com.interest.auth.bean.UserFinanceBean;
import com.interest.auth.dao.ActivityRepository;
import com.interest.auth.dao.CourtRepository;
import com.interest.auth.dao.UserRepository;
import com.interest.auth.daobean.*;
import com.interest.auth.util.HGException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 类描述：
 *
 * @author
 */
@Component
public class ActivityService implements IActivityService
{
    protected Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private IFinanceService financeService;


    @Override
    public Activity getActivityGoing()
    {
        Integer goingState = 0;

        Iterable<Activity> goingActivities = activityRepository.findAllByState(goingState);

        if (goingActivities == null)
        {
            return null;
        }
        Long size = goingActivities.spliterator().getExactSizeIfKnown();
        if (size == 0)
        {
            return null;
        }
        //暂时获取第一个
        Iterator<Activity> it = goingActivities.iterator();
        Activity activity = it.next();
        logger.info("get on going activity = " + activity);
        return activity;
    }

    @Override
    public void handlerJoinActivity(JoinActivityBean payload) throws HGException
    {
        Long activityID = Long.valueOf(payload.getActivity_id());
        String username = payload.getUsername();
        Integer participateNum = payload.getNums();

        //根据id找活动信息
        Activity activity = activityRepository.findOne(activityID);
        if (activity == null)
        {
            throw new HGException(Constant.RETCODE_NO_RECORD, "handlerJoinActivity -can not find activity");
        }

        User user = userRepository.findByUsername(username);
        if (user == null)
        {
            throw new HGException(Constant.RETCODE_NO_RECORD, "handlerJoinActivity -can no find user");
        }
        //是否重复报名？
        Set<ActivityParticipate> participates = activity.getParticipates();
        for (ActivityParticipate participate : participates)
        {
            String usernameInActivity = participate.getUsername();
            if (username.equals(usernameInActivity))
            {
                throw new HGException(Constant.RETCODE_USER_ALREADY_JOIN, "handlerJoinActivity user already join");
            }
        }
        ActivityParticipate newUser = new ActivityParticipate();
        newUser.setUsername(username);
        newUser.setNickname(user.getNickname());
        newUser.setParticipateNumbers(participateNum);
        newUser.setActivity(activity);

        participates.add(newUser);
        activity.setParticipates(participates);

        activityRepository.save(activity);
        logger.info("save activity success");
    }


    @Override
    public void createActivity(ActivityBean payload) throws HGException
    {
        List<ActivityBean.CourtBean> courts = payload.getCourts();

        if(courts == null  || courts.size() == 0){
            throw new HGException(Constant.RET_CODE_INPUT_ILLEGAL,"courts is empty");
        }
        Activity activity = new Activity();
        activity.setRemark(payload.getRemark());
        activity.setChargeTotal(payload.getChargeTotal());
        activity.setState(Constant.ACTIVITY_STATE.ONGOING.ordinal());

        ActivityCourt courtDao;
        Set<ActivityCourt> activityCourts = new HashSet<>(10);

        for (ActivityBean.CourtBean courtBean : courts)
        {
            courtDao = new ActivityCourt();
            String beginTime = courtBean.getBeginTime();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date parsedDate;
            try
            {
                parsedDate = dateFormat.parse(beginTime);
            } catch (ParseException e)
            {
                throw new HGException(Constant.RET_CODE_INPUT_ILLEGAL, "dataTime input is illegal");
            }
            Timestamp timestamp = new Timestamp(parsedDate.getTime());

            courtDao.setBeginTime(timestamp);
            courtDao.setDuration(courtBean.getDuration());
            courtDao.setCourtNums(courtBean.getCourtNums());

            Long courtID = courtBean.getCourtID();
            Court court = courtRepository.findOne(courtID);
            String courtName = court.getName();
            courtDao.setCourtName(courtName);
            courtDao.setActivity(activity);
            activityCourts.add(courtDao);
        }
        activity.setCourts(activityCourts);
        activityRepository.save(activity);
    }

    public void cancelJoinActivity(String username,String activityID) throws HGException
    {
        //根据id找活动信息
        Activity activity = activityRepository.findOne(Long.valueOf(activityID));
        if (activity == null)
        {
            throw new HGException(Constant.RETCODE_NO_RECORD, "cancelParticipateActivity -can not find activity");
        }

        User user = userRepository.findByUsername(username);
        if (user == null)
        {
            throw new HGException(Constant.RETCODE_NO_RECORD, "handlerJoinActivity -can no find user");
        }

        //是否重复报名？
        Set<ActivityParticipate> participates = activity.getParticipates();
        for (ActivityParticipate participate : participates)
        {
            String usernameInActivity = participate.getUsername();
            if (username.equals(usernameInActivity))
            {
                logger.info("delete participate user begin = " + username);
                participates.remove(participate);
                break;
            }
        }
        activity.setParticipates(participates);
        activityRepository.saveAndFlush(activity);
    }

    @Transactional
    public void handleSettlement(Activity activity) throws HGException
    {
        // 活动标志为end
        activity.setState(Constant.ACTIVITY_STATE.END.ordinal());

        // 统计分摊费用，更新表格
        activity.getParticipates();
        Integer chargeTotal = activity.getChargeTotal();

        Set<ActivityParticipate> participates = activity.getParticipates();
        List<String> usernames = new ArrayList<String>(10);
        float chargeAverage = 0.0f;
        if (null != participates && participates.size() >= 1)
        {
            Integer userNums = 0;
            for (ActivityParticipate participate : participates)
            {
                userNums = userNums + participate.getParticipateNumbers();
                usernames.add(participate.getUsername());
            }

            chargeAverage = chargeTotal / userNums;
            DecimalFormat df = new DecimalFormat(".00");
            chargeAverage = Float.valueOf(df.format(chargeAverage));

        }
        //TODO 需要用到事务
        //TODO 暂时无性能问题，先用遍历的方式，后期寻求使用拼接SQL
        UserFinanceBean payload;
        for (String username : usernames)
        {
            payload = new UserFinanceBean();
            payload.setActivityID(activity.getId());
            payload.setAmount(chargeAverage);
            payload.setRemark("Settlement auto when activity is ended");
            payload.setUsername(username);
            financeService.userExpenditure(payload);
        }
        activityRepository.save(activity);
    }
}
