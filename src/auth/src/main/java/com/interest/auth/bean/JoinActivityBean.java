package com.interest.auth.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * 类描述：
 *
 * @author
 */
public class JoinActivityBean
{
    private int activity_id;

    @Max(10)
    private int nums;

    @NotBlank(message="username is empty")
    @Size(max=256,message = "username is exceed of max length")
    private String username;

    public int getActivity_id()
    {
        return activity_id;
    }

    public void setActivity_id(int activity_id)
    {
        this.activity_id = activity_id;
    }

    public int getNums()
    {
        return nums;
    }

    public void setNums(int nums)
    {
        this.nums = nums;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public String toString()
    {
        return "JoinActivityBean{" +
                "activity_id=" + activity_id +
                ", nums=" + nums +
                ", username='" + username + '\'' +
                '}';
    }
}
