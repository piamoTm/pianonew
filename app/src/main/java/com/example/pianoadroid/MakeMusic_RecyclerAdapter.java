package com.example.pianoadroid;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import java.util.ArrayList;

//작곡 MakeMusic 액티비티에서 사용되는 리사이클러뷰 어댑터
//아두이노로부터 블루투스 통신으로 'C' 라는 데이터가 오면 악보에 도를 그리는 역할
/*
****작곡완료 된 곡을 재생시에
* 원곡재생, 연습모드를 위해 악보를 그리는 리사이클러뷰
* 음악 클래스를 받고 String score를 getScore()로 받아서.
* 글자씩 잘라서 아이템 생성하고
* 아이템 하나에 한글자씩 위치를 지정해주면 됨.
* */
public class MakeMusic_RecyclerAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<String> listData ;  //작곡된 노래 전체 list
    Music music;   //노래
    int highlightPos;  //하이라이트 포지션
    int adparter_size; //아이템의 개수

    public MakeMusic_RecyclerAdapter(ArrayList<String> listData) {
        this.listData = listData;
    }
    public MakeMusic_RecyclerAdapter(Music music,int highlightPos){
        this.music = music; this.highlightPos =highlightPos;
    }

    public void setHighlightPos(int highlightPos) {
        this.highlightPos = highlightPos;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleritem_makenote, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        int width = 35;//음표의 크기였음
        int space = 7;
        Log.i("listSize 확인확인222" ,"번호:"+adparter_size);
        Log.i("testLog", "악보 확인 확인 확인 " );

        //i는 아이템 악보 한줄,두줄 이거
        int startNote = i*8;// 0,8,16,24
        int endNote = startNote + 8; //


       // Log.i("testLog", "악보 확인 확인 확인 " + score);
        String scorePiece = ""; //0~7 /8~15로 악보자르기

        if(music != null) {
            String score = music.getScore();
            // 작곡된 곡 play시 하이라이트 적용
            if(endNote >= music.getScoreLen()){
                endNote = music.getScoreLen();
            }
              scorePiece = score.substring(startNote, endNote); //0~7 /8~15로 악보자르기
        }else{
            //작곡
            if (endNote >= listData.size()) {
                endNote = listData.size();
            }
            for (int j = startNote; j<endNote; j++){
                scorePiece += listData.get(j);
            }

            highlightPos = -11;// 작곡시 하이라이트 처리가 안되게
        }


        Log.i("testLog", "악보 조각 " + startNote + scorePiece + endNote);

        //악보 조각(한줄)을 계이름 하나씩 자르기
        String[] split = scorePiece.split("");

        // 리사이클러뷰 남아있는 view 찌꺼기들 제거
        for (ImageView iv: ((ItemViewHolder)holder).imageViews) {
            iv.setVisibility(View.INVISIBLE);
            iv.setImageResource(R.drawable.music_icon);
        }

        //악보조각(한줄) 에 있는 계이름 수만큼 반복문을 돌림.
        for (int ni = 1; ni < split.length; ni++){
            int n = ni-1;
            // 코드에 따라 음표의 위치를 조절
            Log.i("testLog", n+"번째 계이름 " + split[ni]);
            if(!split[ni].equals(" ")){ //공란이 아닐때 == 도~시일
//                ((ItemViewHolder)holder).imageViews[n].setImageResource(R.drawable.music_icon);
                if(split[ni].equals("C")){
                    // 이미지 크기 조절
                    ((ItemViewHolder)holder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)holder).dp), (int)(width*((ItemViewHolder)holder).dp));
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)holder).imageViewID[n]);
                    ((ItemViewHolder)holder).layoutParams.bottomMargin = (int)(space * ((ItemViewHolder)holder).dp);
                    ((ItemViewHolder)holder).imageViews[n].setImageResource(R.drawable.do_icon);

                }else if(split[ni].equals("D")){
                    ((ItemViewHolder)holder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)holder).dp), (int)(width*((ItemViewHolder)holder).dp));
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)holder).imageViewID[n]);
                    //((ItemViewHolder)holder).imageViews[n].setImageResource(0);

                }else if(split[ni].equals("E")){
                    ((ItemViewHolder)holder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)holder).dp), (int)(width*((ItemViewHolder)holder).dp));
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)holder).imageViewID[n]);
                    ((ItemViewHolder)holder).layoutParams.bottomMargin = (int)(space * ((ItemViewHolder)holder).dp);

                }else if(split[ni].equals("F")){
                    ((ItemViewHolder)holder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)holder).dp), (int)(width*((ItemViewHolder)holder).dp));
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)holder).imageViewID[n]);

                }else if(split[ni].equals("G")){
                    ((ItemViewHolder)holder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)holder).dp), (int)(width*((ItemViewHolder)holder).dp));
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)holder).imageViewID[n]);
                    ((ItemViewHolder)holder).layoutParams.bottomMargin = (int)(space * ((ItemViewHolder)holder).dp);

                }else if(split[ni].equals("A")){
                    ((ItemViewHolder)holder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)holder).dp), (int)(width*((ItemViewHolder)holder).dp));
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)holder).imageViewID[n]);

                }else if(split[ni].equals("B")){
                    ((ItemViewHolder)holder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)holder).dp), (int)(width*((ItemViewHolder)holder).dp));
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    ((ItemViewHolder)holder).layoutParams.bottomMargin = (int)(space * ((ItemViewHolder)holder).dp);
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)holder).imageViewID[n]);
                }
                else if(split[ni].equals("H")){
                    ((ItemViewHolder)holder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)holder).dp), (int)(width*((ItemViewHolder)holder).dp));
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view5);
                    ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)holder).imageViewID[n]);
                }
                ((ItemViewHolder)holder).imageViews[n].setVisibility(View.VISIBLE);
                ((ItemViewHolder)holder).imageViews[n].setLayoutParams(((ItemViewHolder)holder).layoutParams);
            }
            int position = n+(i*8);
            if(highlightPos != -11){
                if((position == highlightPos) && (highlightPos != -11) ){
                    //argb 투명색까지 포함   , rgb는 그냥  색상만  //헥사 코드로 넣을것
                    Log.i("MakeMusic:  ","하이라이트 포지션 값 :    "+highlightPos);
                    ((ItemViewHolder)holder).imageViews[n].setBackgroundColor(Color.argb(0xA0,0xeb,0xbc,0xbb));
                }
            }
        }
    }

    @Override
    public int getItemCount(){
        //아이템의 개수
        //오선지 한줄의 개수
        //8개로 잘라서 나머지 있으면 +1
        if( music == null){
            adparter_size  = listData.size();
        }else{
            adparter_size =  music.getScoreLen();
        }
        Log.i("listSize 확인확인 adpater" ,"번호:"+adparter_size);

        int len = adparter_size;
        int cnt = len/8;
        len -= (cnt*8);
        if(len > 0){
            cnt++;
        }
        return cnt;
    }

