package com.example.pianoadroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Main_menu extends AppCompatActivity {

    private Button mPlayBtn, mWriteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mPlayBtn = (Button)findViewById(R.id.play_btn);  //연주하기
        mWriteBtn = (Button)findViewById(R.id.write_btn);  // 작곡하기


        //연주하기
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 작곡하기
        mWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakeMusic.class);
                //intent.putExtra("imageUri", uri);
                startActivity(intent);

            }
        });


    }
}
