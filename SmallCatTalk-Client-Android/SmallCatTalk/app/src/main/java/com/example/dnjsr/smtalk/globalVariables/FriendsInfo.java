package com.example.dnjsr.smtalk.globalVariables;

import com.example.dnjsr.smtalk.info.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class FriendsInfo {
    public static HashMap<String ,UserInfo > friendsInfo = new HashMap<>();

    public static HashMap<String, UserInfo> getFriendsInfo() {
        return friendsInfo;
    }

    public static void setFriendsInfo(HashMap<String, UserInfo> friendsInfo) {
        FriendsInfo.friendsInfo = friendsInfo;
    }
}
