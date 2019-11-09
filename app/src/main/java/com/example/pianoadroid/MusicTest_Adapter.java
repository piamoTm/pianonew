package com.example.pianoadroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class MusicTest_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    float dp;
    RelativeLayout.LayoutParams layoutParams;



    int hight_pos;

    public void setHight_pos(int hight_pos) {
        this.hight_pos = hight_pos;
    }

    String musicnote;

    public void setMusicnote(String musicnote) {
        this.musicnote = musicnote;
    }

    //아이템 클릭 시 실행 함수 최근 수정사항
    MusicTest_Adapter.ThreadFinishListener tfliListener;

    public interface ThreadFinishListener{
        void onMusicFinish(String finish, Context context);
    }

    public void ThreadFinishListener(MusicTest_Adapter.ThreadFinishListener threadFinishListener){
        tfliListener = threadFinishListener;
    }



    // 이 클래스는 아이템 뷰를 저장하는 뷰홀더 클래스
    // RecyclerView의 행(row)를 표시하는 클래스
    public class MyViewHolder extends RecyclerView.ViewHolder{
        // 음표 이미지들 10개
        ImageView img0, img1, img2, img3, img4, img5, img6, img7, img8, img9, img10;

        // 배경색이 칠해진 음표 이미지
        ImageView img1_color, img2_color, img3_color, img4_color, img5_color, img6_color, img7_color, img8_color;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img0 = itemView.findViewById(R.id.img0);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            img4 = itemView.findViewById(R.id.img4);
            img5 = itemView.findViewById(R.id.img5);
            img6 = itemView.findViewById(R.id.img6);
            img7 = itemView.findViewById(R.id.img7);
            img8 = itemView.findViewById(R.id.img8);


            // 배경색이 칠해진 칼러
            img1_color = itemView.findViewById(R.id.img1_color);
            img2_color = itemView.findViewById(R.id.img2_color);
            img3_color = itemView.findViewById(R.id.img3_color);
            img4_color = itemView.findViewById(R.id.img4_color);
            img5_color = itemView.findViewById(R.id.img5_color);
            img6_color = itemView.findViewById(R.id.img6_color);
            img7_color = itemView.findViewById(R.id.img7_color);
            img8_color = itemView.findViewById(R.id.img8_color);

        }
    }

    ArrayList<String> musicNoteList;


    public MusicTest_Adapter(ArrayList<String> musicNoteList) {
        this.musicNoteList = musicNoteList;

        Log.e("생성자 안 어댑터 값: ", musicNoteList + "");
    }

    // 새로운 뷰홀더 생성
    // RecyclerView가 ViewHolder항목을 나타내기 위해 지정된 새 항목이 필요할 때 호출됨.
    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_musictest, parent, false);
        MusicTest_Adapter.MyViewHolder myViewHolder = new MusicTest_Adapter.MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        Log.e("onBindViewHolder", "musicNoteList값: " + musicNoteList.get(position) + "" + position);
        Log.e("musicNoteList크기: ", musicNoteList.size() + " ");

        // 넘어온 배열의 포지션에 있는 값을 악보에 세팅해줌
        M_names(musicNoteList.get(position) , myViewHolder, position);


    }



    // 계이름(도,레,..)을 받아오면 xml에 동적으로 계이름에 맞게 이미지를 설정함
    public void M_names(String lists, MyViewHolder myViewHolder, int position){

        Log.e("M_names", "M_names 들어옴");
        Log.e("musicnote의 길이: ", musicnote.length() + "");

        dp = myViewHolder.itemView.getResources().getDisplayMetrics().density;
        String [] list = lists.split("");

        Log.e("lists문자열길이: ", lists.length() + " ");

        // 넘어온 배열의 0번째 속한 값들을 split으로 쪼갠 뒤 문자 하나하나를 반복문을 통해 끄집어 냄
        for (int i = 0; i < list.length; i++){
            Log.e("aa["+i+"]", list[i] + "");

            // 뷰홀더의 이미지 아이템 위치
            int index = i + (position * 8);

            Log.e("list0: ", list[0]);
            Log.e("list1: ", list[1]);

            // 뷰홀더 맨 끝의 음계의 색이 들어올 경우 색을 없애고 원상복귀 시킴
            if (index != 1){
                myViewHolder.img8_color.setVisibility(View.INVISIBLE);
            }

            Log.e("hight_pos : ", hight_pos + "");

            // 마지막 음표 색 없앰 , 최근 수정사항
            if (hight_pos == musicnote.length() + 1){
                Log.e("마지막 음표", "ㅇ");

                if (lists.length() == 1){
                    myViewHolder.img1_color.setVisibility(View.INVISIBLE);
                    myViewHolder.img1.setVisibility(View.VISIBLE);
                    tfliListener.onMusicFinish("finish", myViewHolder.itemView.getContext());
                    break;
                }else if(lists.length() == 2){
                    myViewHolder.img2_color.setVisibility(View.INVISIBLE);
                    myViewHolder.img2.setVisibility(View.VISIBLE);
                    tfliListener.onMusicFinish("finish", myViewHolder.itemView.getContext());
                    break;
                }else if(lists.length() == 3){
                    myViewHolder.img3_color.setVisibility(View.INVISIBLE);
                    myViewHolder.img3.setVisibility(View.VISIBLE);
                    tfliListener.onMusicFinish("finish", myViewHolder.itemView.getContext());
                    break;
                }else if(lists.length() == 4){
                    myViewHolder.img4_color.setVisibility(View.INVISIBLE);
                    myViewHolder.img4.setVisibility(View.VISIBLE);
                    tfliListener.onMusicFinish("finish", myViewHolder.itemView.getContext());
                    break;
                }else if(lists.length() == 5){
                    myViewHolder.img5_color.setVisibility(View.INVISIBLE);
                    myViewHolder.img5.setVisibility(View.VISIBLE);
                    tfliListener.onMusicFinish("finish", myViewHolder.itemView.getContext());
                    break;
                }else if(lists.length() == 6){
                    myViewHolder.img6_color.setVisibility(View.INVISIBLE);
                    myViewHolder.img6.setVisibility(View.VISIBLE);
                    tfliListener.onMusicFinish("finish", myViewHolder.itemView.getContext());
                    break;
                }else if(lists.length() == 7){
                    myViewHolder.img7_color.setVisibility(View.INVISIBLE);
                    myViewHolder.img7.setVisibility(View.VISIBLE);
                    tfliListener.onMusicFinish("finish", myViewHolder.itemView.getContext());
                    break;
                }else if(lists.length() == 8){
                    myViewHolder.img8_color.setVisibility(View.INVISIBLE);
                    myViewHolder.img8.setVisibility(View.VISIBLE);
                    tfliListener.onMusicFinish("finish", myViewHolder.itemView.getContext());
                    break;
                }
            }


            // 여기서 부터 배열 첫번째 계이름을 찾음
            if(i == 1){

                Log.e("배열 2번째: ", list[1]);

                if(list[1].equals("도")){
                    Log.e("2번째: ", "도");

                    if (index == hight_pos){

                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img1_color.setImageResource(R.drawable.do_icon);
                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);

                    }else{

                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img1.setImageResource(R.drawable.do_icon);
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }


                }else if(list[1].equals("레")){

                    if (index == hight_pos){
                        Log.e("0번째: ", "레");
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);

                    }else{
                        Log.e("0번째: ", "레");
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }


                }else if(list[1].equals("미")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }


                }else if(list[1].equals("파")){

                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }


                }else if(list[1].equals("솔")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);

                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }


                }else if(list[1].equals("라")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);

                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }


                }else if(list[1].equals("시")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }


                }else if(list[1].equals(" ")){

                }


                if (index == hight_pos){
                    myViewHolder.img1.setVisibility(View.INVISIBLE);
                    myViewHolder.img1_color.setVisibility(View.VISIBLE);
                }

            }else if(i == 2){
                Log.e("배열 2번째: ", list[2]);

                if(list[2].equals("도")){
                    if (index == hight_pos){
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img2_color.setImageResource(R.drawable.do_icon);
                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);
                    }else{
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img2.setImageResource(R.drawable.do_icon);
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }


                }else if(list[2].equals("레")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }



                }else if(list[2].equals("미")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);

                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }



                }else if(list[2].equals("파")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);

                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }


                }else if(list[2].equals("솔")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);

                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }



                }else if(list[2].equals("라")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);

                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }


                }else if(list[2].equals("시")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);

                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }


                }else if(list[2].equals(" ")){

                }

                //if (index == hight_pos){
                   // myViewHolder.img2.setVisibility(View.INVISIBLE);
                   // myViewHolder.img2_color.setVisibility(View.VISIBLE);
                //}

                //myViewHolder.img2.setVisibility(View.VISIBLE);
                //myViewHolder.img2.setLayoutParams(layoutParams);

            }else if(i == 3){
                Log.e("배열 3번째: ", list[3]);

                if(list[3].equals("도")){
                    if (index == hight_pos){
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.bottomMargin = (int)(7*dp);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img3_color.setImageResource(R.drawable.do_icon);
                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.bottomMargin = (int)(7*dp);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img3.setImageResource(R.drawable.do_icon);
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }


                }else if(list[3].equals("레")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }


                }else if(list[3].equals("미")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }



                }else if(list[3].equals("파")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }


                }else if(list[3].equals("솔")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }


                }else if(list[3].equals("라")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }


                }else if(list[3].equals("시")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }


                }else if(list[3].equals(" ")){

                }

