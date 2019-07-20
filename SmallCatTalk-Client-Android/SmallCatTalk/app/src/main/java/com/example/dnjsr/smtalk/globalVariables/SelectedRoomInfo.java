package com.example.dnjsr.smtalk.globalVariables;

import com.example.dnjsr.smtalk.info.RoomInfo;

public class SelectedRoomInfo {
    private static RoomInfo selectedRoomInfo = new RoomInfo("",0 );

    public static RoomInfo getSelectedRoomInfo() {
        return selectedRoomInfo;
    }

    public static void setSelectedRoomInfo(RoomInfo selectedRoomInfo) {
        SelectedRoomInfo.selectedRoomInfo = selectedRoomInfo;
    }
}
