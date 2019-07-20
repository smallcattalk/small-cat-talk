package com.example.dnjsr.smtalk.userInfoUpdate;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.dnjsr.smtalk.ChatRoomActivity;
import com.example.dnjsr.smtalk.R;
import com.example.dnjsr.smtalk.Thread.PhotoDownloadThread;
import com.example.dnjsr.smtalk.api.RetrofitApi;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.SelectedRoomInfo;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.info.ChatObject;
import com.example.dnjsr.smtalk.result.GetAllChatResult;
import com.example.dnjsr.smtalk.result.SearchResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetAllChats {

    String url = ServerURL.getUrl();
    PhotoDownloadThread photoDownloadThread = new PhotoDownloadThread();
    List<ChatObject> chatObjectList =new ArrayList<>();
    public void getAllChats(String roomId, final Context context){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        retrofitApi.getAllChats(roomId).enqueue(new Callback<GetAllChatResult>() {

            @Override
            public void onResponse(Call<GetAllChatResult> call, Response<GetAllChatResult> response) {
                if (response.isSuccessful()) {
                    final GetAllChatResult map = response.body();
                    if (map != null) {
                        switch (map.getResult()) {
                            case 0:
                                Log.d("12321","no chats");
                                break;
                            case 1:
                                Log.d("12321","yes chats");

                                int i =0;
                                for (ChatObject chatObject:map.getChats()) {
                                    chatObject.setIndex(i);
                                    chatObjectList.add(chatObject);
                                    i++;
                                }
                                for (ChatObject chatObject:chatObjectList) {
                                    if(!chatObject.getImgUrl().equals("")){
                                        photoDownloadThread.ThreadStartForList(chatObject,chatObjectList,(ChatRoomActivity)context);
                                    }
                                }
                                ChatRoomActivity.setchatsList(chatObjectList);
                                ((ChatRoomActivity)context).handler.sendEmptyMessage(0);
                                break;
                        }
                    }
                }
                else {
                    Log.d("12321", "chat not successful");
                    ChatRoomActivity.clearchatList();

                }

            }

            @Override
            public void onFailure(Call<GetAllChatResult> call, Throwable t) {
                Log.d("12321","fail to get chats");
                Log.d("12321", SelectedRoomInfo.getSelectedRoomInfo().get_id());
                t.printStackTrace();
            }
        });

    }
}
