package com.example.dnjsr.smtalk.result;

import com.example.dnjsr.smtalk.info.UserInfo;

public class LoginResult {

    int result;
    UserInfo user;

    public LoginResult(int result, UserInfo user) {
        this.result = result;
        this.user = user;
    }

    public int getResult() {
        return result;
    }

    public UserInfo getUserInfo() {
        return user;
    }
}
