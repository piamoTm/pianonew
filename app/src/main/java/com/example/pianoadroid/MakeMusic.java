package com.example.pianoadroid;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MakeMusic extends AppCompatActivity {

///ㄹㄴㅇㄹㄴㅇ
    private MakeMusic_RecyclerAdapter adapter;
    //private PlayList_RecyclerAdapter adapter2;
    private ImageView mBackBtn;
    private ImageButton mPlayBtn,mUploadBtn;
    private RecyclerView mMakeNoteRecycler;
    Music data = new Music();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_make_music);

        mBackBtn = (ImageView)findViewById(R.id.back_btn);
        mUploadBtn = (ImageButton)findViewById(R.id.uploadbtn);
        mPlayBtn =(ImageButton)findViewById(R.id.playbtn);

        init();

        //show();// 안내 다이얼로그


        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data.setId(1);
                data.setTitle("도");

                adapter.notifyDataSetChanged();
            }
        });


    }

    private void init() {
        mMakeNoteRecycler = (RecyclerView)findViewById(R.id.makenote);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMakeNoteRecycler.setLayoutManager(linearLayoutManager);

        //adapter = new MakeMusic_RecyclerAdapter();
        adapter = new MakeMusic_RecyclerAdapter();
        mMakeNoteRecycler.setAdapter(adapter);

        //Music data = new Music();
        data.setId(0);
       // data.setTitle("도");
        adapter.addItem(data);
        adapter.notifyDataSetChanged();

    }

    void show()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
       // builder.setTitle("AlertDialog Title");
        builder.setMessage("건반을 눌러주세요.:)");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });

        builder.show();
    }



}
