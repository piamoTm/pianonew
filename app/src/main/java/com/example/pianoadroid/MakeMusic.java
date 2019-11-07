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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_make_music);

        mBackBtn = (ImageView)findViewById(R.id.back_btn);
        mUploadBtn = (ImageButton)findViewById(R.id.uploadbtn);
        mPlayBtn =(ImageButton)findViewById(R.id.playbtn);

        init();
        //getData();
        //show();// 안내 다이얼로그


        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        Music data = new Music();
        adapter.addItem(data);
        adapter.notifyDataSetChanged();

    }

//    private void getData() {
//        // 임의의 데이터입니다.
//        List<String> listTitle = Arrays.asList("작은별","비행기","비행기","비행기","비행기","비행기");
//        List<String> listWriter = Arrays.asList("미상","미상","미상","미상","미상","미상");
//        List<Integer> listId = Arrays.asList(0,1,2,3,4,5);
//
//        for (int i = 0; i < listTitle.size(); i++) {
//            // 각 List의 값들을 data 객체에 set 해줍니다.
//
//            Music data = new Music();
//            data.setTitle(listTitle.get(i));   // 노래제목
//            data.setWriter(listWriter.get(i));  // 작곡가
//            data.setId(listId.get(i));   // 노래 고유 ID
//
//            // 각 값이 들어간 data를 adapter에 추가합니다.
//            adapter.addItem(data);
//        }
//
//        // adapter의 값이 변경되었다는 것을 알려줍니다.
//        v.notifyDataSetChanged();
//    }
//    void show()
//    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//       // builder.setTitle("AlertDialog Title");
//        builder.setMessage("건반을 눌러주세요.:)");
//        builder.setPositiveButton("확인",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        //Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        builder.show();
//    }
//
//
//





}