//                if (index == hight_pos){
//                    myViewHolder.img3.setVisibility(View.INVISIBLE);
//                    myViewHolder.img3_color.setVisibility(View.VISIBLE);
//                }

                //myViewHolder.img3.setVisibility(View.VISIBLE);
                //myViewHolder.img3.setLayoutParams(layoutParams);
            }else if(i == 4){
                Log.e("배열 3번째: ", list[4]);

                if(list[4].equals("도")){

                    if (index == hight_pos){
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img4_color.setImageResource(R.drawable.do_icon);
                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img4.setImageResource(R.drawable.do_icon);
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }


                }else if(list[4].equals("레")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }


                }else if(list[4].equals("미")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }


                }else if(list[4].equals("파")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }


                }else if(list[4].equals("솔")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }


                }else if(list[4].equals("라")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }


                }else if(list[4].equals("시")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }


                }else if(list[4].equals(" ")){

                }

//                if (index == hight_pos){
//                    myViewHolder.img4.setVisibility(View.INVISIBLE);
//                    myViewHolder.img4_color.setVisibility(View.VISIBLE);
//                }

            }else if(i == 5){
                Log.e("배열 5번째: ", list[5]);

                if(list[5].equals("도")){
                    if (index == hight_pos){
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img5_color.setImageResource(R.drawable.do_icon);
                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img5.setImageResource(R.drawable.do_icon);
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }


                }else if(list[5].equals("레")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }


                }else if(list[5].equals("미")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }

                }else if(list[5].equals("파")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }


                }else if(list[5].equals("솔")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }


                }else if(list[5].equals("라")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }


                }else if(list[5].equals("시")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }


                }else if(list[5].equals(" ")){

                }

