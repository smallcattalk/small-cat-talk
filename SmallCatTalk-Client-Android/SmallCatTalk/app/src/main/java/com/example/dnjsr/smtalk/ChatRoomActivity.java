package com.example.dnjsr.smtalk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnjsr.smtalk.Thread.PhotoDownloadThread;
import com.example.dnjsr.smtalk.Tool.Tool;
import com.example.dnjsr.smtalk.globalVariables.AllRoomUser;
import com.example.dnjsr.smtalk.globalVariables.CurrentUserInfo;
import com.example.dnjsr.smtalk.globalVariables.MySocketManager;
import com.example.dnjsr.smtalk.globalVariables.SelectedRoomInfo;
import com.example.dnjsr.smtalk.info.ChatObjWithOnlyId;
import com.example.dnjsr.smtalk.info.ChatObject;
import com.example.dnjsr.smtalk.info.UserInfo;
import com.example.dnjsr.smtalk.userInfoUpdate.GetAllChats;
import com.example.dnjsr.smtalk.userInfoUpdate.NewChatSend;
import com.example.dnjsr.smtalk.userInfoUpdate.SendPhoto;
import com.example.dnjsr.smtalk.userInfoUpdate.VideoCallSend;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatRoomActivity extends AppCompatActivity {
    public static Context mContext;
    Socket socket;
    public Handler handler;
    final static int SELECT_PHOTO = 1;

    Emitter.Listener emmiterNewChat = new Emitter.Listener() {
        Gson gson = new Gson();
        ChatObjWithOnlyId newChatObj;
        ChatObject chatObject;

        @Override
        public void call(Object... args) {
            try {
                JSONObject obj = (JSONObject) args[0];
                newChatObj = (ChatObjWithOnlyId) gson.fromJson(String.valueOf(obj), ChatObjWithOnlyId.class);
                if(newChatObj.getType().equals("img")){
                    chatObject  = new ChatObject(newChatObj.getCreateAt(), "", AllRoomUser.getAllRoomUsers().get(newChatObj.getUser()), SelectedRoomInfo.getSelectedRoomInfo(), 1);
                    chatObject.setIndex(chatObjectList.size()-1);
                    chatObject.setImgUrl(newChatObj.getImgUrl());
                    PhotoDownloadThread photoDownloadThread = new PhotoDownloadThread();
                    photoDownloadThread.ThreadStart(chatObject,chatObjectList,mContext);
                }
                else if (newChatObj.getType().equals("text")){
                    String a2 = newChatObj.getUser();
                    HashMap<String,UserInfo> a = AllRoomUser.getAllRoomUsers();
                    String a1 = a.get(newChatObj.getUser()).getUserName();
                    chatObject = new ChatObject(newChatObj.getCreateAt(), newChatObj.getChat(), AllRoomUser.getAllRoomUsers().get(newChatObj.getUser()), SelectedRoomInfo.getSelectedRoomInfo(), 1);
                    chatObjectList.add(chatObject);
                    handler.sendEmptyMessage(0);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    };

    Emitter.Listener emmiterVideoCall = new Emitter.Listener() {
        ChatObject chatObject;

        @Override
        public void call(Object... args) {
            try {


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    };

    Emitter.Listener emitterInit = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("roomId", SelectedRoomInfo.getSelectedRoomInfo().get_id());
                //HashMap object = new HashMap();
                socket.emit("init", obj);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };


    static List<ChatObject> chatObjectList = new ArrayList<ChatObject>();

    public static ChatRoomRecyclerViewAdapter chatRoomRecyclerViewAdapter = new ChatRoomRecyclerViewAdapter(chatObjectList);

    public static void setchatsList(List<ChatObject> list) {
        chatObjectList = list;
        //chatRoomRecyclerViewAdapter.setItems(chatObjectList);
    }

    public static void clearchatList() {
        chatObjectList.clear();
        chatRoomRecyclerViewAdapter.setItems(chatObjectList);
    }


    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
        socket.off(Socket.EVENT_CONNECT, emitterInit);
        socket.off("newChat", emmiterNewChat);
        socket = null;
        Log.d("12321", "소켓 연결 해제");
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Tool tool = new Tool();

        mContext = this;

        FloatingActionButton btVideoCall = findViewById(R.id.chatroomactivity_floatingbutton_videoCall);
        FloatingActionButton btChat = findViewById(R.id.chatroomactivity_floatingbutton_send);
        FloatingActionButton btPhoto = findViewById(R.id.chatroomactivity_floatingbutton_photo);
        final EditText edtChat = findViewById(R.id.chatroomactivity_edittext_message);
        final RecyclerView chatRoomRecyclerView = findViewById(R.id.chatroomactivity_recyclerview);
        chatRoomRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRoomRecyclerView.setAdapter(chatRoomRecyclerViewAdapter);


        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    //setchatsList(chatObjectList);
                    chatRoomRecyclerViewAdapter.setItems(chatObjectList);
                    Log.d("12321","handler");
                }
            }
        };


        try {
            socket = MySocketManager.getManager().socket("/chat");
        } catch (Exception e) {
            e.printStackTrace();
        }
        socket.connect();

        socket.on(Socket.EVENT_CONNECT, emitterInit).on("newChat", emmiterNewChat).on("call",emmiterVideoCall);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }

        Log.d("12321", SelectedRoomInfo.getSelectedRoomInfo().get_id());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String title = "그룹톡";
        if (SelectedRoomInfo.getSelectedRoomInfo().getUsersList().size() < 3)
            title = AllRoomUser.getAllRoomUsers().get(tool.getOther_Id(SelectedRoomInfo.getSelectedRoomInfo())).getUserName();
        actionBar.setTitle(title);
        btChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewChatSend newChatSend = new NewChatSend();
                newChatSend.newChat(CurrentUserInfo.getUser().getUserInfo().get_id(), SelectedRoomInfo.getSelectedRoomInfo().get_id(), edtChat.getText().toString());

                edtChat.setText("");
            }
        });
        btPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoIntent, SELECT_PHOTO);
            }
        });

        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
        btVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoCallSend videoCallSend = new VideoCallSend();
                videoCallSend.newVideoCall(SelectedRoomInfo.getSelectedRoomInfo().get_id());
            }
        });



        GetAllChats getAllChats = new GetAllChats();
        getAllChats.getAllChats(SelectedRoomInfo.getSelectedRoomInfo().get_id(),mContext);


    }

    @Override

    public boolean onSupportNavigateUp()

    {

        Log.d("클릭", "클릭됨");



        return super.onSupportNavigateUp();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_PHOTO:
                    Uri image = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.sendPhoto(bytes,CurrentUserInfo.getUser().getUserInfo().get_id(),SelectedRoomInfo.getSelectedRoomInfo().get_id());



                    try {
                        File file = File.createTempFile("upload112.png",null,getCacheDir());
                        //SendPhoto sendPhoto = new SendPhoto();
                        //sendPhoto.sendPhoto(file,CurrentUserInfo.getUser().getUserInfo().get_id(),SelectedRoomInfo.getSelectedRoomInfo().get_id());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
        }
    }

    static class ChatRoomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<ChatObject> items = new ArrayList<>();
        boolean isMe;

        @Override
        public int getItemViewType(int position) {


            if (items.get(position).getUser().get_id().equals(CurrentUserInfo.getUser().getUserInfo().get_id())) {
                if (items.get(position).getBitmap() == null)
                    return 0;
                else
                    return 2;
            } else {
                if (items.get(position).getBitmap() == null)
                    return 1;
                else
                    return 3;
            }

        }

        public ChatRoomRecyclerViewAdapter(List<ChatObject> chatList) {
            items = chatList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view;
            if (i == 0) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_send, viewGroup, false);
                return new CustomViewHolder_My(view);
            } else if(i == 1) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_recive, viewGroup, false);
                return new CustomViewHolder_Other(view);
            }
            else if (i==2){
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_photo_send, viewGroup, false);
                return new CustomViewHolder_My_Photo(view);
            }
            else{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_photo_recive, viewGroup, false);
                return new CustomViewHolder_Other_Photo(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
            if (viewHolder.getItemViewType() == 0) {
                //((CustomViewHolder_My) viewHolder).chatBubble_iv_profile.setImageBitmap(AllRoomUser.getAllRoomUsers().get(items.get(i).getUser().get_id()).getImage());
                ((CustomViewHolder_My) viewHolder).chatBubble_tv_chat.setText(items.get(i).getChat());
                // ((CustomViewHolder_My) viewHolder).getChatBubble_tv_userName.setText(AllRoomUser.getAllRoomUsers().get(items.get(i).getUser().get_id()).getUserName());
            } else if(viewHolder.getItemViewType() == 1){
                ((CustomViewHolder_Other) viewHolder).chatBubble_iv_profile.setImageBitmap(AllRoomUser.getAllRoomUsers().get(items.get(i).getUser().get_id()).getImage());
                ((CustomViewHolder_Other) viewHolder).chatBubble_tv_chat.setText(items.get(i).getChat());
                ((CustomViewHolder_Other) viewHolder).getChatBubble_tv_userName.setText(AllRoomUser.getAllRoomUsers().get(items.get(i).getUser().get_id()).getUserName());
            }
            else if(viewHolder.getItemViewType() == 2){
                ((CustomViewHolder_My_Photo) viewHolder).chatBubble_iv_profile.setImageBitmap(items.get(i).getBitmap());
            }
            else {
                ((CustomViewHolder_Other_Photo) viewHolder).chatBubble_iv_profile.setImageBitmap(AllRoomUser.getAllRoomUsers().get(items.get(i).getUser().get_id()).getImage());
                ((CustomViewHolder_Other_Photo) viewHolder).chatBubble_tv_name.setText(AllRoomUser.getAllRoomUsers().get(items.get(i).getUser().get_id()).getUserName());
                if(items.get(i).getBitmap()==null)
                    ((CustomViewHolder_Other_Photo) viewHolder).chatBubble_iv_photo.setImageResource(R.drawable.loading_icon);
                else
                    ((CustomViewHolder_Other_Photo) viewHolder).chatBubble_iv_photo.setImageBitmap(items.get(i).getBitmap());

            }

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setItems(List<ChatObject> chatList) {
            items = chatList;
            notifyDataSetChanged();
        }


        private class CustomViewHolder_My extends RecyclerView.ViewHolder {
            private ImageView chatBubble_iv_profile;
            private TextView chatBubble_tv_chat;
            private TextView getChatBubble_tv_userName;

            public CustomViewHolder_My(View view) {
                super(view);
                //chatBubble_iv_profile = view.findViewById(R.id.my_chatBubble_iv_profileImage);
                chatBubble_tv_chat = view.findViewById(R.id.chatroomitem_send_textview_message);
                // getChatBubble_tv_userName = view.findViewById(R.id.my_chatBubble_tv_userName);

            }

        }

        private class CustomViewHolder_Other extends RecyclerView.ViewHolder {
            private ImageView chatBubble_iv_profile;
            private TextView chatBubble_tv_chat;
            private TextView getChatBubble_tv_userName;

            public CustomViewHolder_Other(View view) {
                super(view);

                chatBubble_iv_profile = view.findViewById(R.id.chatroomitem_recive_imageview_profile);
                chatBubble_tv_chat = view.findViewById(R.id.chatroomitem_recive_textview_message);
                getChatBubble_tv_userName = view.findViewById(R.id.chatroomitem_recive_textview_id);

            }

        }
        private class CustomViewHolder_My_Photo extends RecyclerView.ViewHolder {
            private ImageView chatBubble_iv_profile;

            public CustomViewHolder_My_Photo(View view) {
                super(view);

                chatBubble_iv_profile = view.findViewById(R.id.chatroomitem_imageview_photo);

            }

        }
        private class CustomViewHolder_Other_Photo extends RecyclerView.ViewHolder {
            private ImageView chatBubble_iv_profile;
            private TextView chatBubble_tv_name;
            private ImageView chatBubble_iv_photo;
            public CustomViewHolder_Other_Photo(View view) {
                super(view);

                chatBubble_iv_profile = view.findViewById(R.id.chatroomitem_recive_photo_imageview_profile);
                chatBubble_tv_name = view.findViewById(R.id.chatroomitem_recive_photo_textview_username);
                chatBubble_iv_photo = view.findViewById(R.id.chatroomitem_recive_imageview_photo);

            }

        }
    }

}
