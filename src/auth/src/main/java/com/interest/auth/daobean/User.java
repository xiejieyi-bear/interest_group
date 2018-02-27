package com.interest.auth.daobean;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String username;
    private String salt;
    private String encrypt;
    @Column(unique=true)
    private String usermark="";
    private String telephone="";
    private String email="";
    private Integer role=1;

    /**
     * 必须要有，否则查询时会报错：No default constructor for entity
     */
    public User()
    {
    }

    public User(String salt, String encrypt, String usermark)
    {
        this.salt = salt;
        this.encrypt = encrypt;
        this.usermark = usermark;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
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

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public String getEncrypt()
    {
        return encrypt;
    }

    public void setEncrypt(String encrypt)
    {
        this.encrypt = encrypt;
    }

    public String getUsermark()
    {
        return usermark;
    }

    public void setUsermark(String usermark)
    {
        this.usermark = usermark;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getRole()
    {
        return role;
    }

    public void setRole(Integer role)
    {
        this.role = role;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", salt='" + salt + '\'' +
                ", encrypt='" + encrypt + '\'' +
                ", usermark='" + usermark + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
