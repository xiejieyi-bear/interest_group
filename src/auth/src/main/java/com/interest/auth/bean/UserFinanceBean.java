package com.interest.auth.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * 类描述：
 *
 * @author
 */
public class UserFinanceBean
{
    @Size(max=256,message = "username is exceed of max length")
    private String username;

    @Max(1000)
    private int amount;

    @Max(5)
    private int type;

    @Size(max=256,message = "remark is exceed of max length")
    private String remark;

    private Long activityID;

    public Long getActivityID()
    {
        return activityID;
    }

    public void setActivityID(Long activityID)
    {
        this.activityID = activityID;
    }

    public String getUsername()
    {
        return username;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }
}
