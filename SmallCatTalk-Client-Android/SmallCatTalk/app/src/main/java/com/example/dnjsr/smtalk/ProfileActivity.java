package com.example.dnjsr.smtalk;


import android.content.Intent;

import android.graphics.Color;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnjsr.smtalk.Tool.Tool;
import com.example.dnjsr.smtalk.api.RetrofitApi;

import com.example.dnjsr.smtalk.globalVariables.AllRoomUser;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.MySocketManager;
import com.example.dnjsr.smtalk.globalVariables.SelectedUserInfo;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;

import com.example.dnjsr.smtalk.info.UserInfo;
import com.example.dnjsr.smtalk.result.ResultCode;
import com.example.dnjsr.smtalk.userInfoUpdate.RoomCreate;
import com.example.dnjsr.smtalk.userInfoUpdate.UserInfoUpdate;

import org.json.JSONObject;

import java.net.URI;
import java.util.HashMap;

import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profileactivity_imageview_profileimage;
    private TextView profileactivity_textview_username;
    private TextView profileactivity_textview_usermessage;
    private ImageView profileactivity_imageview_friend;
    private ImageView profileactivity_imageview_chatCreate;
    private TextView profileactivity_textview_friend;


    String serverUrl = new ServerURL().getUrl();
    UserInfo currentUser = CurrentUserInfo.getUser().getUserInfo();
    UserInfoUpdate userInfoUpdate = new UserInfoUpdate();
    boolean isFriend = false;
    Boolean isMe = false;
    Tool tool = new Tool();
    Socket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }

        final UserInfo userinfo = SelectedUserInfo.getUser().getUserInfo();


        profileactivity_imageview_profileimage = findViewById(R.id.profileactivity_imageview_profileimage);
        profileactivity_textview_username = findViewById(R.id.profileactivity_textview_username);
        profileactivity_textview_usermessage =findViewById(R.id.profileactivity_textview_usermessage);
        profileactivity_imageview_chatCreate = findViewById(R.id.profileactivity_imageview_chatCreate);
        profileactivity_imageview_friend = findViewById(R.id.profileactivity_imageview_friend);
        profileactivity_textview_friend = findViewById(R.id.profileactivity_textview_friend);



        profileactivity_imageview_profileimage.setImageBitmap(userinfo.getImage());
        profileactivity_textview_username.setText(userinfo.getUserName());
        profileactivity_textview_usermessage.setText(userinfo.getComment());

        if(SelectedUserInfo.getUser().getUserInfo().equals(CurrentUserInfo.getUser().getUserInfo())){
            isMe = true;
            profileactivity_imageview_profileimage.setImageBitmap(CurrentUserInfo.getUser().getUserInfo().getImage());
            profileactivity_imageview_friend.setImageResource(R.drawable.icon_account);
            profileactivity_textview_friend.setText("내 정보");
        }

        for (int i=0; i<currentUser.getFriendsList().size();i++){
            if(currentUser.getFriendsList().get(i).get_id().equals(userinfo.get_id())){
                isFriend=true;
                break;
            }
        }

        profileactivity_imageview_profileimage.setOnClickListener(new View.OnClickListener() {               //image눌러 화면이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ProfilepictureActivity.class);
                startActivity(intent);
            }
        });
        profileactivity_imageview_chatCreate.setOnClickListener(new View.OnClickListener() {               //image눌러 화면이동
            @Override
            public void onClick(View v) {
                Intent intentToCatRoom = new Intent(ProfileActivity.this,ChatRoomActivity.class);
                Intent intentToProfileModify = new Intent(ProfileActivity.this, ProfileModifyActivity.class);
                if(isMe){
                    startActivity(intentToProfileModify);
                }
                else {
                    if(MySocketManager.getManager()==null){
                        try {
                            Manager manager = new Manager(new URI(serverUrl));
                            MySocketManager.setManager(manager);

                            socket = MySocketManager.getManager().socket("/room");
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                try {
                                    JSONObject obj = new JSONObject();
                                    obj.put("_id", CurrentUserInfo.getUser().getUserInfo().get_id());
                                    //HashMap object = new HashMap();
                                    socket.emit("init",obj);
                                }catch (Exception e){

                                }
                            }
                        });

                        socket.connect();
                    }
                    RoomCreate roomCreate = new RoomCreate();
                    AllRoomUser.getAllRoomUsers().put(SelectedUserInfo.getUser().getUserInfo().get_id(),SelectedUserInfo.getUser().getUserInfo());
                    AllRoomUser.getAllRoomUsers().put(CurrentUserInfo.getUser().getUserInfo().get_id(), CurrentUserInfo.getUser().getUserInfo());
                    roomCreate.createRoom(tool.getRoomIdBy_Id(SelectedUserInfo.getUser().getUserInfo().get_id()),CurrentUserInfo.getUser().getUserInfo().get_id(), SelectedUserInfo.getUser().getUserInfo().get_id(), intentToCatRoom, ProfileActivity.this);

                }
            }
        });

        if (isFriend) {
            profileactivity_imageview_friend.setImageResource(R.drawable.icon_user_delete);
            profileactivity_textview_friend.setText("친구 삭제");
        }
        else if(!isMe){
            profileactivity_imageview_friend.setImageResource(R.drawable.icon_user_plus);
            profileactivity_textview_friend.setText("친구 추가");
        }

        profileactivity_imageview_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myId = CurrentUserInfo.getUser().getUserInfo().get_id();
                String friendId = userinfo.get_id();
                if(isFriend)
                    DeleteFriend(myId,friendId);
                else
                    AddFriend(myId,friendId);
                userInfoUpdate.Update(CurrentUserInfo.getUser().getUserInfo().get_id(),ProfileActivity.this);
            }
        });
    }

    public void AddFriend(String userId, String friendId){
        HashMap<String, String> input = new HashMap<>();
        input.put("_id", userId);
        input.put("friendId",friendId);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        retrofitApi.addFriend(input).enqueue(new Callback<ResultCode>() {
            @Override
            public void onResponse(Call<ResultCode> call, Response<ResultCode> response) {
                if (response.isSuccessful()) {
                    ResultCode searchResult = response.body();
                    switch (searchResult.getResult()) {
                        case 0:
                            Log.d("test123","FAfail");
                            break;
                        case 1:
                            Log.d("test123","FAsucess");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultCode> call, Throwable t) {

            }
        });
    }
    public void DeleteFriend(String userId, String friendId){
        HashMap<String, String> input = new HashMap<>();
        input.put("_id", userId);
        input.put("friendId",friendId);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        retrofitApi.deleteFriend(input).enqueue(new Callback<ResultCode>() {
            @Override
            public void onResponse(Call<ResultCode> call, Response<ResultCode> response) {
                if (response.isSuccessful()) {
                    ResultCode result = response.body();
                    switch (result.getResult()) {
                        case 0:
                            Log.d("test123","FDfail");
                            break;
                        case 1:
                            Log.d("test123","FDsucess");
                            break;
                    }
                }
            }
            @Override
            public void onFailure(Call<ResultCode> call, Throwable t) {

            }
        });
    }
}