//    void addItem(Write data) {
//        // 외부에서 item을 추가시킬 함수입니다.
//        // 오선지를 추가하는 함수
//        listData.add(data);
//    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {
        //음표png를 넣을 이미지뷰 8개
        ImageView[] imageViews;
        int[] imageViewID ;

        //레이아웃 설정용 변수
        RelativeLayout relative;
        RelativeLayout.LayoutParams layoutParams;
        float dp;

       public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViews  = new ImageView[8];
            imageViewID = new int[8]; //이미지뷰 아이디 8개

            imageViews[0] = itemView.findViewById(R.id.img1);
            imageViews[1] = itemView.findViewById(R.id.img2);
            imageViews[2] = itemView.findViewById(R.id.img3);
            imageViews[3] = itemView.findViewById(R.id.img4);
            imageViews[4] = itemView.findViewById(R.id.img5);
            imageViews[5] = itemView.findViewById(R.id.img6);
            imageViews[6] = itemView.findViewById(R.id.img7);
            imageViews[7] = itemView.findViewById(R.id.img8);

            imageViewID[0] = R.id.img0;
            imageViewID[1] = R.id.img1;
            imageViewID[2] = R.id.img2;
            imageViewID[3] = R.id.img3;
            imageViewID[4] = R.id.img4;
            imageViewID[5] = R.id.img5;
            imageViewID[6] = R.id.img6;
            imageViewID[7] = R.id.img7;

            relative = itemView.findViewById(R.id.relative_1);
            // 이미지뷰의 레이아웃을 가로 35dp, 세로 35dp로 지정
            dp = itemView.getResources().getDisplayMetrics().density;
            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));

        }

    }

}
