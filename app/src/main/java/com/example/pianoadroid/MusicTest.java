package com.example.pianoadroid;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MusicTest extends AppCompatActivity {

    //리사이클러뷰
    RecyclerView mRecyclerView;
    MusicTest_Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;


    // 원곡듣기 버튼
    Button btn_listen, btn_stop;

    // 최종 계이름을 담는 변수
    String musicnote;

    // 임시로 디비에서 악보의 계이름을 코드상으로 받는 함수
    String musicnote_eng;

    // 문자열로 온 악보정보를 split로 나눠서 저장한 배열변수
    String [] array;

    // 초 를 담는 변수
    int index_value = 0;

    // 악보의 계이름을 담는 리스트 변수
    ArrayList<String> MusicNoteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_test);

        btn_listen = findViewById(R.id.btn_listen);
        btn_stop = findViewById(R.id.btn_stop);

        //리사이클러뷰 변수에 위젯 대입
        mRecyclerView = findViewById(R.id.recycler_music_note);

        //이 설정을 사용하면 변경 사항을 알고 있는 경우 성능을 향상 시킬 수 있슴.
        mRecyclerView.setHasFixedSize(true);

        //LinearLayout으로 layoutManager을 정함
        layoutManager = new LinearLayoutManager(this);

        //리사이클러뷰에 위에서 지정한 layoutManager인 LinearLayout을 대입.
        mRecyclerView.setLayoutManager(layoutManager);


        // 디비에서 계이름 코드를 받는 변수
        musicnote_eng = "BDEFGABCDEFGAB";

        // 한글로 바꾸는 변수
        String musicnote_kor = "";

        String musicnote_eng_array[] = musicnote_eng.split("");
        Log.e("musicnote_eng_array크기: ", musicnote_eng_array.length + "");

        for (int i = 0; i < musicnote_eng_array.length; i++){
            if (i != 0){
                if (musicnote_eng_array[i].equals("C")){
                    musicnote_eng_array[i] = "도";
                    musicnote_kor += musicnote_eng_array[i];
                }else if(musicnote_eng_array[i].equals("D")){
                    musicnote_eng_array[i] = "레";
                    musicnote_kor += musicnote_eng_array[i];
                }else if(musicnote_eng_array[i].equals("E")){
                    musicnote_eng_array[i] = "미";
                    musicnote_kor += musicnote_eng_array[i];
                }else if(musicnote_eng_array[i].equals("F")){
                    musicnote_eng_array[i] = "파";
                    musicnote_kor += musicnote_eng_array[i];
                }else if(musicnote_eng_array[i].equals("G")){
                    musicnote_eng_array[i] = "솔";
                    musicnote_kor += musicnote_eng_array[i];
                }else if(musicnote_eng_array[i].equals("A")){
                    musicnote_eng_array[i] = "라";
                    musicnote_kor += musicnote_eng_array[i];
                }else if(musicnote_eng_array[i].equals("B")){
                    musicnote_eng_array[i] = "시";
                    musicnote_kor += musicnote_eng_array[i];
                }
            }
        }

        Log.e("한글변환계이름: ", musicnote_kor);



        // 코드로 온 계이름을 한글로 변환 후 musicnote변수에 넣음
        musicnote = musicnote_kor;


        // 8개씩 악보를 담아서 배열로 보낸 뒤 초기화 하는 변수
        String array_value = "";

        // 문자열로 온 악보정보를 split로 나눠서 저장한 배열변수
        array = musicnote.split("");


        Log.e("배열에 담긴 악보 채우기 전: ", MusicNoteList + "");

        for (int i = 0; i < array.length; i++){


            // array_value라는 문자열에다가 계이름들을 계속 담음
            array_value += array[i];
            Log.e("array_value값: " , i+"번째: "+array[i]);

            // 8개가 되었을 때
            if(i % 8 == 0){

                // 0번째는 빈값이라서 1부터 음계가 나옴
                if (i != 0){

                    Log.e(i+"번째까지의 계이름 목록: ", array_value);
                    // 악보의 계이름을 담는 리스트 변수에 9번째까지의 계이름을 담음
                    MusicNoteList.add(array_value);

                    Log.e("배열에 담기는 중인 악보: ", MusicNoteList + "");
                    // 계이름담는 문자열 초기화
                    array_value = "";
                }
            }

            if(i == musicnote.length()){
                Log.e("남은 array_value값: ", array_value);

                if (!array_value.equals("O")){
                    // 남은 계이름들을 마지막으로 배열에 넣음
                    MusicNoteList.add(array_value);
                    Log.e("배열에 담기는 중인 악보: ", MusicNoteList + "");
                }
            }
        }

        Log.e("계이름 수: ", musicnote.length() + " ");
        Log.e("최종 배열에 담긴 악보: ", MusicNoteList + "");


        // 어댑터 생성자에 악보리스트를 넣음
        mAdapter = new MusicTest_Adapter(MusicNoteList);

        //
        mRecyclerView.setAdapter(mAdapter);

        // 악보전체를 어댑터에 보냄
        mAdapter.setMusicnote(musicnote);

        // 중지 버튼 클릭 이벤트
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 원곡중지 버튼 클릭 시 발생 메서드
                btn_listen_func_stop();
            }
        });

        // 원곡재생 클릭 이벤트
        btn_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 원곡듣기 버튼 클릭 시 발생 메서드
                btn_listen_func();
            }
        });

    }
    // 원곡중지 버튼 클릭 시 발생 메서드
    public void btn_listen_func_stop(){
        btn_stop.setVisibility(View.INVISIBLE);
        btn_listen.setVisibility(View.VISIBLE);
    }

    // 원곡듣기 버튼 클릭 시 발생 메서드
    public void btn_listen_func(){
        btn_listen.setVisibility(View.INVISIBLE);
        btn_stop.setVisibility(View.VISIBLE);
        Log.e("원곡재생 클릭", "~~~");

        music_thread thread = new music_thread();
        thread.start();

    }

    // 노래를 1초마다 실행시키는 스레드
    class music_thread extends Thread{

        @Override
        public void run() {
            super.run();
            Log.e("MusicNoteList사이즈: ", MusicNoteList.size() + "");

            for (int i = 1; i < array.length+1; i++){
                try {
                    // 초
                    index_value = i;

                    // 핸들러를 통해 UI를 바꿈
                    handler.sendEmptyMessage(1);

                    // 1초씩 딜레이를 줌
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        }
    }



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1){
                mAdapter.setHight_pos(index_value);
                mAdapter.notifyDataSetChanged();
            }
        }
    };



}
