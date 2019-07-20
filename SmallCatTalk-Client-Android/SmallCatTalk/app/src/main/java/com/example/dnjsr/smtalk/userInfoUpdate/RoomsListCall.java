package com.example.dnjsr.smtalk.userInfoUpdate;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.dnjsr.smtalk.MainActivity;
import com.example.dnjsr.smtalk.api.RetrofitApi;
import com.example.dnjsr.smtalk.fragment.ChatFragment;
import com.example.dnjsr.smtalk.fragment.PeopleFragment;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.FriendsInfo;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.info.RoomInfo;
import com.example.dnjsr.smtalk.info.UserInfo;
import com.example.dnjsr.smtalk.result.RoomListCallResult;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomsListCall {
    String url = ServerURL.getUrl();
    ChatFragment chatFragment = new ChatFragment();

    public void getRoomsList(String _id){
        try {
            HashMap<String, String> input = new HashMap<>();
            input.put("_id", _id);
            Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
            retrofitApi.post_idForRoomList(input).enqueue(new Callback<RoomListCallResult>() {
                @Override
                public void onResponse(Call<RoomListCallResult> call, Response<RoomListCallResult> response) {
                    if (response.isSuccessful()) {
                        RoomListCallResult map = response.body();
                        if (map != null) {
                            switch (map.getResult()) {
                                case 0:
                                    Log.d("12321","room listr fail");
                                    break;
                                case 1:
                                    Log.d("12321","room list ok");
                                    //CurrentUserInfo.getUser().getUserInfo().setRoomsList(map.getRoomsList());

                                   // CurrentUserInfo.getUser().getUserInfo().setChange(true);

                                    break;
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<RoomListCallResult> call, Throwable t) {
                    Log.d("12321","fail to connect :  roomlist");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
