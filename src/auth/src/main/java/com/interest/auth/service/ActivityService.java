package com.interest.auth.service;

import com.interest.auth.Constant;
import com.interest.auth.bean.ActivityBean;
import com.interest.auth.bean.JoinActivityBean;
import com.interest.auth.dao.ActivityRepository;
import com.interest.auth.dao.CourtRepository;
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
public class ActivityService implements IActivityService
{
    protected Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CourtRepository courtRepository;


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
}
