package com.example.dnjsr.smtalk.userInfoUpdate;

import android.app.Activity;
import android.util.Log;

import com.example.dnjsr.smtalk.MainActivity;
import com.example.dnjsr.smtalk.api.RetrofitApi;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.info.UserInfo;
import com.example.dnjsr.smtalk.result.LoginResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInfoUpdate {
    String url = ServerURL.getUrl();

    public void Update(String _id, final Activity activity){
        try {
            HashMap<String, String> input = new HashMap<>();
            input.put("_id", _id);
            Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
            retrofitApi.post_idForUpdate(input).enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                    if (response.isSuccessful()) {
                        LoginResult map = response.body();
                        if (map != null) {
                            switch (map.getResult()) {
                                case 0:
                                    Log.d("12321","update fail");

                                    break;
                                case 1:
                                    Log.d("12321","update ok");
                                    UserInfo userinfo = map.getUserInfo();
                                    userinfo.setChange(true);
                                    CurrentUserInfo.getUser().setUserInfo(userinfo);
                                    if(!activity.getLocalClassName().equals("MainActivity")) {
                                        Log.d("12321",activity.getLocalClassName());
                                        activity.finish();
                                    }
                                    break;
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<LoginResult> call, Throwable t) {
                    Log.d("12321","fail to connect :  update");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
