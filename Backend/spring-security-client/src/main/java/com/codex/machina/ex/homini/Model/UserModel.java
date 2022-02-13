package com.codex.machina.ex.homini.Model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class UserModel
{
    private String firstname;
    private String lastname;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthday;
    private String email;
    private String username;
    private String password;
    private String matchingPassword;

    public UserModel()
    {}

    public UserModel(String firstname, String lastname, Date birthday, String email, String username,String password, String matchingPassword)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.email = email;
        this.username = username;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }


    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getMatchingPassword()
    {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword)
    {
        this.matchingPassword = matchingPassword;
    }
}
