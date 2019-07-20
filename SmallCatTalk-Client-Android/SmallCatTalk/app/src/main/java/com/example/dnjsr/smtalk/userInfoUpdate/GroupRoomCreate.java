package com.example.dnjsr.smtalk.userInfoUpdate;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.dnjsr.smtalk.api.RetrofitApi;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.SelectedRoomInfo;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.info.RoomsList;
import com.example.dnjsr.smtalk.result.CreateRoomResult;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroupRoomCreate {
    String url = ServerURL.getUrl();

    public void createRoom(final List<String> usersList, final Intent intent, final Activity activity){


        try {
            int i=0;
            String[] a = new String[usersList.size()];
            for (String b:usersList){
                a[i]=b;
                i++;
            }
            usersList.add(CurrentUserInfo.getUser().getUserInfo().get_id());
            HashMap<String, List<String>> input = new HashMap<>();
            input.put("usersList", usersList);
            /*for (String user_Id:usersList) {
                input.put("usersList",user_Id);
            }*/


            Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
            retrofitApi.createGroupRoom(usersList).enqueue(new Callback<CreateRoomResult>() {
                @Override
                public void onResponse(Call<CreateRoomResult> call, Response<CreateRoomResult> response) {
                    if (response.isSuccessful()) {
                        CreateRoomResult map = response.body();
                        if (map != null) {
                            Log.d("12321", "room create ok");
                            CurrentUserInfo.getUser().getUserInfo().getRoomsList().add(new RoomsList(map.getNewRoom().get_id(),"000000000000000000000000"));
                            SelectedRoomInfo.setSelectedRoomInfo(map.getNewRoom());
                            CurrentUserInfo.getUser().getUserInfo().setChange(true);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CreateRoomResult> call, Throwable t) {
                    Log.d("12321","fail room create");
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
