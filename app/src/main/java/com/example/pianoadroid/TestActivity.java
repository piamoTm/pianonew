package com.example.pianoadroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    //TestAdapter testAdapter;
    testAdapter2 t;
    int highlightPos;

    testAdapter3 testAdapter3;
    ArrayList<String> noteArr;
    private RecyclerView recyclerView;
    int i;
    String[] str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Music music = new Music();
        music.setScore("ABC");
        highlightPos = -11;

        str = new String[7];
        str[0] = "C";
        str[1] = "D";
        str[2] = "E";
        str[3] = "F";
        str[4] = "G";
        str[5] = "A";
        str[6] = "B";

        //리사이클러뷰 세팅
        recyclerView = findViewById(R.id.recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteArr.add(str[(i++)%7]);
               // Log.i("TESTLOG_YYJ","postion : "+testAdapter3.getItemCount());
                recyclerView.getLayoutManager().scrollToPosition(testAdapter3.getItemCount()-1);
                testAdapter3.notifyDataSetChanged();

            }
        });



        //testAdapter = new TestAdapter(music);
        //recyclerView.setAdapter(testAdapter);
        t = new testAdapter2(music,highlightPos);
        //recyclerView.setAdapter(t);

        //하이라이트할 번호 지정
        t.setHighlightPos(highlightPos);
        //리사이클러뷰 갱신
        t.notifyDataSetChanged();
        highlightNext();

        //리사이클러뷰 어댑터3 세팅
        noteArr = new ArrayList<>();
        noteArr.add("C");
        noteArr.add("D");
        testAdapter3 = new testAdapter3(noteArr);

        recyclerView.setAdapter(t);


    }
    //하이라이트 다음으로 넘기
    void highlightNext(){
        highlightPos++;
        //노래의 Score가 마지막인지 체크하는 if 문 추가 할것
        //highlightPos++ -11 로 초기화 해서 없앤다.
        //
        t.setHighlightPos(highlightPos);
        t.notifyDataSetChanged();
    }


}
