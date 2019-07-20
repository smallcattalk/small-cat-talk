package com.example.dnjsr.smtalk.userInfoUpdate;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;


import com.example.dnjsr.smtalk.Thread.GroupImageThread;
import com.example.dnjsr.smtalk.Thread.MyProfileThread;
import com.example.dnjsr.smtalk.api.RetrofitApi;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.IsLogin;
import com.example.dnjsr.smtalk.globalVariables.ServerURL;

import com.example.dnjsr.smtalk.info.UserInfo;
import com.example.dnjsr.smtalk.result.LoginResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLogin {
    String url = ServerURL.getUrl();
    MyProfileThread myProfileThread = new MyProfileThread();
    GroupImageThread groupImageThread = new GroupImageThread();

    public void Login(final String id, final String password , final Intent intent, final Activity activity){

        final String strId = id;
        final String strPassword = password;

                try {
                    HashMap<String, String> input = new HashMap<>();
                    input.put("userId", strId);
                    input.put("userPassword", strPassword);


                    Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
                    retrofitApi.postLoginUserInfo(input).enqueue(new Callback<LoginResult>() {
                        @Override
                        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                            if (response.isSuccessful()) {
                                LoginResult map = response.body();
                                if (map != null) {
                                    switch (map.getResult()) {
                                        case -2:
                                            Log.d("12321", "Login fail DB error");
                                            break;
                                        case -1:
                                            Log.d("12321", "Login fail wrong password");
                                            break;
                                        case 0:
                                            Log.d("12321", "Login fail wrong ID");
                                            break;
                                        case 1:
                                            Log.d("12321", "Login ok");
                                            UserInfo userinfo = map.getUserInfo();
                                            userinfo.setChange(true);
                                            CurrentUserInfo.getUser().setUserInfo(userinfo);
                                            myProfileThread.ThreadStart();
                                            groupImageThread.ThreadStart();
                                            UserInfo asd = CurrentUserInfo.getUser().getUserInfo();
                                            IsLogin.setIsLogin(true);
                                            activity.startActivity(intent);
                                            activity.finish();
                                            break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResult> call, Throwable t) {
                            Log.d("123212","Loginconnectfail");
                            t.printStackTrace();

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


    }
}
