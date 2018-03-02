package com.interest.auth.daobean;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Court
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String addr;
    private Integer charge;
    private String telephone="";
    private Integer balance=1;

    /**
     * 必须要有，否则查询时会报错：No default constructor for entity
     */
    public Court()
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddr()
    {
        return addr;
    }

    public void setAddr(String addr)
    {
        this.addr = addr;
    }

    public Integer getCharge()
    {
        return charge;
    }

    public void setCharge(Integer charge)
    {
        this.charge = charge;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public Integer getBalance()
    {
        return balance;
    }

    public void setBalance(Integer balance)
    {
        this.balance = balance;
    }
}
