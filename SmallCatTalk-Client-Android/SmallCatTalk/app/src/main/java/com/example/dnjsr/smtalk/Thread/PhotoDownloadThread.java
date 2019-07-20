package com.example.dnjsr.smtalk.Thread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dnjsr.smtalk.ChatRoomActivity;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.info.ChatObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PhotoDownloadThread {

    public void ThreadStart(final ChatObject chatObject, final List<ChatObject> chatObjectList, final Context context){
        Thread getPhotoImage = new Thread(new Runnable() {
            @Override
            public void run() {

                URL photoUrl = null;
                try {
                    photoUrl = new URL(ServerURL.getUrl() + chatObject.getImgUrl());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                InputStream myProfileInputStream = null;

                try {
                    myProfileInputStream = photoUrl.openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final Bitmap photoBitmap = BitmapFactory.decodeStream(myProfileInputStream);
                chatObject.setBitmap(photoBitmap);
                //chatObjectList.set(chatObject.getIndex(),chatObject);
                chatObjectList.add(chatObject);
                ChatRoomActivity.setchatsList(chatObjectList);
                ((ChatRoomActivity)context).handler.sendEmptyMessage(0);


            }
        });
        getPhotoImage.start();
    }
    public void ThreadStartForList(final ChatObject chatObject, final List<ChatObject> chatObjectList, final Context context){
        Thread getPhotoImage = new Thread(new Runnable() {
            @Override
            public void run() {

                URL photoUrl = null;
                try {
                    photoUrl = new URL(ServerURL.getUrl() + chatObject.getImgUrl());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                InputStream myProfileInputStream = null;

                try {
                    myProfileInputStream = photoUrl.openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final Bitmap photoBitmap = BitmapFactory.decodeStream(myProfileInputStream);
                chatObject.setBitmap(photoBitmap);
                chatObjectList.set(chatObject.getIndex(),chatObject);
                ChatRoomActivity.setchatsList(chatObjectList);
                ((ChatRoomActivity)context).handler.sendEmptyMessage(0);
            }
        });
        getPhotoImage.start();
    }

}
