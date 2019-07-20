package com.example.dnjsr.smtalk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnjsr.smtalk.info.UserInfo;
import com.example.dnjsr.smtalk.userInfoUpdate.GroupRoomCreate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupRoomCreateActivity extends AppCompatActivity {
    List<UserInfo> userInfos;
    List<String> cl;
    SelectUserRecyclerViewAdapter selectUserRecyclerViewAdapter;
    RecyclerView recyclerView;

    @Override

    public boolean onSupportNavigateUp()     //actionbar 뒤로가기 이벤트

    {

        Log.d("클릭", "클릭됨");

        return super.onSupportNavigateUp();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_room_create);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("대화상대 초대");
        actionBar.setDisplayHomeAsUpEnabled(true);

        userInfos = ((MainActivity)MainActivity.mContext).getUserInfos();
        recyclerView = findViewById(R.id.Rv_UserSelect);
        selectUserRecyclerViewAdapter = new SelectUserRecyclerViewAdapter(userInfos);
        recyclerView.setAdapter(selectUserRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button bt_Ok = findViewById(R.id.bt_Ok);
        bt_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cl = selectUserRecyclerViewAdapter.getChecked_Id();
                if(cl.size()<=0){

                }
                else{
                    Intent intentToCatRoom = new Intent(GroupRoomCreateActivity.this,ChatRoomActivity.class);
                    GroupRoomCreate groupRoomCreate = new GroupRoomCreate();
                    groupRoomCreate.createRoom(cl,intentToCatRoom,GroupRoomCreateActivity.this);
                }
            }
        });
    }


    class SelectUserRecyclerViewAdapter extends RecyclerView.Adapter<SelectUserRecyclerViewAdapter.CustomViewHolder>{
        Boolean[] isChecked = new Boolean[userInfos.size()];

        private List<UserInfo> items;

        public SelectUserRecyclerViewAdapter(List<UserInfo> list)
        {
            this.items=list;
            Arrays.fill(isChecked, false);
            Log.d("12321", Integer.toString(items.size())+"tlwkr");
        }

        @Override
        public CustomViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_check,parent,false);
            Log.d("12321", Integer.toString(items.size())+"온크리에이트");
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {

            Log.d("12321", Integer.toString(items.size())+"바인드뷰홀더");
            ((CustomViewHolder)holder).profileImage.setImageBitmap(items.get(position).getImage());
            ((CustomViewHolder)holder).textview_name.setText(items.get(position).getUserName());
            ((CustomViewHolder)holder).checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox)v;
                    if(checkBox.isChecked())
                        checkBox.setChecked(true);
                    else
                        checkBox.setChecked(false);
                    isChecked[position] = checkBox.isChecked();
                }
            });

        }

        @Override
        public int getItemCount() {
            return items.size();
        }
        public void setItems(List<UserInfo> list){
            items = list;
            notifyDataSetChanged();
        }
        public Boolean[] getIsChecked(){
            return isChecked;

        }
        public List<String> getChecked_Id(){
            List<String> checked_idList = new ArrayList<>();
            for (int i = 0; i<isChecked.length; i++){
                if(isChecked[i]){
                    checked_idList.add(items.get(i).get_id());
                }
            }
            return checked_idList;
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            public ImageView profileImage;
            public TextView textview_name;
            public CheckBox checkBox;
            public CustomViewHolder(View view) {
                super(view);
                profileImage = view.findViewById(R.id.Iv_profile_forCheck);
                textview_name = view.findViewById(R.id.Tv_userName_forCheck);
                checkBox = view.findViewById(R.id.checkbox);
            }
        }


    }
}
