package com.example.dnjsr.smtalk.result;

import com.example.dnjsr.smtalk.info.UserInfo;

import java.util.List;

public class SearchResult {
    int result;
    List<UserInfo> users;

    public int getResult() {
        return result;
    }

    public List<UserInfo> getUsers() {
        return users;
    }
}