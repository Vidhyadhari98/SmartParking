package com.su.iot.smartparkingapi.request;

import java.io.Serializable;

public class UserLoginData implements Serializable {

    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public UserLoginData setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginData setPassword(String password) {
        this.password = password;
        return this;
    }
}
