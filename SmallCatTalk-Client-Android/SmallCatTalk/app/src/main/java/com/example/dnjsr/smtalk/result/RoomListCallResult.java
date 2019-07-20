package com.example.dnjsr.smtalk.result;

import com.example.dnjsr.smtalk.info.RoomInfo;
import com.example.dnjsr.smtalk.info.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class RoomListCallResult {

    int result;
    ArrayList<RoomInfo> roomsList;
    List<List<UserInfo>> usersLists;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public ArrayList<RoomInfo> getRoomsList() {
        return roomsList;
    }

    public void setRoomsList(ArrayList<RoomInfo> roomsList) {
        this.roomsList = roomsList;
    }

    public List<List<UserInfo>> getUsersLists() {
        return usersLists;
    }

    public void setUsersLists(List<List<UserInfo>> usersLists) {
        this.usersLists = usersLists;
    }
}
