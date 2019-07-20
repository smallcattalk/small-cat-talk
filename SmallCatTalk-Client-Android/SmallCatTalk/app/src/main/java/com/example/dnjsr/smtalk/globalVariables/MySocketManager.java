package com.example.dnjsr.smtalk.globalVariables;

import java.net.URI;

import io.socket.client.Manager;

public class MySocketManager {

    private static Manager manager;

    public static Manager getManager() {
        return manager;
    }

    public static void setManager(Manager manager) {
        MySocketManager.manager = manager;
    }
}
