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

//작곡 위해 악보를 그리는 리사이클러뷰
public class testAdapter3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<String> noteArr;

    public testAdapter3(ArrayList<String> noteArr) {
        this.noteArr = noteArr;
    }

    public void setNoteArr(ArrayList<String> noteArr) {
        this.noteArr = noteArr;
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
        int startNote = i*8;
        int endNote = startNote + 8;
        if(endNote >= noteArr.size()){
            endNote = noteArr.size();
        }
        String scorePiece = ""; //0~7 /8~15로 악보자르기
        for (int j = startNote; j<endNote; j++){
            scorePiece += noteArr.get(j);
        }
        Log.i("testLog", "악보 조각 " + startNote + scorePiece + endNote);

        //악보 조각(한줄)을 계이름 하나씩 자르기
        String[] split = scorePiece.split("");

        // 리사이클러뷰 남아있는 view들 제거
        for (ImageView iv: ((ItemViewHolder)viewHolder).imageViews
             ) {
            iv.setVisibility(View.INVISIBLE);
        }

        //악보조각(한줄) 에 있는 계이름 수만큼 반복문을 돌림.
        for (int ni = 1; ni < split.length; ni++){
            int n = ni-1;
            // 코드에 따라 음표의 위치를 조절
            Log.i("testLog", n+"번째 계이름 " + split[ni]);
            if(!split[ni].equals(" ")){ //공란이 아닐때 == 도~시일
                if(split[ni].equals("C")){
                    // 이미지 크기 조절
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);
                    ((ItemViewHolder)viewHolder).layoutParams.bottomMargin = (int)(space * ((ItemViewHolder)viewHolder).dp);
                    ((ItemViewHolder)viewHolder).imageViews[n].setImageResource(R.drawable.do_icon);

                }else if(split[ni].equals("D")){
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);

                }else if(split[ni].equals("E")){
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);
                    ((ItemViewHolder)viewHolder).layoutParams.bottomMargin = (int)(space * ((ItemViewHolder)viewHolder).dp);

                }else if(split[ni].equals("F")){
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);

                }else if(split[ni].equals("G")){
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);
                    ((ItemViewHolder)viewHolder).layoutParams.bottomMargin = (int)(space * ((ItemViewHolder)viewHolder).dp);

                }else if(split[ni].equals("A")){
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);

                }else if(split[ni].equals("B")){
                    ((ItemViewHolder)viewHolder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)viewHolder).dp), (int)(width*((ItemViewHolder)viewHolder).dp));
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    ((ItemViewHolder)viewHolder).layoutParams.bottomMargin = (int)(space * ((ItemViewHolder)viewHolder).dp);
                    ((ItemViewHolder)viewHolder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)viewHolder).imageViewID[n]);
                }
                ((ItemViewHolder)viewHolder).imageViews[n].setVisibility(View.VISIBLE);
                ((ItemViewHolder)viewHolder).imageViews[n].setLayoutParams(((ItemViewHolder)viewHolder).layoutParams);
            }
            int position = n+(i*8);
        }
    }

    @Override
    public int getItemCount() {
        //아이템의 개수
        //오선지 한줄의 개수
        //8개로 잘라서 나머지 있으면 +1
        int len = noteArr.size();
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
