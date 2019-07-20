package com.example.dnjsr.smtalk.result;

import com.example.dnjsr.smtalk.info.UserInfo;

import java.util.List;

public class FriendListCallResult {
    int result;
    List<UserInfo> friendsList;

    public int getResult() {
        return result;
    }

    public List<UserInfo> getFriendsList() {
        return friendsList;
    }
}
