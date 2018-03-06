package com.interest.auth.dao;

import com.interest.auth.daobean.Activity;
import com.interest.auth.daobean.Court;
import org.springframework.data.repository.CrudRepository;

/**
 * 类描述：场地管理Repository
 *
 * @author
 */
public interface ActivityRepository extends CrudRepository<Activity, Long>
{
    //根据状态查找活动
    Iterable<Activity> findAllByState(Integer activityState);
}
