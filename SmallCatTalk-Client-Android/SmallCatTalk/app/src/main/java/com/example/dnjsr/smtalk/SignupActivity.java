package com.example.dnjsr.smtalk;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dnjsr.smtalk.api.RetrofitApi;

import com.example.dnjsr.smtalk.globalVariables.ServerURL;
import com.example.dnjsr.smtalk.pattern.UserIdPattern;
import com.example.dnjsr.smtalk.result.IdCheckResult;
import com.example.dnjsr.smtalk.result.ResultCode;
import com.example.dnjsr.smtalk.userInfoUpdate.UserLogin;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {
    private EditText id;
    private EditText name;
    private EditText password;
    private EditText password_check;
    private Button signup;
    private TextView signactivity_textview_idcheckmessage;
    private FloatingActionButton signactivity_button_idcheckbutton;
    private boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final ServerURL serverURL = new ServerURL();
        final String currentSever = serverURL.getUrl();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }

        signup = findViewById(R.id.signupActivity_button_signup);
        id = findViewById(R.id.signupActivity_edittext_id);
        name = findViewById(R.id.signupActivity_edittext_name);
        password = findViewById(R.id.signupActivity_edittext_password);
        password_check = findViewById(R.id.signupActivity_edittext_passwordcheck);
        signactivity_button_idcheckbutton = findViewById(R.id.signupActivity_button_idcheckbutton);
        signactivity_textview_idcheckmessage = findViewById(R.id.signupActivity_textview_idcheckmessage);

        signactivity_button_idcheckbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserIdPattern userIdPattern = new UserIdPattern();
                String userId = id.getText().toString();
                if (!userIdPattern.checkUserId(userId)) {
                    signactivity_button_idcheckbutton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent))); //floating button 색상 변경
                    signactivity_textview_idcheckmessage.setText("아이디는 영문자와 숫자 4자리 이상으로 작성해주세요");
                } else {

                    try {
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(currentSever)
                                .addConverterFactory(GsonConverterFactory.create()).build();

                        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
                        retrofitApi.idCheck(userId).enqueue(new Callback<IdCheckResult>() {
                            @Override
                            public void onResponse(Call<IdCheckResult> call, Response<IdCheckResult> response) {
                                if (response.isSuccessful()) {
                                    IdCheckResult idCheckResult = response.body();
                                    switch (idCheckResult.getResult()) {
                                        case 0:
                                            signactivity_button_idcheckbutton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent))); //floating button 색상 변경
                                            signactivity_textview_idcheckmessage.setText("아이디가 이미 존재합니다.");
                                            break;
                                        case 1:
                                            signactivity_button_idcheckbutton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorCorrect)));
                                            signactivity_textview_idcheckmessage.setText("사용가능한 ID입니다.");
                                            isChecked = true;
                                            break;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<IdCheckResult> call, Throwable t) {

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (password.getText().toString().equals(password_check.getText().toString())) {
                    password_check.setTextColor(Color.parseColor("#00ff00"));
                    password.setTextColor(Color.parseColor("#00ff00"));
                    Log.d("test", password.getText().toString());
                } else {
                    password_check.setTextColor(Color.parseColor("#ff0000"));
                    password.setTextColor(Color.parseColor("#ff0000"));

                }

            }
        });

        password_check.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (password.getText().toString().equals(password_check.getText().toString())) {
                            password_check.setTextColor(Color.parseColor("#00ff00"));
                            password.setTextColor(Color.parseColor("#00ff00"));
                        } else {
                            password_check.setTextColor(Color.parseColor("#ff0000"));
                            password.setTextColor(Color.parseColor("#ff0000"));

                        }

                    }
                }
        );


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChecked) {
                    Toast.makeText(SignupActivity.this, "아이디 체크 하시오", Toast.LENGTH_SHORT).show();
                } else if (id.getText().toString().equals("") || name.getText().toString().equals("") || password.getText().toString().equals("") || password_check.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (!password.getText().toString().equals(password_check.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        HashMap<String, String> input = new HashMap<>();
                        input.put("userId", id.getText().toString());
                        input.put("userPassword", password.getText().toString());
                        input.put("userName", name.getText().toString());


                        Retrofit retrofit = new Retrofit.Builder().baseUrl(currentSever)
                                .addConverterFactory(GsonConverterFactory.create()).build();

                        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

                        retrofitApi.postJoinUserInfo(input).enqueue(new Callback<ResultCode>() {
                            @Override
                            public void onResponse(Call<ResultCode> call, Response<ResultCode> response) {
                                if (response.isSuccessful()) {
                                    ResultCode map = response.body();

                                    if (map != null) {
                                        switch (map.getResult()) {
                                            case 0:
                                                Toast.makeText(SignupActivity.this, "존재하는 아이디 입니다. 다른 아이디를 사용해주세요.", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 1:
                                                Toast.makeText(SignupActivity.this, "회원가입 되었습니다.", Toast.LENGTH_SHORT).show(); //아이디 비밀번호 비워둔 Login창
                                                UserLogin userLogin = new UserLogin();
                                                userLogin.Login(id.getText().toString(), password.getText().toString(), new Intent(SignupActivity.this, MainActivity.class), SignupActivity.this);
                                                break;
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultCode> call, Throwable t) {

                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });
    }


}
