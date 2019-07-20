package com.example.dnjsr.smtalk.Thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class GroupImageThread {
    public void ThreadStart(){
        Thread getGroupImage = new Thread(new Runnable() {
            @Override
            public void run() {
                //my profile
                URL myProfileUrl = null;
                try {
                    myProfileUrl = new URL(ServerURL.getUrl() + "profileImgs/defaultGroup.png");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                InputStream myProfileInputStream = null;

                try {
                    myProfileInputStream = myProfileUrl.openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final Bitmap groupImageBitmap = BitmapFactory.decodeStream(myProfileInputStream);
                CurrentUserInfo.setGroupImage(groupImageBitmap);
            }
        });
        getGroupImage.start();
    }
}
