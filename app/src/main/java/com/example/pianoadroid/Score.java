package com.example.pianoadroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

//MusicScore.java 코드 정리


public class Score extends AppCompatActivity {
    //음표png를 넣을 이미지뷰 8개
    ImageView[] imageViews;
    int[] imageViewID; //이미지뷰 아이디 8개

    //RelativeLayout relative_1;

    RelativeLayout.LayoutParams layoutParams;
    float dp;

    int width = 35; //너비
    int height = 35; //높이
    int space = 7; //반음올리기위한 크기, 선에 걸치게 하려고


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_score);

        imageViews = new ImageView[8];
        imageViewID = new int[8];

        imageViews[0] = findViewById(R.id.img1);
        imageViews[1] = findViewById(R.id.img2);
        imageViews[2] = findViewById(R.id.img3);
        imageViews[3] = findViewById(R.id.img4);
        imageViews[4] = findViewById(R.id.img5);
        imageViews[5] = findViewById(R.id.img6);
        imageViews[6] = findViewById(R.id.img7);
        imageViews[7] = findViewById(R.id.img8);

        imageViewID[0] = R.id.img0;
        imageViewID[1] = R.id.img1;
        imageViewID[2] = R.id.img2;
        imageViewID[3] = R.id.img3;
        imageViewID[4] = R.id.img4;
        imageViewID[5] = R.id.img5;
        imageViewID[6] = R.id.img6;
        imageViewID[7] = R.id.img7;


        //DisplayMetrics : 스마트폰의 크기정보를 구할때 사용하는 클래
        //density : mdpi를 기준으로 한 배율. 스케일링 시 곱해지는 값
        //density : 디스플레이의 논리적인 밀, dip단위에 사용되는 곱하기 인자. dip는 xml에서 사용되는 dp를 의미함.
        dp = getResources().getDisplayMetrics().density;

        // 이미지뷰의 레이아웃을 가로 35dp, 세로 35dp로 지정
        layoutParams = new RelativeLayout.LayoutParams((int)(width*dp), (int)(height*dp));

        // layout_above적용
        //layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
        //layoutParams.bottomMargin = (int)(7*dp);
        //img1.setVisibility(View.VISIBLE);
        //img1.setLayoutParams(layoutParams);

        // 임시로 디비에서 꺼내온 코드을 담는 arrayList
        ArrayList<String> db_value=  new ArrayList<>();
        db_value.add("C"); //dump
        db_value.add("D");
        db_value.add("E");
        db_value.add("F");
        db_value.add("G");
        db_value.add(" ");
        db_value.add("A");
        db_value.add("B");


        // 함수호출
        makeScore(db_value);
        Log.e("db_value: ", db_value.size() + " ");
    }

    // 계이름(도,레,..)을 받아오면 xml에 동적으로 계이름에 맞게 위치를 설정함
    public void makeScore(ArrayList<String> noteArr){

        dp = getResources().getDisplayMetrics().density;
        // 이미지뷰의 레이아웃을 가로 35dp, 세로 35dp로 지정
        //layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));

        // 배열에 있는 코드 수만큼 반복문을 돌림
        for (int i = 0; i < noteArr.size(); i++){

            // 코드에 따라 음표의 위치를 조절
            Log.i("testLog", i+"번째 계이름 " + noteArr.get(i));
            if(!noteArr.get(i).equals(" ")){ //공란이 아닐때 == 도~시일
                if(noteArr.get(i).equals("C")){
                    // 이미지 크기 조절
                    layoutParams = new RelativeLayout.LayoutParams((int)(width*dp), (int)(width*dp));
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, imageViewID[i]);
                    layoutParams.bottomMargin = (int)(space * dp);
                    imageViews[i].setImageResource(R.drawable.do_icon);

                }else if(noteArr.get(i).equals("D")){
                    layoutParams = new RelativeLayout.LayoutParams((int)(width*dp), (int)(width*dp));
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, imageViewID[i]);

                }else if(noteArr.get(i).equals("E")){
                    layoutParams = new RelativeLayout.LayoutParams((int)(width*dp), (int)(width*dp));
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, imageViewID[i]);
                    layoutParams.bottomMargin = (int)(space * dp);

                }else if(noteArr.get(i).equals("F")){
                    layoutParams = new RelativeLayout.LayoutParams((int)(width*dp), (int)(width*dp));
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, imageViewID[i]);

                }else if(noteArr.get(i).equals("G")){
                    layoutParams = new RelativeLayout.LayoutParams((int)(width*dp), (int)(width*dp));
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, imageViewID[i]);
                    layoutParams.bottomMargin = (int)(space * dp);

                }else if(noteArr.get(i).equals("A")){
                    layoutParams = new RelativeLayout.LayoutParams((int)(width*dp), (int)(width*dp));
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, imageViewID[i]);

                }else if(noteArr.get(i).equals("B")){
                    layoutParams = new RelativeLayout.LayoutParams((int)(width*dp), (int)(width*dp));
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.bottomMargin = (int)(space * dp);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, imageViewID[i]);
                }
                imageViews[i].setVisibility(View.VISIBLE);
                imageViews[i].setLayoutParams(layoutParams);
            }
        }
    }
}
