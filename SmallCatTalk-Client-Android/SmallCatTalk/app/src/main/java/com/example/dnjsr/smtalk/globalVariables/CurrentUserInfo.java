package com.example.dnjsr.smtalk.globalVariables;

import android.graphics.Bitmap;

import com.example.dnjsr.smtalk.info.User;

public class CurrentUserInfo {
    private static User user = new User();
    private static Bitmap groupImage = null;

    public static Bitmap getGroupImage() {
        return groupImage;
    }

    public static void setGroupImage(Bitmap groupImage) {
        CurrentUserInfo.groupImage = groupImage;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        CurrentUserInfo.user = user;
    }
}
