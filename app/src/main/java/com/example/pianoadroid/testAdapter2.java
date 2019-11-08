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

//원곡재생, 연습모드를 위해 악보를 그리는 리사이클러뷰
//음악 클래스를 받고 String score를 getScore()로 받아서.
//글자씩 잘라서 아이템 생성하고
//아이템 하나에 한글자씩 위치를 지정해주면 됨.
public class testAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Music music;
    int highlightPos;

    public testAdapter2(Music music,int highlightPos) {
        this.music = music; this.highlightPos =highlightPos;
    }

    public void setHighlightPos(int highlightPos) {
        this.highlightPos = highlightPos;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
    public void addNote(String note){
        this.music.addNote(note);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleritem_makenote, viewGroup,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int width = 35;
        int space = 7;

        //i는 아이템 악보 한줄,두줄 이거
        String score = music.getScore();
        int startNote = i*8;
        int endNote = startNote+7;
        if(endNote >= music.getScoreLen()){
            endNote = music.getScoreLen()-1;
        }
        String scorePiece = score.substring(startNote, endNote); //0~7 /8~15로 악보자르기
        Log.i("testLog", "악보조각 " + startNote+scorePiece+endNote);

        //악보조각(한줄)을 계이름 하나씩 자르기
        String[] split = scorePiece.split("");

        //악보조각(한줄) 에 있는 계이름 수만큼 반복문을 돌림.
        for (int n = 0; n< split.length; n++){
            // 코드에 따라 음표의 위치를 조절
            Log.i("testLog", i+"번째 계이름 " + split[n]);
            if(!split[n].equals(" ")){ //공란이 아닐때 == 도~시일
                if(split[n].equals("C")){
                    // 이미지 크기 조절
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);
                    ((ItemViewHolder)viewHolder).layoutParams.bottomMargin = (int)(space * ((ItemViewHolder)viewHolder).dp);
                    ((ItemViewHolder)viewHolder).imageViews[n].setImageResource(R.drawable.do_icon);

                }else if(split[n].equals("D")){
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);

                }else if(split[n].equals("E")){
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);
                    ((ItemViewHolder)viewHolder).layoutParams.bottomMargin = (int)(space * ((ItemViewHolder)viewHolder).dp);

                }else if(split[n].equals("F")){
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);

                }else if(split[n].equals("G")){
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);
                    ((ItemViewHolder)viewHolder).layoutParams.bottomMargin = (int)(space * ((ItemViewHolder)viewHolder).dp);

                }else if(split[n].equals("A")){
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);

                }else if(split[n].equals("B")){
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    ((ItemViewHolder)viewHolder).layoutParams.bottomMargin = (int)(space * ((ItemViewHolder)viewHolder).dp);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);
                }
                ((ItemViewHolder)viewHolder).imageViews[n].setVisibility(View.VISIBLE);
                ((ItemViewHolder)viewHolder).imageViews[n].setLayoutParams(((ItemViewHolder)viewHolder).layoutParams);
            }
            int position = n+(i*8);
            if(highlightPos != -11){
                if((position == highlightPos) && (highlightPos != -11) ){
                    ((ItemViewHolder)viewHolder).imageViews[n].setBackgroundColor(Color.RED);
                }
            }

        }


    }

    @Override
    public int getItemCount() {
        //아이템의 개수
        //오선지 한줄의 개수
        //8개로 잘라서 나머지 있으면 +1
        int len = music.getScoreLen();
        int cnt = len/8;
        len -= (cnt*8);
        if(len > 0){
            cnt++;
        }
        return cnt;
    }


    private static class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView[] imageViews;
        int[] imageViewID;

        //레이아웃 설정용 변수
        RelativeLayout relative;
        RelativeLayout.LayoutParams layoutParams;
        float dp;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViews = new ImageView[8];
            imageViewID = new int[8];

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

            // 이미지뷰의 레이아웃을 가로 35dp, 세로 35dp로 지정
            relative = itemView.findViewById(R.id.relative_1);
            dp = itemView.getResources().getDisplayMetrics().density;
            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));

        }
    }
}
