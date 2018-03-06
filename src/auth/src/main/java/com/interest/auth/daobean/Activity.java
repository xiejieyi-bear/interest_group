package com.interest.auth.daobean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity // This tells Hibernate to make a table out of this class
public class Activity
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Timestamp beginTime;
    private Integer duration;
    private Long courtID;
    private String courtName;
    private Integer courtNums;
    private String participateNicknames;

    public Integer getCourtNums()
    {
        return courtNums;
    }

    public void setCourtNums(Integer courtNums)
    {
        this.courtNums = courtNums;
    }

    public String getParticipateNicknames()
    {
        return participateNicknames;
    }

    public void setParticipateNicknames(String participateNicknames)
    {
        this.participateNicknames = participateNicknames;
    }

    private String participateUsers;
    private Integer chargeTotal;
    //活动状态：0 ：报名中 1：已结束
    private Integer state;

    public Integer getState()
    {
        return state;
    }

    public void setState(Integer state)
    {
        this.state = state;
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

    public Long getCourtID()
    {
        return courtID;
    }

    public void setCourtID(Long courtID)
    {
        this.courtID = courtID;
    }

    public String getCourtName()
    {
        return courtName;
    }

    public void setCourtName(String courtName)
    {
        this.courtName = courtName;
    }

    public String getParticipateUsers()
    {
        return participateUsers;
    }

    public void setParticipateUsers(String participateUsers)
    {
        this.participateUsers = participateUsers;
    }

    public Integer getChargeTotal()
    {
        return chargeTotal;
    }

    public void setChargeTotal(Integer chargeTotal)
    {
        this.chargeTotal = chargeTotal;
    }

    @Override
    public String toString()
    {
        return "Activity{" +
                "id=" + id +
                ", beginTime=" + beginTime +
                ", duration=" + duration +
                ", courtID=" + courtID +
                ", courtName='" + courtName + '\'' +
                ", participateUsers='" + participateUsers + '\'' +
                ", chargeTotal=" + chargeTotal +
                ", state=" + state +
                '}';
    }
}
