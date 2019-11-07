package com.example.pianoadroid;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

public class Play_List extends AppCompatActivity {


    private PlayList_RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView mBackbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //가로모드고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_play_list);

        mBackbtn = (ImageView)findViewById(R.id.backpressBtn);

        init();

        getData();

        //뒤로가기 버튼
        mBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new PlayList_RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList("작은별","비행기","비행기","비행기","비행기","비행기");
        List<String> listWriter = Arrays.asList("미상","미상","미상","미상","미상","미상");
        List<Integer> listId = Arrays.asList(0,1,2,3,4,5);

        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.

            Music data = new Music();
            data.setTitle(listTitle.get(i));   // 노래제목
            data.setWriter(listWriter.get(i));  // 작곡가
            data.setId(listId.get(i));   // 노래 고유 ID

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}
