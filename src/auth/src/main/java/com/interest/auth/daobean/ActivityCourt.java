package com.interest.auth.daobean;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 描述： 存储活动的场地信息
 *
 * @author xiejieyi
 * @date 3/7/2018
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "activity_courts")
public class ActivityCourt
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    //场地开始时间
    private Timestamp beginTime;
    //持续时间
    private Integer duration;

    //直接存储场地名称，如果场地发生变化，需要重新创建活动，或者重新选择场地信息
    private  String courtName;

    //总共有几片场地
    private Integer courtNums;

    @ManyToOne
    @JoinColumn(name = "activityID")
    @JsonBackReference//加上这个注解，否则转换成json的时候，会循环转换
    private Activity activity;

    public ActivityCourt()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Timestamp getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime)
    {
        this.beginTime = beginTime;
    }

    public Integer getDuration()
    {
        return duration;
    }

    public void setDuration(Integer duration)
    {
        this.duration = duration;
    }

    public String getCourtName()
    {
        return courtName;
    }

    public void setCourtName(String courtName)
    {
        this.courtName = courtName;
    }

    public Integer getCourtNums()
    {
        return courtNums;
    }

    public void setCourtNums(Integer courtNums)
    {
        this.courtNums = courtNums;
    }

    public Activity getActivity()
    {
        return activity;
    }

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }
}
