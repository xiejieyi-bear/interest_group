package com.interest.auth.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 类描述：
 *
 * @author
 */
public class ActivityBean
{

    private int chargeTotal;

    @Size(max=256, message = "remark is exceed of max length")
    private String remark;

    //暂时只允许1个场地信息
    @Size(max=1)
    private List<CourtBean> courts;

    public static class CourtBean
    {
        @Size(max=20, message = "beginTime is exceed of max length")
        private String beginTime;

        @Max(1000)
        private Integer duration;

        @Max(10)
        private Integer courtNums;

        private Long courtID;

        public String getBeginTime()
        {
            return beginTime;
        }

        public void setBeginTime(String beginTime)
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

        public Integer getCourtNums()
        {
            return courtNums;
        }

        public void setCourtNums(Integer courtNums)
        {
            this.courtNums = courtNums;
        }

        public Long getCourtID()
        {
            return courtID;
        }

        public void setCourtID(Long courtID)
        {
            this.courtID = courtID;
        }

        @Override
        public String toString()
        {
            return "CourtBean{" +
                    "beginTime='" + beginTime + '\'' +
                    ", duration=" + duration +
                    ", courtNums=" + courtNums +
                    ", courtID=" + courtID +
                    '}';
        }
    }

    public int getChargeTotal()
    {
        return chargeTotal;
    }

    public void setChargeTotal(int chargeTotal)
    {
        this.chargeTotal = chargeTotal;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public List<ActivityBean.CourtBean> getCourts()
    {
        return courts;
    }

    public void setCourts(List<ActivityBean.CourtBean> courts)
    {
        this.courts = courts;
    }

    @Override
    public String toString()
    {
        return "ActivityBean{" +
                "chargeTotal=" + chargeTotal +
                ", remark='" + remark + '\'' +
                ", courts=" + courts +
                '}';
    }
}
