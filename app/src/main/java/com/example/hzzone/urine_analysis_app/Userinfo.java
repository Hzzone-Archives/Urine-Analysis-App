package com.example.hzzone.urine_analysis_app;

/**
 * Created by Hzzone on 2017/4/6.
 */

public class Userinfo {
    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {

        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    private String email;
    private String userName;
    private String password;

    public Userinfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Userinfo(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }


}
