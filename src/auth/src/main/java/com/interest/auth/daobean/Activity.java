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
    private String participateUsers;
    private Integer chargeTotal;

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
}
