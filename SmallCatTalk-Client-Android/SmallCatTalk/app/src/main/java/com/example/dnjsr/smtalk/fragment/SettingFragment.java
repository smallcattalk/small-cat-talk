package com.example.dnjsr.smtalk.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnjsr.smtalk.R;
import com.example.dnjsr.smtalk.info.SettingInfo;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends android.support.v4.app.Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_setting,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.settingfragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new SettingFragmentRecyclerViewAdapter());

        return view;
    }

    class SettingFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{ //이미지 설정이름 필요

        List<SettingInfo> settingList = new ArrayList<SettingInfo>();
        //Resources res;
        public SettingFragmentRecyclerViewAdapter() {
            settingList.add(new SettingInfo(R.drawable.icon_account,"내 계정"));
            settingList.add(new SettingInfo(R.drawable.icon_sound,"소리"));
            settingList.add(new SettingInfo(R.drawable.icon_notice,"공지 사항"));
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_setting,viewGroup,false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ((CustomViewHolder)viewHolder).setting_Image.setImageResource(settingList.get(i).getSetting_Image());
            ((CustomViewHolder)viewHolder).setting_text.setText(settingList.get(i).getSetting_menu());
        }

        @Override
        public int getItemCount() {
            return settingList.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            public ImageView setting_Image;
            public TextView setting_text;

            public CustomViewHolder(View view) {
                super(view);
                setting_Image = view.findViewById(R.id.settingitem_imageview);
                setting_text = view.findViewById(R.id.settingitem_textview);
            }
        }
    }
}
