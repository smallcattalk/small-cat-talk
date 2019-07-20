package com.example.dnjsr.smtalk.info;

public class RoomsList {
    String roomId;
    String friendId;

    public RoomsList(String roomId, String friendId) {
        this.roomId = roomId;
        this.friendId = friendId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
