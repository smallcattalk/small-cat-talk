package com.example.dnjsr.smtalk.userInfoUpdate;

import android.util.Log;

import com.example.dnjsr.smtalk.api.RetrofitApi;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.result.ResultCode;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoCallSend {
    String url = ServerURL.getUrl();
    public void newVideoCall(String roomId){
        try {
            HashMap<String, String> input = new HashMap<>();
            input.put("roomId", roomId);


            Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
            retrofitApi.sendVideoCall(input).enqueue(new Callback<ResultCode>() {
                @Override
                public void onResponse(Call<ResultCode> call, Response<ResultCode> response) {

                    if (response.isSuccessful()) {
                        ResultCode map = response.body();
                        if (map != null) {
                            switch (map.getResult()) {
                                case 0:
                                    Log.d("12321", "video call fail 1");
                                    break;
                                case 1:
                                    Log.d("12321", "video call success");
                                    break;
                            }
                        }
                        else
                            Log.d("12321", "new chat fail 2");
                    }
                }

                @Override
                public void onFailure(Call<ResultCode> call, Throwable t) {
                    Log.d("123212","video call fail");
                    t.printStackTrace();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
