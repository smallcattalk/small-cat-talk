package com.example.dnjsr.smtalk.userInfoUpdate;

import android.util.Log;

import com.example.dnjsr.smtalk.api.RetrofitApi;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.SelectedRoomInfo;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.result.ResultCode;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendPhoto {
    String url = ServerURL.getUrl();

    public void sendPhoto(byte[] file, String userId, String roomId){
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("img","upload"+file.toString(),requestFile);
        RequestBody user = RequestBody.create(MediaType.parse("multipart/form-data"),userId);
        RequestBody room = RequestBody.create(MediaType.parse("multipart/form-data"),roomId);


        try {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
            retrofitApi.sendPhoto(user,room,body).enqueue(new Callback<ResultCode>() {
                @Override
                public void onResponse(Call<ResultCode> call, Response<ResultCode> response) {
                    if (response.isSuccessful()) {
                        ResultCode map = response.body();
                        if (map != null) {
                            Log.d("12321", "photo send ok");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResultCode> call, Throwable t) {
                    Log.d("12321","fail photo send");
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
