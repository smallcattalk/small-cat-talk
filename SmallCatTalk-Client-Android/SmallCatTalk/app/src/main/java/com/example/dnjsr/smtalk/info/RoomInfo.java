package com.example.dnjsr.smtalk.info;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RoomInfo  {
    String roomName;
    String lastChat;
    int unreadCount;
    List<UserInfo> usersList;
    String _id;

    public RoomInfo(String roomName, int unreadCount) {
        this.roomName = roomName;
        this.unreadCount = unreadCount;
    }

    public String getLastChat() {
        return lastChat;
    }

    public void setLastChat(String lastChat) {
        this.lastChat = lastChat;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public List<UserInfo> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<UserInfo> usersList) {
        this.usersList = usersList;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

