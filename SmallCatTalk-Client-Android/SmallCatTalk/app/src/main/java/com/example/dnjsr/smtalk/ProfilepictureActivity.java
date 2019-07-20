package com.example.dnjsr.smtalk;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dnjsr.smtalk.globalVariables.SelectedUserInfo;
import com.example.dnjsr.smtalk.info.UserInfo;

public class ProfilepictureActivity extends AppCompatActivity {
    private ImageView profilepictureactivity_imageview_profileimage;
    UserInfo userinfo = SelectedUserInfo.getUser().getUserInfo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepicture);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }

        Bundle bundle = getIntent().getExtras();
        //Bitmap userimage = bundle.getParcelable("userimage");


        profilepictureactivity_imageview_profileimage = findViewById(R.id.profilepictureactivity_imageview_profileimage);
        //Glide.with(this).load(userimage).into(profilepictureactivity_imageview_profileimage);
        profilepictureactivity_imageview_profileimage.setImageBitmap(userinfo.getImage());

    }
}
