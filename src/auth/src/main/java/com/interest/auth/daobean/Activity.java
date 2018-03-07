package com.interest.auth.daobean;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class Activity
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    //总费用信息，手工输入，以支持自定义使用多少公共经费
    private Integer chargeTotal;

    //活动状态：0 ：报名中 1：已结束
    private Integer state;

    //活动描述信息
    private String remark;

    // 活动报名用户信息
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true,mappedBy = "activity", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ActivityParticipate> participates;

    // 活动场地信息
    //fetch = FetchType.EAGER是为了避免lazily initialize
    //orphanRemoval = true 允许删除关联表的信息
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true,mappedBy = "activity", cascade = CascadeType.ALL)
    @JsonManagedReference //加上这个注解，否则转换成json的时候，会循环转换
    private Set<ActivityCourt> courts;

    public Activity()
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

    public Integer getChargeTotal()
    {
        return chargeTotal;
    }

    public void setChargeTotal(Integer chargeTotal)
    {
        this.chargeTotal = chargeTotal;
    }

    public Integer getState()
    {
        return state;
    }

    public void setState(Integer state)
    {
        this.state = state;
    }


    @Override
    public String toString()
    {
        String result =  "Activity{" +
                "id=" + id +
                ", chargeTotal=" + chargeTotal +
                ", state=" + state +
                ", remark='" + remark + '\'' +
                '}';

        if (participates != null) {
            for(ActivityParticipate book : participates) {
                result += String.format(
                        "participate[id=%d, username='%s']%n",
                        book.getId(), book.getUsername());
            }
        }
        if(courts != null){
            for(ActivityCourt court:courts){
                result += String.format(
                        "court[id=%d, username='%s']%n",
                        court.getId(), court.getCourtName());

            }
        }
        return result;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Set<ActivityParticipate> getParticipates()
    {
        return participates;
    }

    public void setParticipates(Set<ActivityParticipate> participates)
    {
        this.participates = participates;
    }

    public Set<ActivityCourt> getCourts()
    {
        return courts;
    }

    public void setCourts(Set<ActivityCourt> courts)
    {
        this.courts = courts;
    }
}