//                if (index == hight_pos){
//                    myViewHolder.img5.setVisibility(View.INVISIBLE);
//                    myViewHolder.img5_color.setVisibility(View.VISIBLE);
//                }


            }else if(i == 6){
                Log.e("배열 6번째: ", list[6]);

                if(list[6].equals("도")){
                    if (index == hight_pos){
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img6_color.setImageResource(R.drawable.do_icon);
                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);
                    }else{
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img6.setImageResource(R.drawable.do_icon);
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }


                }else if(list[6].equals("레")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }


                }else if(list[6].equals("미")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }


                }else if(list[6].equals("파")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);

                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }


                }else if(list[6].equals("솔")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }


                }else if(list[6].equals("라")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }


                }else if(list[6].equals("시")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }


                }else if(list[6].equals(" ")){

                }

            }else if(i == 7){
                Log.e("배열 7번째: ", list[7]);

                if(list[7].equals("도")){
                    if (index == hight_pos){
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img7_color.setImageResource(R.drawable.do_icon);
                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img7.setImageResource(R.drawable.do_icon);
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }


                }else if(list[7].equals("레")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }


                }else if(list[7].equals("미")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }


                }else if(list[7].equals("파")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }


                }else if(list[7].equals("솔")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }


                }else if(list[7].equals("라")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }


                }else if(list[7].equals("시")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                        layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }


                }else if(list[7].equals(" ")){

                }

            }else if(i == 8){
                Log.e("배열 8번째: ", list[8]);

                if(list[8].equals("도")){
                    if (index == hight_pos){
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img8_color.setImageResource(R.drawable.do_icon);
                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        // 이미지 크기 조절
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img8.setImageResource(R.drawable.do_icon);
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }



                }else if(list[8].equals("레")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }


                }else if(list[8].equals("미")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }



                }else if(list[8].equals("파")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }


                }else if(list[8].equals("솔")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }


                }else if(list[8].equals("라")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);

                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }


                }else if(list[8].equals("시")){
                    if (index == hight_pos){
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);

                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7_color.setVisibility(View.INVISIBLE);

                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                        layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                        //layoutParams.rightMargin = (int)(22*dp);
                        layoutParams.bottomMargin = (int)(7*dp);
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }


                }else if(list[8].equals(" ")){

                }

            }

            // 마지막 음표 색 없앰
            if (hight_pos == musicnote.length() + 1){
                myViewHolder.img1_color.setVisibility(View.INVISIBLE);
                myViewHolder.img1.setVisibility(View.VISIBLE);

            }


        }

    }

    @Override
    public int getItemCount() {
        return musicNoteList.size();
    }
}
