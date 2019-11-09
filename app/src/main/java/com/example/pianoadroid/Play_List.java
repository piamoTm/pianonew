package com.example.pianoadroid;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class Play_List extends AppCompatActivity {


    private PlayList_RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView mBackbtn;

    private ArrayList<Music> musicArr;

    //SQLite db 개체 생성
    DBMyProductHelper_Read db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //가로모드고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_play_list);

        //SQLite db helper init 초기화
        db = new DBMyProductHelper_Read(this);

        mBackbtn = (ImageView)findViewById(R.id.backpressBtn);

        musicArr = loadMusicList();//데이터 불러오기

        init(musicArr);//리사이클러뷰 세팅

        //뒤로가기 버튼 이벤트
        mBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        int beat[] = {1,1,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,1,1,21,1,1,1,1,1,2};
//        Music music = new Music("작은 별", "미상", "CCGGAAG FFEEDDC GGFFEED GGFFEED CCGGAAG FFEEDDC ",beat);
//        db.addMusic(music);

        //int beat[] = {1,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,1,2,1,1,1,1,2};
        //Music music = new Music("학교 종", "미상", "GGAAGGE GGEED GGAAGGE GEDEC ",beat);
        //db.addMusic(music);


    }

    //리사이클러뷰 세팅
    private void init(ArrayList<Music> musicArr) {
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new PlayList_RecyclerAdapter(musicArr);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Music> loadMusicList() {
        ArrayList<Music> musicArrayList = db.getAllMusic();
        for (Music m: musicArrayList
             ) {
            Log.i("testLog", "music title " +m.getTitle() + m.getId());
        }
        return musicArrayList;
    }
}
