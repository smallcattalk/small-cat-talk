package com.example.dnjsr.smtalk.api;

import com.example.dnjsr.smtalk.result.CreateRoomResult;
import com.example.dnjsr.smtalk.result.FriendListCallResult;
import com.example.dnjsr.smtalk.result.GetAllChatResult;
import com.example.dnjsr.smtalk.result.IdCheckResult;
import com.example.dnjsr.smtalk.result.ResultCode;
import com.example.dnjsr.smtalk.result.LoginResult;
import com.example.dnjsr.smtalk.result.RoomListCallResult;
import com.example.dnjsr.smtalk.result.SearchResult;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApi {
    // friend add
    @FormUrlEncoded
    @POST("/friend")
    Call<ResultCode> addFriend(@FieldMap HashMap<String,String> map);

    // friend delete
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/friend", hasBody = true)
    Call<ResultCode> deleteFriend(@FieldMap HashMap<String,String> map);

    // friend list
    @FormUrlEncoded
    @POST("/friend/list")
    Call<FriendListCallResult> postUserInfoForFriendList(@FieldMap HashMap<String,String> map);

    // id check
    @GET("/check/{userId}")
    Call<IdCheckResult> idCheck(@Path("userId") String userId);

    //join
    @FormUrlEncoded
    @POST("/join")
    Call<ResultCode> postJoinUserInfo(@FieldMap HashMap<String,String> map);

    //login
    @FormUrlEncoded
    @POST("/login")
    Call<LoginResult> postLoginUserInfo(@FieldMap HashMap<String,String> map);

    //search
    @GET("/search/user/{userId}")
    Call<SearchResult> postIdForSearch(@Path("userId") String searchId);

    //update
    @FormUrlEncoded
    @POST("/update/user")
    Call<LoginResult> post_idForUpdate(@FieldMap HashMap<String,String> map);

    //call room list
    @FormUrlEncoded
    @POST("/room/list")
    Call<RoomListCallResult> post_idForRoomList(@FieldMap HashMap<String,String> map);

    //create room
    @FormUrlEncoded
    @POST("/room")
    Call<CreateRoomResult> createRoom(@FieldMap HashMap<String,String> map);

    //create room
    @FormUrlEncoded
    @POST("/groupRoom")
    Call<CreateRoomResult> createGroupRoom(@Field("usersList") List<String> list);

    //get all chats by room_id
    @GET("/chat/list/{roomId}")
    Call<GetAllChatResult> getAllChats(@Path("roomId") String roomId);

    //create chat
    @FormUrlEncoded
    @POST("/chat/newChat")
    Call<ResultCode> createChat(@FieldMap HashMap<String,String> map);

    //send photo
    @Multipart
    @POST("/chat/img")
    Call<ResultCode> sendPhoto(@Part("user") RequestBody user, @Part("room") RequestBody room, @Part MultipartBody.Part img);


    //send video call
    @FormUrlEncoded
    @POST("/chat/call")
    Call<ResultCode> sendVideoCall(@FieldMap HashMap<String,String> map);





}
