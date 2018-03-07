package com.interest.auth.daobean;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * 描述： 存储活动报名用户信息
 *
 * @author xiejieyi
 * @date 3/7/2018
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "activity_participate")
public class ActivityParticipate
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String username;
    private String nickname;
    private Integer participateNumbers;

    @ManyToOne
    @JoinColumn(name = "activityID")
    @JsonBackReference
    private Activity activity;

    public ActivityParticipate()
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

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public Integer getParticipateNumbers()
    {
        return participateNumbers;
    }

    public void setParticipateNumbers(Integer participateNumbers)
    {
        this.participateNumbers = participateNumbers;
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
