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
import android.widget.Toast;

import java.util.ArrayList;


public class MusicTest_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    float dp;
    RelativeLayout.LayoutParams layoutParams;

    // 액티비티로 hight_position값을 넘겨주기 위한 변수. 이전 곡을 다시 연습하기 위헤
    int sendHight_pos;

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


    // 연습하기 중 이미 끝난 음을 치고 싶을 때 음계를 클릭 시 해당 위치의 hight_position과 음계를 액티비티로 보내기 위한 리스너
    MusicTest_Adapter.BeforeMusicNoteListener bListener;
    public interface BeforeMusicNoteListener{
        void onBeforeMusicNote(String value, int hight_pos, int position);
    }
    public void BeforeMusicNoteListener(MusicTest_Adapter.BeforeMusicNoteListener beforeMusicNote){
        bListener = beforeMusicNote;
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
    ArrayList<String> musicNoteBitList;


    public MusicTest_Adapter(ArrayList<String> musicNoteList, ArrayList<String> musicNoteBitList) {
        this.musicNoteList = musicNoteList;
        this.musicNoteBitList = musicNoteBitList;

        Log.e("어댑터 안 musicNoteList 값: ", musicNoteList + "");
        Log.e("어댑터 안 musicNoteBitList 값: ", musicNoteBitList + "");
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

        myViewHolder.img1.setVisibility(View.INVISIBLE);
        myViewHolder.img2.setVisibility(View.INVISIBLE);
        myViewHolder.img3.setVisibility(View.INVISIBLE);
        myViewHolder.img4.setVisibility(View.INVISIBLE);
        myViewHolder.img5.setVisibility(View.INVISIBLE);
        myViewHolder.img6.setVisibility(View.INVISIBLE);
        myViewHolder.img7.setVisibility(View.INVISIBLE);
        myViewHolder.img8.setVisibility(View.INVISIBLE);

        myViewHolder.img1_color.setVisibility(View.INVISIBLE);
        myViewHolder.img2_color.setVisibility(View.INVISIBLE);
        myViewHolder.img3_color.setVisibility(View.INVISIBLE);
        myViewHolder.img4_color.setVisibility(View.INVISIBLE);
        myViewHolder.img5_color.setVisibility(View.INVISIBLE);
        myViewHolder.img6_color.setVisibility(View.INVISIBLE);
        myViewHolder.img7_color.setVisibility(View.INVISIBLE);
        myViewHolder.img8_color.setVisibility(View.INVISIBLE);

        myViewHolder.img1_color.setImageResource(R.drawable.music_icon);
        myViewHolder.img2_color.setImageResource(R.drawable.music_icon);
        myViewHolder.img3_color.setImageResource(R.drawable.music_icon);
        myViewHolder.img4_color.setImageResource(R.drawable.music_icon);
        myViewHolder.img5_color.setImageResource(R.drawable.music_icon);
        myViewHolder.img6_color.setImageResource(R.drawable.music_icon);
        myViewHolder.img7_color.setImageResource(R.drawable.music_icon);
        myViewHolder.img8_color.setImageResource(R.drawable.music_icon);

        myViewHolder.img1.setImageResource(R.drawable.music_icon);
        myViewHolder.img2.setImageResource(R.drawable.music_icon);
        myViewHolder.img3.setImageResource(R.drawable.music_icon);
        myViewHolder.img4.setImageResource(R.drawable.music_icon);
        myViewHolder.img5.setImageResource(R.drawable.music_icon);
        myViewHolder.img6.setImageResource(R.drawable.music_icon);
        myViewHolder.img7.setImageResource(R.drawable.music_icon);
        myViewHolder.img8.setImageResource(R.drawable.music_icon);


        // 각 포지션에 맞는 음계들(8개)
        final String [] list = musicNoteList.get(position).split("");

        Log.e("index: ", 1 + (position * 8) + "");

        // 이 부분부터 첫번째 음계부터 마지막 음계까지의 클릭 리스너
        myViewHolder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택한 음계의 hight_position값을 구함
                sendHight_pos = 1 + (position * 8);
                // 액티비티에 해당 음계와 hight_position값과 position값을 인자로 보냄
                bListener.onBeforeMusicNote(list[1], sendHight_pos, position);
            }
        });

        myViewHolder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택한 음계의 hight_position값을 구함
                sendHight_pos = 2 + (position * 8);
                // 액티비티에 해당 음계와 hight_position과 position값을 인자로 보냄
                bListener.onBeforeMusicNote(list[2], sendHight_pos, position);
            }
        });
        myViewHolder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택한 음계의 hight_position값을 구함
                sendHight_pos = 3 + (position * 8);
                // 액티비티에 해당 음계와 hight_position과 position값을 인자로 보냄
                bListener.onBeforeMusicNote(list[3], sendHight_pos, position);
            }
        });
        myViewHolder.img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택한 음계의 hight_position값을 구함
                sendHight_pos = 4 + (position * 8);
                // 액티비티에 해당 음계와 hight_position과 position값을 인자로 보냄
                bListener.onBeforeMusicNote(list[4], sendHight_pos, position);
            }
        });
        myViewHolder.img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택한 음계의 hight_position값을 구함
                sendHight_pos = 5 + (position * 8);
                // 액티비티에 해당 음계와 hight_position과 position값을 인자로 보냄
                bListener.onBeforeMusicNote(list[5], sendHight_pos, position);
            }
        });
        myViewHolder.img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택한 음계의 hight_position값을 구함
                sendHight_pos = 6 + (position * 8);
                // 액티비티에 해당 음계와 hight_position과 position값을 인자로 보냄
                bListener.onBeforeMusicNote(list[6], sendHight_pos, position);
            }
        });
        myViewHolder.img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택한 음계의 hight_position값을 구함
                sendHight_pos = 7 + (position * 8);
                // 액티비티에 해당 음계와 hight_position과 position값을 인자로 보냄
                bListener.onBeforeMusicNote(list[7], sendHight_pos, position);
            }
        });
        myViewHolder.img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택한 음계의 hight_position값을 구함
                sendHight_pos = 8 + (position * 8);
                // 액티비티에 해당 음계와 hight_position과 position값을 인자로 보냄
                bListener.onBeforeMusicNote(list[8], sendHight_pos, position);
            }
        });


        // 넘어온 배열의 포지션에 있는 값을 악보에 세팅해줌
        M_names(musicNoteList.get(position) , myViewHolder, position, musicNoteBitList.get(position));


    }



    // 계이름(도,레,..)을 받아오면 xml에 동적으로 계이름에 맞게 이미지를 설정함
    public void M_names(String lists, MyViewHolder myViewHolder, int position, String bit_lists){

        Log.e("M_names", "M_names 들어옴");
        Log.e("musicnote의 길이: ", musicnote.length() + "");
        Log.e("bit_lists의 길이: ", bit_lists.length() + "");

        dp = myViewHolder.itemView.getResources().getDisplayMetrics().density;
        String [] list = lists.split("");
        String [] bit_list = bit_lists.split("");

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
                    // 마지막 음표가 빈칸이면
                    if(list[1].equals(" ")){
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                    }else{
                        myViewHolder.img1_color.setVisibility(View.INVISIBLE);
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                    }

                }else if(lists.length() == 2){
                    // 마지막 음표가 빈칸이면
                    if(list[2].equals(" ")){
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                    }else{
                        myViewHolder.img2_color.setVisibility(View.INVISIBLE);
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                    }

                }else if(lists.length() == 3){
                    // 마지막 음표가 빈칸이면
                    if(list[3].equals(" ")){
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                    }else{
                        myViewHolder.img3_color.setVisibility(View.INVISIBLE);
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                    }

                }else if(lists.length() == 4){
                    // 마지막 음표가 빈칸이면
                    if(list[4].equals(" ")){
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                    }else{
                        myViewHolder.img4_color.setVisibility(View.INVISIBLE);
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                    }

                }else if(lists.length() == 5){
                    // 마지막 음표가 빈칸이면
                    if(list[5].equals(" ")){
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                    }else{
                        myViewHolder.img5_color.setVisibility(View.INVISIBLE);
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                    }

                }else if(lists.length() == 6){
                    // 마지막 음표가 빈칸이면
                    if(list[6].equals(" ")){
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                    }else{
                        myViewHolder.img6_color.setVisibility(View.INVISIBLE);
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                    }

                }else if(lists.length() == 7){
                    // 마지막 음표가 빈칸이면
                    if(list[7].equals(" ")){
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                    }else{
                        myViewHolder.img7_color.setVisibility(View.INVISIBLE);
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                    }

                }else if(lists.length() == 8){
                    // 마지막 음표가 빈칸이면
                    if(list[8].equals(" ")){
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                    }else{
                        myViewHolder.img8_color.setVisibility(View.INVISIBLE);
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                    }
                }

                tfliListener.onMusicFinish("finish", myViewHolder.itemView.getContext());
               // break;
            }


            // 여기서 부터 배열 첫번째 계이름을 찾음
            if(i == 1){
                // 이미지 크기 조절
                layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                Log.e("배열 2번째: ", list[1]);

                if(list[1].equals("도")){
                    Log.e("2번째: ", "도");
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1_color.setImageResource(R.drawable.do_icon);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);

                        if (bit_list[1].equals("1")){
                            myViewHolder.img1_color.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img1_color.setImageResource(R.drawable.note2_do);
                        }

                    }else{
                        if (bit_list[1].equals("1")){
                            myViewHolder.img1.setImageResource(R.drawable.do_icon);
                        }else{
                            myViewHolder.img1.setImageResource(R.drawable.note2_do);
                        }
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }


                }else if(list[1].equals("레")){
                    Log.e("0번째: ", "레");
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 박자가 2박이면
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);

                    }else{
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }


                }else if(list[1].equals("미")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 박자가 2박이면
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }


                }else if(list[1].equals("파")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 박자가 2박이면
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }


                }else if(list[1].equals("솔")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 박자가 2박이면
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }


                }else if(list[1].equals("라")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 박자가 2박이면
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);

                    }else{
                        // 박자가 2박이면
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }


                }else if(list[1].equals("시")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 박자가 2박이면
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }
                    // 높은 도
                }else if(list[1].equals("두")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view5);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img0);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 박자가 2박이면
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img1_color.setLayoutParams(layoutParams);
                        myViewHolder.img1.setVisibility(View.INVISIBLE);
                        myViewHolder.img1_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[1].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img1.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img1.setVisibility(View.VISIBLE);
                        myViewHolder.img1.setLayoutParams(layoutParams);
                    }

                }else if(list[1].equals(" ")){
                    myViewHolder.img1.setVisibility(View.INVISIBLE);
                    myViewHolder.img1_color.setVisibility(View.INVISIBLE);
                }


            }else if(i == 2){
                Log.e("배열 2번째: ", list[2]);
                // 이미지 크기 조절
                layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));

                if(list[2].equals("도")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[1].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[1] + "]");
                            myViewHolder.img1.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img1.setVisibility(View.VISIBLE);
                            myViewHolder.img1_color.setVisibility(View.INVISIBLE);
                        }
                        if (bit_list[2].equals("1")){
                            myViewHolder.img2_color.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img2_color.setImageResource(R.drawable.note2_do);
                        }

                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);
                    }else{
                        if (bit_list[2].equals("1")){
                            myViewHolder.img2.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img2.setImageResource(R.drawable.note2_do);
                        }

                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }


                }else if(list[2].equals("레")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[1].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[1] + "]");
                            myViewHolder.img1.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img1.setVisibility(View.VISIBLE);
                            myViewHolder.img1_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[2].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img2_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[2].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img2.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }

                }else if(list[2].equals("미")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[1].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[1] + "]");
                            myViewHolder.img1.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img1.setVisibility(View.VISIBLE);
                            myViewHolder.img1_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[2].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img2_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);

                    }else{
                        // 박자가 2박이면
                        if (bit_list[2].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img2.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }



                }else if(list[2].equals("파")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[1].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[1] + "]");
                            myViewHolder.img1.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img1.setVisibility(View.VISIBLE);
                            myViewHolder.img1_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[2].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img2_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[2].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img2.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }

                }else if(list[2].equals("솔")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[1].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[1] + "]");
                            myViewHolder.img1.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img1.setVisibility(View.VISIBLE);
                            myViewHolder.img1_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[2].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img2_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);

                    }else{
                        // 박자가 2박이면
                        if (bit_list[2].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img2.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }

                }else if(list[2].equals("라")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[1].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[1] + "]");
                            myViewHolder.img1.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img1.setVisibility(View.VISIBLE);
                            myViewHolder.img1_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[2].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img2_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);

                    }else{
                        // 박자가 2박이면
                        if (bit_list[2].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img2.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }

                }else if(list[2].equals("시")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                        if (index == hight_pos){
                            // 음계가 " "빈칸일 때,
                            if(list[1].equals(" ")){
                                Log.e("음계 빈칸 1번째 배열값: ", "["+list[1] + "]");
                                myViewHolder.img1.setVisibility(View.INVISIBLE);
                            }else{
                                myViewHolder.img1.setVisibility(View.VISIBLE);
                                myViewHolder.img1_color.setVisibility(View.INVISIBLE);
                            }
                            // 박자가 2박이면
                            if (bit_list[2].equals("2")){
                                Log.e("Adapter", "박자는 2");
                                myViewHolder.img2_color.setImageResource(R.drawable.note2);
                            }
                            myViewHolder.img2_color.setLayoutParams(layoutParams);
                            myViewHolder.img2.setVisibility(View.INVISIBLE);
                            myViewHolder.img2_color.setVisibility(View.VISIBLE);
                        }else{
                            // 박자가 2박이면
                            if (bit_list[2].equals("2")){
                                Log.e("Adapter", "박자는 2");
                                myViewHolder.img2.setImageResource(R.drawable.note2);
                            }
                            myViewHolder.img2.setVisibility(View.VISIBLE);
                            myViewHolder.img2.setLayoutParams(layoutParams);
                        }

                }else if(list[2].equals("두")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view5);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[1].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[1] + "]");
                            myViewHolder.img1.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img1.setVisibility(View.VISIBLE);
                            myViewHolder.img1_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[2].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img2_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img2_color.setLayoutParams(layoutParams);
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[2].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img2.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img2.setVisibility(View.VISIBLE);
                        myViewHolder.img2.setLayoutParams(layoutParams);
                    }

                }else if(list[2].equals(" ")){
                    // index값과 hight_pos값이 같을 때
                    // 연주가 시작될 때,
                    if (index == hight_pos){
                        // 음계가 " "빈칸이 아닐 때,
                        if(!list[1].equals(" ")){
                            Log.e("음계 빈칸 2번째 배열값: ", "["+list[1] + "]");
                            myViewHolder.img1_color.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        myViewHolder.img2.setVisibility(View.INVISIBLE);
                        myViewHolder.img2_color.setVisibility(View.INVISIBLE);
                    }
                }

            }else if(i == 3){
                Log.e("배열 3번째: ", list[3]);
                layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));

                if(list[3].equals("도")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                    layoutParams.bottomMargin = (int)(7*dp);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[2].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[2] + "]");
                            myViewHolder.img2.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img2.setVisibility(View.VISIBLE);
                            myViewHolder.img2_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자에 따라 이미지 변환
                        if (bit_list[3].equals("1")){
                            myViewHolder.img3_color.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img3_color.setImageResource(R.drawable.note2_do);
                        }

                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자에 따라 이미지 변환
                        if (bit_list[3].equals("1")){
                            myViewHolder.img3.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img3.setImageResource(R.drawable.note2_do);
                        }

                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }

                }else if(list[3].equals("레")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[2].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[2] + "]");
                            myViewHolder.img2.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img2.setVisibility(View.VISIBLE);
                            myViewHolder.img2_color.setVisibility(View.INVISIBLE);
                        }

                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }

                }else if(list[3].equals("미")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[2].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[2] + "]");
                            myViewHolder.img2.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img2.setVisibility(View.VISIBLE);
                            myViewHolder.img2_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }



                }else if(list[3].equals("파")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[2].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[2] + "]");
                            myViewHolder.img2.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img2.setVisibility(View.VISIBLE);
                            myViewHolder.img2_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }

                }else if(list[3].equals("솔")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[2].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[2] + "]");
                            myViewHolder.img2.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img2.setVisibility(View.VISIBLE);
                            myViewHolder.img2_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }


                }else if(list[3].equals("라")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[2].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[2] + "]");
                            myViewHolder.img2.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img2.setVisibility(View.VISIBLE);
                            myViewHolder.img2_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }


                }else if(list[3].equals("시")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[2].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[2] + "]");
                            myViewHolder.img2.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img2.setVisibility(View.VISIBLE);
                            myViewHolder.img2_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }

                }else if(list[3].equals("두")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view5);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[2].equals(" ")){
                            Log.e("음계 빈칸 1번째 배열값: ", "["+list[2] + "]");
                            myViewHolder.img2.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img2.setVisibility(View.VISIBLE);
                            myViewHolder.img2_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img3_color.setLayoutParams(layoutParams);
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[3].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img3.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img3.setVisibility(View.VISIBLE);
                        myViewHolder.img3.setLayoutParams(layoutParams);
                    }
                }

                else if(list[3].equals(" ")){
                    // index값과 hight_pos값이 같을 때
                    // 연주가 시작될 때,
                    if (index == hight_pos){
                        // 음계가 " "빈칸이 아닐 때,
                        if(!list[2].equals(" ")){
                            Log.e("음계 빈칸 2번째 배열값: ", "["+list[2] + "]");
                            myViewHolder.img2_color.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        myViewHolder.img3.setVisibility(View.INVISIBLE);
                        myViewHolder.img3_color.setVisibility(View.INVISIBLE);
                    }
                }

//                if (index == hight_pos){
//                    myViewHolder.img3.setVisibility(View.INVISIBLE);
//                    myViewHolder.img3_color.setVisibility(View.VISIBLE);
//                }

                //myViewHolder.img3.setVisibility(View.VISIBLE);
                //myViewHolder.img3.setLayoutParams(layoutParams);
            }else if(i == 4){
                Log.e("배열 3번째: ", list[4]);
                // 이미지 크기 조절
                layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));

                if(list[4].equals("도")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[3].equals(" ")){
                            Log.e("음계 빈칸 3번째 배열값: ", "["+list[3] + "]");
                            myViewHolder.img3.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img3.setVisibility(View.VISIBLE);
                            myViewHolder.img3_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자에 따라 음표 이미지 변환
                        if (bit_list[4].equals("1")){
                            myViewHolder.img4_color.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img4_color.setImageResource(R.drawable.note2_do);
                        }

                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자에 따라 음표 이미지 변환
                        if (bit_list[4].equals("1")){
                            myViewHolder.img4.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img4.setImageResource(R.drawable.note2_do);
                        }

                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }


                }else if(list[4].equals("레")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[3].equals(" ")){
                            Log.e("음계 빈칸 3번째 배열값: ", "["+list[3] + "]");
                            myViewHolder.img3.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img3.setVisibility(View.VISIBLE);
                            myViewHolder.img3_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }


                }else if(list[4].equals("미")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[3].equals(" ")){
                            Log.e("음계 빈칸 3번째 배열값: ", "["+list[3] + "]");
                            myViewHolder.img3.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img3.setVisibility(View.VISIBLE);
                            myViewHolder.img3_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }


                }else if(list[4].equals("파")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[3].equals(" ")){
                            Log.e("음계 빈칸 3번째 배열값: ", "["+list[3] + "]");
                            myViewHolder.img3.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img3.setVisibility(View.VISIBLE);
                            myViewHolder.img3_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }


                }else if(list[4].equals("솔")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[3].equals(" ")){
                            Log.e("음계 빈칸 3번째 배열값: ", "["+list[3] + "]");
                            myViewHolder.img3.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img3.setVisibility(View.VISIBLE);
                            myViewHolder.img3_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }


                }else if(list[4].equals("라")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[3].equals(" ")){
                            Log.e("음계 빈칸 3번째 배열값: ", "["+list[3] + "]");
                            myViewHolder.img3.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img3.setVisibility(View.VISIBLE);
                            myViewHolder.img3_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }


                }else if(list[4].equals("시")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[3].equals(" ")){
                            Log.e("음계 빈칸 3번째 배열값: ", "["+list[3] + "]");
                            myViewHolder.img3.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img3.setVisibility(View.VISIBLE);
                            myViewHolder.img3_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }
                }else if(list[4].equals("두")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view5);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 음계가 " "빈칸일 때,
                        if(list[3].equals(" ")){
                            Log.e("음계 빈칸 3번째 배열값: ", "["+list[3] + "]");
                            myViewHolder.img3.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img3.setVisibility(View.VISIBLE);
                            myViewHolder.img3_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img4_color.setLayoutParams(layoutParams);
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[4].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img4.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img4.setVisibility(View.VISIBLE);
                        myViewHolder.img4.setLayoutParams(layoutParams);
                    }
                }

                else if(list[4].equals(" ")){
                    // index값과 hight_pos값이 같을 때
                    // 연주가 시작될 때,
                    if (index == hight_pos){
                        // 음계가 " "빈칸이 아닐 때,
                        if(!list[3].equals(" ")){
                            Log.e("음계 빈칸 2번째 배열값: ", "["+list[3] + "]");
                            myViewHolder.img3_color.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        myViewHolder.img4.setVisibility(View.INVISIBLE);
                        myViewHolder.img4_color.setVisibility(View.INVISIBLE);
                    }
                }


            }else if(i == 5){
                Log.e("배열 5번째: ", list[5]);
                layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));

                if(list[5].equals("도")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[4].equals(" ")){
                            Log.e("음계 빈칸 4번째 배열값: ", "["+list[4] + "]");
                            myViewHolder.img4.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img4.setVisibility(View.VISIBLE);
                            myViewHolder.img4_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자에 따라 음표 이미지 변환
                        if (bit_list[5].equals("1")){
                            myViewHolder.img5_color.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img5_color.setImageResource(R.drawable.note2_do);
                        }

                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자에 따라 음표 이미지 변환
                        if (bit_list[5].equals("1")){
                            myViewHolder.img5.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img5.setImageResource(R.drawable.note2_do);
                        }
                        myViewHolder.img5.setImageResource(R.drawable.do_icon);
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }


                }else if(list[5].equals("레")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[4].equals(" ")){
                            Log.e("음계 빈칸 4번째 배열값: ", "["+list[4] + "]");
                            myViewHolder.img4.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img4.setVisibility(View.VISIBLE);
                            myViewHolder.img4_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }


                }else if(list[5].equals("미")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[4].equals(" ")){
                            Log.e("음계 빈칸 4번째 배열값: ", "["+list[4] + "]");
                            myViewHolder.img4.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img4.setVisibility(View.VISIBLE);
                            myViewHolder.img4_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }

                }else if(list[5].equals("파")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[4].equals(" ")){
                            Log.e("음계 빈칸 4번째 배열값: ", "["+list[4] + "]");
                            myViewHolder.img4.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img4.setVisibility(View.VISIBLE);
                            myViewHolder.img4_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }


                }else if(list[5].equals("솔")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[4].equals(" ")){
                            Log.e("음계 빈칸 4번째 배열값: ", "["+list[4] + "]");
                            myViewHolder.img4.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img4.setVisibility(View.VISIBLE);
                            myViewHolder.img4_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }


                }else if(list[5].equals("라")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[4].equals(" ")){
                            Log.e("음계 빈칸 4번째 배열값: ", "["+list[4] + "]");
                            myViewHolder.img4.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img4.setVisibility(View.VISIBLE);
                            myViewHolder.img4_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }


                }else if(list[5].equals("시")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[4].equals(" ")){
                            Log.e("음계 빈칸 4번째 배열값: ", "["+list[4] + "]");
                            myViewHolder.img4.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img4.setVisibility(View.VISIBLE);
                            myViewHolder.img4_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }
                }else if(list[5].equals("두")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view5);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[4].equals(" ")){
                            Log.e("음계 빈칸 4번째 배열값: ", "["+list[4] + "]");
                            myViewHolder.img4.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img4.setVisibility(View.VISIBLE);
                            myViewHolder.img4_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img5_color.setLayoutParams(layoutParams);
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[5].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img5.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img5.setVisibility(View.VISIBLE);
                        myViewHolder.img5.setLayoutParams(layoutParams);
                    }
                }else if(list[5].equals(" ")){
                    // index값과 hight_pos값이 같을 때
                    // 연주가 시작될 때,
                    if (index == hight_pos){
                        // 음계가 " "빈칸이 아닐 때,
                        if(!list[4].equals(" ")){
                            Log.e("음계 빈칸 4번째 배열값: ", "["+list[4] + "]");
                            myViewHolder.img4_color.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        myViewHolder.img5.setVisibility(View.INVISIBLE);
                        myViewHolder.img5_color.setVisibility(View.INVISIBLE);
                    }
                }

            }else if(i == 6){
                Log.e("배열 6번째: ", list[6]);
                layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));

                if(list[6].equals("도")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[5].equals(" ")){
                            Log.e("음계 빈칸 5번째 배열값: ", "["+list[5] + "]");
                            myViewHolder.img5.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img5.setVisibility(View.VISIBLE);
                            myViewHolder.img5_color.setVisibility(View.INVISIBLE);
                        }

                        // 박자에 따라 음표 이미지 변환
                        if (bit_list[6].equals("1")){
                            myViewHolder.img6_color.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img6_color.setImageResource(R.drawable.note2_do);
                        }

                        myViewHolder.img6_color.setImageResource(R.drawable.do_icon);
                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자에 따라 음표 이미지 변환
                        if (bit_list[6].equals("1")){
                            myViewHolder.img6.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img6.setImageResource(R.drawable.note2_do);
                        }
                        myViewHolder.img6.setImageResource(R.drawable.do_icon);
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }

                }else if(list[6].equals("레")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[5].equals(" ")){
                            Log.e("음계 빈칸 5번째 배열값: ", "["+list[5] + "]");
                            myViewHolder.img5.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img5.setVisibility(View.VISIBLE);
                            myViewHolder.img5_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }


                }else if(list[6].equals("미")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);
                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[5].equals(" ")){
                            Log.e("음계 빈칸 5번째 배열값: ", "["+list[5] + "]");
                            myViewHolder.img5.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img5.setVisibility(View.VISIBLE);
                            myViewHolder.img5_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }


                }else if(list[6].equals("파")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[5].equals(" ")){
                            Log.e("음계 빈칸 5번째 배열값: ", "["+list[5] + "]");
                            myViewHolder.img5.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img5.setVisibility(View.VISIBLE);
                            myViewHolder.img5_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);

                    }else{
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }


                }else if(list[6].equals("솔")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[5].equals(" ")){
                            Log.e("음계 빈칸 5번째 배열값: ", "["+list[5] + "]");
                            myViewHolder.img5.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img5.setVisibility(View.VISIBLE);
                            myViewHolder.img5_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }


                }else if(list[6].equals("라")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[5].equals(" ")){
                            Log.e("음계 빈칸 5번째 배열값: ", "["+list[5] + "]");
                            myViewHolder.img5.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img5.setVisibility(View.VISIBLE);
                            myViewHolder.img5_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }


                }else if(list[6].equals("시")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[5].equals(" ")){
                            Log.e("음계 빈칸 5번째 배열값: ", "["+list[5] + "]");
                            myViewHolder.img5.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img5.setVisibility(View.VISIBLE);
                            myViewHolder.img5_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }
                }else if(list[6].equals("두")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view5);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[5].equals(" ")){
                            Log.e("음계 빈칸 5번째 배열값: ", "["+list[5] + "]");
                            myViewHolder.img5.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img5.setVisibility(View.VISIBLE);
                            myViewHolder.img5_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img6_color.setLayoutParams(layoutParams);
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[6].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img6.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img6.setVisibility(View.VISIBLE);
                        myViewHolder.img6.setLayoutParams(layoutParams);
                    }
                }else if(list[6].equals(" ")){
                    // index값과 hight_pos값이 같을 때
                    // 연주가 시작될 때,
                    if (index == hight_pos){
                        // 음계가 " "빈칸이 아닐 때,
                        if(!list[5].equals(" ")){
                            Log.e("음계 빈칸 5번째 배열값: ", "["+list[5] + "]");
                            myViewHolder.img5_color.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        myViewHolder.img6.setVisibility(View.INVISIBLE);
                        myViewHolder.img6_color.setVisibility(View.INVISIBLE);
                    }
                }

            }else if(i == 7){
                Log.e("배열 7번째: ", list[7]);
                layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));

                if(list[7].equals("도")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[6].equals(" ")){
                            Log.e("음계 빈칸 6번째 배열값: ", "["+list[6] + "]");
                            myViewHolder.img6.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img6.setVisibility(View.VISIBLE);
                            myViewHolder.img6_color.setVisibility(View.INVISIBLE);
                        }

                        // 박자에 따라 음표 이미지 변환
                        if (bit_list[7].equals("1")){
                            myViewHolder.img7_color.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img7_color.setImageResource(R.drawable.note2_do);
                        }

                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자에 따라 음표 이미지 변환
                        if (bit_list[7].equals("1")){
                            myViewHolder.img7.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img7.setImageResource(R.drawable.note2_do);
                        }

                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }


                }else if(list[7].equals("레")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[6].equals(" ")){
                            Log.e("음계 빈칸 6번째 배열값: ", "["+list[6] + "]");
                            myViewHolder.img6.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img6.setVisibility(View.VISIBLE);
                            myViewHolder.img6_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }


                }else if(list[7].equals("미")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[6].equals(" ")){
                            Log.e("음계 빈칸 6번째 배열값: ", "["+list[6] + "]");
                            myViewHolder.img6.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img6.setVisibility(View.VISIBLE);
                            myViewHolder.img6_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }


                }else if(list[7].equals("파")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[6].equals(" ")){
                            Log.e("음계 빈칸 6번째 배열값: ", "["+list[6] + "]");
                            myViewHolder.img6.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img6.setVisibility(View.VISIBLE);
                            myViewHolder.img6_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }


                }else if(list[7].equals("솔")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[6].equals(" ")){
                            Log.e("음계 빈칸 6번째 배열값: ", "["+list[6] + "]");
                            myViewHolder.img6.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img6.setVisibility(View.VISIBLE);
                            myViewHolder.img6_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7_color.setImageResource(R.drawable.note2);
                        }
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7_color.setImageResource(R.drawable.note2);
                        }
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{

                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }


                }else if(list[7].equals("라")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[6].equals(" ")){
                            Log.e("음계 빈칸 6번째 배열값: ", "["+list[6] + "]");
                            myViewHolder.img6.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img6.setVisibility(View.VISIBLE);
                            myViewHolder.img6_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }


                }else if(list[7].equals("시")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                    layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[6].equals(" ")){
                            Log.e("음계 빈칸 6번째 배열값: ", "["+list[6] + "]");
                            myViewHolder.img6.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img6.setVisibility(View.VISIBLE);
                            myViewHolder.img6_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }
                }else if(list[7].equals("두")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view5);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                    layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[6].equals(" ")){
                            Log.e("음계 빈칸 6번째 배열값: ", "["+list[6] + "]");
                            myViewHolder.img6.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img6.setVisibility(View.VISIBLE);
                            myViewHolder.img6_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img7_color.setLayoutParams(layoutParams);
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[7].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img7.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img7.setVisibility(View.VISIBLE);
                        myViewHolder.img7.setLayoutParams(layoutParams);
                    }
                }else if(list[7].equals(" ")){
                    // index값과 hight_pos값이 같을 때
                    // 연주가 시작될 때,
                    if (index == hight_pos){
                        // 음계가 " "빈칸이 아닐 때,
                        if(!list[6].equals(" ")){
                            Log.e("음계 빈칸 6번째 배열값: ", "["+list[6] + "]");
                            myViewHolder.img6_color.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        myViewHolder.img7.setVisibility(View.INVISIBLE);
                        myViewHolder.img7_color.setVisibility(View.INVISIBLE);
                    }
                }

            }else if(i == 8){
                Log.e("배열 8번째: ", list[8]);
                // 이미지 크기 조절
                layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));

                if(list[8].equals("도")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                    //layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[7].equals(" ")){
                            Log.e("음계 빈칸 7번째 배열값: ", "["+list[7] + "]");
                            myViewHolder.img7.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img7.setVisibility(View.VISIBLE);
                            myViewHolder.img7_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자에 따라 음표 이미지 변환
                        if (bit_list[8].equals("1")){
                            myViewHolder.img8_color.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img8_color.setImageResource(R.drawable.note2_do);
                        }

                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자에 따라 음표 이미지 변환
                        if (bit_list[8].equals("1")){
                            myViewHolder.img8_color.setImageResource(R.drawable.do_icon);
                        }else {
                            myViewHolder.img8_color.setImageResource(R.drawable.note2_do);
                        }

                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }



                }else if(list[8].equals("레")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                    //layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[7].equals(" ")){
                            Log.e("음계 빈칸 7번째 배열값: ", "["+list[7] + "]");
                            myViewHolder.img7.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img7.setVisibility(View.VISIBLE);
                            myViewHolder.img7_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }


                }else if(list[8].equals("미")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                    //layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[7].equals(" ")){
                            Log.e("음계 빈칸 7번째 배열값: ", "["+list[7] + "]");
                            myViewHolder.img7.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img7.setVisibility(View.VISIBLE);
                            myViewHolder.img7_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }



                }else if(list[8].equals("파")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                    //layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[7].equals(" ")){
                            Log.e("음계 빈칸 7번째 배열값: ", "["+list[7] + "]");
                            myViewHolder.img7.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img7.setVisibility(View.VISIBLE);
                            myViewHolder.img7_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8_color.setImageResource(R.drawable.note2);
                        }

                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }


                }else if(list[8].equals("솔")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                    //layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[7].equals(" ")){
                            Log.e("음계 빈칸 7번째 배열값: ", "["+list[7] + "]");
                            myViewHolder.img7.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img7.setVisibility(View.VISIBLE);
                            myViewHolder.img7_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }


                }else if(list[8].equals("라")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                    //layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[7].equals(" ")){
                            Log.e("음계 빈칸 7번째 배열값: ", "["+list[7] + "]");
                            myViewHolder.img7.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img7.setVisibility(View.VISIBLE);
                            myViewHolder.img7_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }


                }else if(list[8].equals("시")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                    //layoutParams.rightMargin = (int)(22*dp);
                    layoutParams.bottomMargin = (int)(7*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[7].equals(" ")){
                            Log.e("음계 빈칸 7번째 배열값: ", "["+list[7] + "]");
                            myViewHolder.img7.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img7.setVisibility(View.VISIBLE);
                            myViewHolder.img7_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }
                }else if(list[8].equals("두")){
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.view5);
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                    //layoutParams.rightMargin = (int)(22*dp);

                    if (index == hight_pos){
                        // 전 음계가 " "빈칸일 때,
                        if(list[7].equals(" ")){
                            Log.e("음계 빈칸 7번째 배열값: ", "["+list[7] + "]");
                            myViewHolder.img7.setVisibility(View.INVISIBLE);
                        }else{
                            myViewHolder.img7.setVisibility(View.VISIBLE);
                            myViewHolder.img7_color.setVisibility(View.INVISIBLE);
                        }
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8_color.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img8_color.setLayoutParams(layoutParams);
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.VISIBLE);
                    }else{
                        // 박자가 2박이면
                        if (bit_list[8].equals("2")){
                            Log.e("Adapter", "박자는 2");
                            myViewHolder.img8.setImageResource(R.drawable.note2);
                        }
                        myViewHolder.img8.setVisibility(View.VISIBLE);
                        myViewHolder.img8.setLayoutParams(layoutParams);
                    }
                }else if(list[8].equals(" ")){
                    // index값과 hight_pos값이 같을 때
                    // 연주가 시작될 때,
                    if (index == hight_pos){
                        // 음계가 " "빈칸이 아닐 때,
                        if(!list[7].equals(" ")){
                            Log.e("음계 빈칸 7번째 배열값: ", "["+list[7] + "]");
                            myViewHolder.img7_color.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        myViewHolder.img8.setVisibility(View.INVISIBLE);
                        myViewHolder.img8_color.setVisibility(View.INVISIBLE);
                    }
                }

            }

            // 마지막 음표 색 없앰
//            if (hight_pos == musicnote.length() + 1){
//                myViewHolder.img1_color.setVisibility(View.INVISIBLE);
//                myViewHolder.img1.setVisibility(View.VISIBLE);
//            }


        }

    }

    @Override
    public int getItemCount() {
        return musicNoteList.size();
    }
}
