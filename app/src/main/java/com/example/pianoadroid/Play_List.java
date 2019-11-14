package com.example.pianoadroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class Play_List extends AppCompatActivity {

    private final int MENU_READ = 0;
    private final int MENU_WRITE = 1;
    private final int REQUEST_ACT = 111;

    private final int WRITE_NEW = 111;//작곡하기 -> 새로만들기
    private final int WRITE_OLD = 222;//작곡하기 -> 기존곡


    private PlayList_RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView mBackbtn;

    private ArrayList<Music> musicArr; //노래 목록

    //SQLite db 개체 생성
    SQLiteOpenHelper db;
    //DBMyProductHelper_Read readDb; //연주하기용 db
    //DBMyProductHelper_Write writeDb; //작곡용 db


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //가로모드고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_play_list);

        //intent값 받기.
        Intent receiveIntent = getIntent();
        int menuIndex = receiveIntent.getIntExtra("menuIndex" , 0);

        if(menuIndex == MENU_READ){ //연주하기 버튼으로 이동해옴

            //SQLite db helper init 초기화
            db = new DBMyProductHelper_Read(this);

        }else{//작곡하기 버튼으로 왔음

            //SQLite db helper init 초기화
            db = new DBMyProductHelper_Write(this);
        }

        //노래 목록 가져오기
        musicArr = loadMusicList();//데이터 불러오기


        //리사이클러뷰 세팅
        init(musicArr, menuIndex);

        //뒤로가기 버튼 이벤트
        mBackbtn = (ImageView)findViewById(R.id.backpressBtn);
        mBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        db.deleteMusic(1);
//
//        int beat[] = {1,1,1,1,1,1,2,0,1,1,1,1,1,1,2,0,1,1,1,1,1,1,2,0,1,1,1,1,1,1,2,0,1,1,1,1,1,1,2,0,1,1,1,1,1,1,2,0};
//        Music music = new Music("작은 별", "미상", "CCGGAAG FFEEDDC GGFFEED GGFFEED CCGGAAG FFEEDDC ",beat);
//        db.addMusic(music);

//        int beat1[] = {1,1,2,0,1,1,2,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,2,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,2,0,1,1,1,1,1,1,1,0,1,1,2,0,1,1,2,0,1,1,1,1,1,1,1,0};
//        Music music = new Music("나비야", "미상", "GEE FDD CDEFGGG GEEEFDD CEGGEEE DDDDDEF EEEEEFG GEE FDD CEGGEEE ",beat1);
//        db.addMusic(music);
//
//        int beat2[] = {1,1,1,1,1,1,2,0,1,1,1,1,2,0,0,0,1,1,1,1,1,1,2,0,1,1,1,1,2,0,0,0};
//        Music music1 = new Music("학교종", "미상", "GGAAGGE GGEED   GGAAGGE GEDEC   ",beat2);
//        db.addMusic(music1);


    }

    //리사이클러뷰 세팅
    //menuIndex에 따라 리사이클러뷰 모양이 좀 달라야해.
    private void init(ArrayList<Music> musicArr, int menuIndex) {
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new PlayList_RecyclerAdapter(musicArr, menuIndex, new PlayList_RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Intent intent = new Intent(getApplicationContext(), MakeMusic.class);
                intent.putExtra("from", WRITE_NEW);
                startActivityForResult(intent, REQUEST_ACT);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Music> loadMusicList() {
        ArrayList<Music> musicArrayList;
        if(db instanceof DBMyProductHelper_Read){
            musicArrayList = ((DBMyProductHelper_Read)db).getAllMusic();
        }else{
            musicArrayList = ((DBMyProductHelper_Write)db).getAllMusic();
        }

        for (Music m: musicArrayList) {
            Log.i("testLog", "music title " +m.getTitle() + m.getId());
        }
        return musicArrayList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("testLog", "onActivityResult] requestCode" +requestCode +" resultCode " +resultCode);

        if(resultCode == RESULT_OK && requestCode == REQUEST_ACT){

            boolean isSave = data.getBooleanExtra("result_msg",false);

            Log.i("testLog", "잘돌아옴 "+isSave);
            if(isSave){
                adapter.setListData(loadMusicList());
                adapter.notifyDataSetChanged();
            }
        }
    }

}
