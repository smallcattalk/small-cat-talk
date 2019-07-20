package com.example.dnjsr.smtalk.Tool;

import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.FriendsInfo;
import com.example.dnjsr.smtalk.info.RoomInfo;
import com.example.dnjsr.smtalk.info.RoomsList;
import com.example.dnjsr.smtalk.info.UserInfo;

public class Tool {
    public String getRoomIdBy_Id(String _id){
        String result = "";
        for (RoomsList roomsList: CurrentUserInfo.getUser().getUserInfo().getRoomsList()) {
            if (roomsList.getFriendId().equals(_id)){
                result = roomsList.getRoomId();
            }
        }
        return result;
    }

    public String get_idByRoomId(String RoomId){
        String result = "";
        for (RoomsList roomsList: CurrentUserInfo.getUser().getUserInfo().getRoomsList()) {
            if (roomsList.getRoomId().equals(RoomId)){
                result = roomsList.getFriendId();
            }
        }
        return result;
    }

    public String getOther_Id(RoomInfo roomInfo){
        int index=-2;
        String result="";
        if(roomInfo.getUsersList().size()==2){
            if (roomInfo.getUsersList().get(0).get_id().equals(CurrentUserInfo.getUser().getUserInfo().get_id()))
                index = 1;
            else if (roomInfo.getUsersList().get(1).get_id().equals(CurrentUserInfo.getUser().getUserInfo().get_id()))
                index = 0;
            else
                index = -1;
        }
        result = roomInfo.getUsersList().get(index).get_id();
        return result;
    }
}
