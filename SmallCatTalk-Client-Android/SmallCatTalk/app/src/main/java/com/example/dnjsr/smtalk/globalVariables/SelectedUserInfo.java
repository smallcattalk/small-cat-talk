package com.example.dnjsr.smtalk.globalVariables;

import android.app.Application;

import com.example.dnjsr.smtalk.info.User;
import com.example.dnjsr.smtalk.info.UserInfo;

public class SelectedUserInfo extends Application {

    private static User user = new User();
    public static User getUser() {
        return user;
    }
    public static void setUser(User user) {
        SelectedUserInfo.user = user;
    }

}
