package com.interest.auth.daobean;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 描述： 存储用户的财务信息
 *
 * @author xiejieyi
 * @date 3/7/2018
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "user_finance")
public class UserFinance
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String username;

    //资金类型：0：recharge 充值(income-收入） 1：expenditure 支出
    private Integer type;
    private float amount;
    // 备注说明
    private String remark;
    private Timestamp time;

    // 支出关联活动ID
    private Long activityID;

    public UserFinance()
    {
    }

    public Long getActivityID()
    {
        return activityID;
    }

    public void setActivityID(Long activityID)
    {
        this.activityID = activityID;
    }

    public Timestamp getTime()
    {
        return time;
    }

    public void setTime(Timestamp time)
    {
        this.time = time;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
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

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public float getAmount()
    {
        return amount;
    }

    public void setAmount(float amount)
    {
        this.amount = amount;
    }
}
