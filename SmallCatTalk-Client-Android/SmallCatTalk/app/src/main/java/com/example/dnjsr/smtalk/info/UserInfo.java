package com.example.dnjsr.smtalk.info;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    String userId;
    String userPassword;
    String userName;
    String profileImgUrl;
    String comment;
    String _id;
    List<UserInfo> friendsList;
    String profileImg;
    Bitmap image;
    Boolean isChange = false;
    List<RoomsList> roomsList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<UserInfo> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<UserInfo> friendsList) {
        this.friendsList = friendsList;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Boolean getChange() {
        return isChange;
    }

    public void setChange(Boolean change) {
        isChange = change;
    }

    public List<RoomsList> getRoomsList() {
        return roomsList;
    }

    public void setRoomsList(List<RoomsList> roomsList) {
        this.roomsList = roomsList;
    }

    public UserInfo(String userId, String userPassword, String userName, String profileImgUrl, String comment, String _id, List<UserInfo> friendsList, String profileImg, Bitmap image) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
        this.comment = comment;
        this._id = _id;
        this.friendsList = friendsList;
        this.profileImg = profileImg;
        this.image = image;
    }

}


