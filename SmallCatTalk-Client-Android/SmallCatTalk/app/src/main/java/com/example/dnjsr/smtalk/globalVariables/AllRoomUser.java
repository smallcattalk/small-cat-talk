package com.example.dnjsr.smtalk.globalVariables;

import com.example.dnjsr.smtalk.info.UserInfo;

import java.util.HashMap;

public class AllRoomUser {
    public static HashMap<String ,UserInfo > allRoomUsers = new HashMap<>();

    public static HashMap<String, UserInfo> getAllRoomUsers() {
        return allRoomUsers;
    }

    public static void setAllRoomUsers(HashMap<String, UserInfo> allRoomUsers) {
        AllRoomUser.allRoomUsers = allRoomUsers;
    }
}
