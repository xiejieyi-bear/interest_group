package com.interest.auth.service;

import com.interest.auth.Constant;
import com.interest.auth.bean.JoinActivityBean;
import com.interest.auth.dao.ActivityRepository;
import com.interest.auth.dao.UserRepository;
import com.interest.auth.daobean.Activity;
import com.interest.auth.daobean.User;
import com.interest.auth.util.HGException;
import com.interest.auth.util.Security;
import com.interest.auth.util.ValidateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * 类描述：
 *
 * @author
 */
@Component
public class ActivityService implements  IActivityService
{
    protected Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public Activity getActivityGoing()
    {
        Integer goingState = 0;

        Iterable<Activity> goingActivities= activityRepository.findAllByState(goingState);

        if(goingActivities == null)
        {
            return null;
        }
        Long size = goingActivities.spliterator().getExactSizeIfKnown();
        if(size==0)
        {
            return null;
        }
        //暂时获取第一个
        Iterator<Activity> it = goingActivities.iterator();
        Activity activity = it.next();
        logger.info("get on going activity = "+ activity);
        return activity;
    }

    @Override
    public void handlerJoinActivity(JoinActivityBean payload) throws HGException
    {
        Long activityID =Long.valueOf(payload.getActivity_id());
        String username = payload.getUsername();
        Integer participateNum = payload.getNums();

        //根据id找活动信息
        Activity activity = activityRepository.findOne(activityID);
        if(activity == null)
        {
            throw new HGException(Constant.RETCODE_NO_RECORD, "handlerJoinActivity -can not find activity");
        }

        //添加报名用户信息
        String username2activity = username;
        User user = userRepository.findByUsername(username);
        if(user == null)
        {
            throw new HGException(Constant.RETCODE_NO_RECORD, "handlerJoinActivity -can no find user");
        }
        String nickname = user.getNickname();
        String nickname2activity = nickname;

        //报名人数超过1个，用 “username*个数” 的方式呈现
        if(participateNum > 1)
        {
            nickname2activity = nickname+"*"+String.valueOf(participateNum);
            username2activity = username+"*"+String.valueOf(participateNum);
        }
        String participateUsers = activity.getParticipateUsers();
        String participateNicknames = activity.getParticipateNicknames();
        if(null == participateUsers || participateUsers.isEmpty())
        {
            activity.setParticipateUsers(username2activity);
            activity.setParticipateNicknames(nickname2activity);
        }
        else {
            //多个用户之间，用英文逗号分隔
            activity.setParticipateUsers(participateUsers+","+username2activity);
            activity.setParticipateNicknames(participateNicknames+","+nickname2activity);
        }
        activityRepository.save(activity);
        logger.info("save activity success");
    }
}
