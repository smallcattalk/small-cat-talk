package com.example.dnjsr.smtalk.result;

import com.example.dnjsr.smtalk.info.ChatObject;

import java.util.List;

public class GetAllChatResult {
    int result;
    List<ChatObject> chats;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<ChatObject> getChats() {
        return chats;
    }

    public void setChats(List<ChatObject> chats) {
        this.chats = chats;
    }
}
