package com.example.pianoadroid;


import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
    private ArrayList<String> listData ;  // 계이름 list
    private ArrayList<Integer> beatData ;  //박자 list
    private int adapter_size; // ArrayList 아이템 개수
    private Music music;   //노래
    private int highlightPos;  //하이라이트 포지션

    //작곡할때 리사이클러뷰 데이터 가져오기
    public MakeMusic_RecyclerAdapter(ArrayList<String> listData, ArrayList<Integer> beetData,int highlightPos) {
        this.beatData = beetData;
        this.listData = listData;
        this.highlightPos = highlightPos;
    }

    // 작곡한 저장곡 연주하기
    public MakeMusic_RecyclerAdapter(Music music,int highlightPos) {
        this.music = music; this.highlightPos =highlightPos;
    }


    public void setHighlightPos(int highlightPos) {
        Log.i("testLog", "setHighlightPos "+ highlightPos);
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



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        int width = 35;//음표의 크기였음
        int space = 7;
        int rspace = 30;  //음표 right 마진 고정값

        //i는 아이템 악보 한줄,두줄 이거
        int startNote = i*8;// 0,8,16,24
        int endNote = startNote + 8; //

        String scorePiece = ""; //0~7 /8~15로 악보자르기
        String beatPiece = ""; //박자도 자르기

        if(music != null){
            //작곡된 곡 play시 하이라이트 적용
            if (endNote >= music.getScoreLen()) {
                endNote = music.getScoreLen();
            }
            scorePiece = music.getScore().substring(startNote, endNote);// 0~7 /8~15 로 악보자르기

        } else{
            // 작곡중

            if (endNote >= listData.size()) {
                endNote = listData.size();
            }
            for (int j = startNote; j<endNote; j++){
                scorePiece += listData.get(j);
                //Log.i("testLog", "listData.get("+j+") " + listData.get(j)  );
                beatPiece += beatData.get(j); //박자
            }
        }
        Log.i("testLog", "" + startNote + "~" + endNote + "의 악보조각 : "+scorePiece);

        //악보 조각(한줄)을 계이름 하나씩 자르기
        String[] split = scorePiece.split("");
        String[] bbsplit = beatPiece.split("");
        // 비트도 index 별로 자르기

        Log.i("testLog", "beatpiece 비트 조각덜: "+beatPiece);
        int[] bsplit =  new int[bbsplit.length];

        // 스트링 비트 배열을 int 배열로 바꿔주기
        for(int p=1; p< bbsplit.length; p++){
                bsplit[p] = Integer.parseInt(bbsplit[p]);
            //Log.i("testLog", "beatpiece 비트 조각덜int형 : "+bsplit[p]);
        }

        //bsplit 배열에 temp(String)을 하나하나 잘라서 넣어주기
       // for(int p =0;p<beatPiece.length();i++) bsplit[i] = beatPiece.charAt(i);

//        Log.i("testLog", "scorePiece을 자른 split");
//        for (int h = 0; h < split.length; h++){
//            Log.i("testLog", "split["+h+"] "+split[h] );
////        }

//        Log.i("testLog", "Beat 확인 ");  //yyj
//        for (int h = 0; h < beatData.size(); h++){//yyj
//            Log.i("testLog", "Beet["+h+"] "+beatData.get(h) );//yyj
//        }

        // 리사이클러뷰 남아있는 view 찌꺼기들 제거
        for (ImageView iv: ((ItemViewHolder)holder).imageViews) {
            iv.setVisibility(View.INVISIBLE);
            iv.setImageResource(R.drawable.music_icon);
        }

        Log.i("testLog", "/"+split.length+"/");
        //악보조각(한줄) 에 있는 계이름 수만큼 반복문을 돌림.
        for (int ni = 1; ni < split.length; ni++){
            int n = ni-1;
            // 코드에 따라 음표의 위치를 조절
            Log.i("testLog", n+"번째 계이름 " + split[ni]);

            //박자별 이미지 배열 저장
            int[]  notes  = {R.drawable.note1,R.drawable.note2,R.drawable.note3,R.drawable.note4};
            int[]  notes_do  ={R.drawable.note1_do,R.drawable.note2_do,R.drawable.note3_do,R.drawable.note4_do};

            if(!split[ni].equals(" ")) { //공란이 아닐때 == 도~시일
//                ((ItemViewHolder)holder).imageViews[n].setImageResource(R.drawable.music_icon);
                if (split[ni].equals("C")) {
                    // 이미지 크기 조절
                    ((ItemViewHolder) holder).layoutParams = new RelativeLayout.LayoutParams((int) (width * ((ItemViewHolder) holder).dp), (int) (width * ((ItemViewHolder) holder).dp));
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder) holder).imageViewID[n]);
                    ((ItemViewHolder) holder).layoutParams.bottomMargin = (int) (space * ((ItemViewHolder) holder).dp);
                    ((ItemViewHolder) holder).layoutParams.rightMargin = (int) (rspace * ((ItemViewHolder) holder).dp);
                    ((ItemViewHolder) holder).imageViews[n].setImageResource(notes_do[bsplit[ni]-1]);

                } else if (split[ni].equals("D")) {
                    ((ItemViewHolder) holder).layoutParams = new RelativeLayout.LayoutParams((int) (width * ((ItemViewHolder) holder).dp), (int) (width * ((ItemViewHolder) holder).dp));
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder) holder).imageViewID[n]);
                    ((ItemViewHolder) holder).layoutParams.rightMargin = (int) (rspace * ((ItemViewHolder) holder).dp);
                    ((ItemViewHolder) holder).imageViews[n].setImageResource(notes[bsplit[ni]-1]);
                } else if (split[ni].equals("E")) {
                    ((ItemViewHolder) holder).layoutParams = new RelativeLayout.LayoutParams((int) (width * ((ItemViewHolder) holder).dp), (int) (width * ((ItemViewHolder) holder).dp));
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder) holder).imageViewID[n]);
                    ((ItemViewHolder) holder).layoutParams.bottomMargin = (int) (space+3 * ((ItemViewHolder) holder).dp);
                    ((ItemViewHolder) holder).layoutParams.rightMargin = (int) (rspace * ((ItemViewHolder) holder).dp);
                    ((ItemViewHolder) holder).imageViews[n].setImageResource(notes[bsplit[ni]-1]);
                } else if (split[ni].equals("F")) {
                    ((ItemViewHolder) holder).layoutParams = new RelativeLayout.LayoutParams((int) (width * ((ItemViewHolder) holder).dp), (int) (width * ((ItemViewHolder) holder).dp));
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder) holder).imageViewID[n]);
                    ((ItemViewHolder) holder).layoutParams.rightMargin = (int) (rspace * ((ItemViewHolder) holder).dp);
                    ((ItemViewHolder) holder).imageViews[n].setImageResource(notes[bsplit[ni]-1]);
                } else if (split[ni].equals("G")) {
                    ((ItemViewHolder) holder).layoutParams = new RelativeLayout.LayoutParams((int) (width * ((ItemViewHolder) holder).dp), (int) (width * ((ItemViewHolder) holder).dp));
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder) holder).imageViewID[n]);
                    ((ItemViewHolder) holder).layoutParams.bottomMargin = (int) (space+3 * ((ItemViewHolder) holder).dp);
                    ((ItemViewHolder) holder).layoutParams.rightMargin = (int) (rspace * ((ItemViewHolder) holder).dp);
                    ((ItemViewHolder) holder).imageViews[n].setImageResource(notes[bsplit[ni]-1]);
                } else if (split[ni].equals("A")) {
                    ((ItemViewHolder) holder).layoutParams = new RelativeLayout.LayoutParams((int) (width * ((ItemViewHolder) holder).dp), (int) (width * ((ItemViewHolder) holder).dp));
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder) holder).imageViewID[n]);
                    ((ItemViewHolder) holder).layoutParams.rightMargin = (int) (rspace * ((ItemViewHolder) holder).dp);
                    ((ItemViewHolder) holder).imageViews[n].setImageResource(notes[bsplit[ni]-1]);
                } else if (split[ni].equals("B")) {
                    ((ItemViewHolder) holder).layoutParams = new RelativeLayout.LayoutParams((int) (width * ((ItemViewHolder) holder).dp), (int) (width * ((ItemViewHolder) holder).dp));
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                    ((ItemViewHolder) holder).layoutParams.bottomMargin = (int) (space+3 * ((ItemViewHolder) holder).dp);
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder) holder).imageViewID[n]);
                    ((ItemViewHolder) holder).layoutParams.rightMargin = (int) (rspace * ((ItemViewHolder) holder).dp);
                    ((ItemViewHolder) holder).imageViews[n].setImageResource(notes[bsplit[ni]-1]);
                } else if (split[ni].equals("H")) {
                    ((ItemViewHolder) holder).layoutParams = new RelativeLayout.LayoutParams((int) (width * ((ItemViewHolder) holder).dp), (int) (width * ((ItemViewHolder) holder).dp));
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view5);
                    ((ItemViewHolder) holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder) holder).imageViewID[n]);
                    ((ItemViewHolder) holder).layoutParams.rightMargin = (int) (rspace * ((ItemViewHolder) holder).dp);
                    ((ItemViewHolder) holder).imageViews[n].setImageResource(notes[bsplit[ni]-1]);
                }
                ((ItemViewHolder) holder).imageViews[n].setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).imageViews[n].setLayoutParams(((ItemViewHolder) holder).layoutParams);
            }else{
                ((ItemViewHolder)holder).layoutParams = new RelativeLayout.LayoutParams((int)(width*((ItemViewHolder)holder).dp), (int)(width*((ItemViewHolder)holder).dp));
                ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.ABOVE, R.id.view5);
                ((ItemViewHolder)holder).layoutParams.addRule(RelativeLayout.RIGHT_OF, ((ItemViewHolder)holder).imageViewID[n]);
                ((ItemViewHolder) holder).layoutParams.rightMargin = (int) (rspace * ((ItemViewHolder) holder).dp);
                ((ItemViewHolder)holder).imageViews[n].setImageResource(0);

                ((ItemViewHolder) holder).imageViews[n].setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).imageViews[n].setLayoutParams(((ItemViewHolder) holder).layoutParams);
            }
            // 음표 배경색 투명으로 리셋시키기
            ((ItemViewHolder)holder).imageViews[n].setBackgroundColor(Color.argb(0,0,0,0));

            // 연주시 연줃되는 음표에 배경색 넣어주기
            int position = n+(i*8);
            if(highlightPos != -11){
                if((position == highlightPos) && (highlightPos != -11) ){
                   // Log.i("MakeMusic:  ","하이라이트 포지션 값 :    "+highlightPos);
                   Log.i("testLog", "highlightPos "+highlightPos);
                   //argb 투명색까지 포함   , rgb는 그냥  색상만  //헥사 코드로 넣을것
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

        if(music == null){
            adapter_size = listData.size();// 작곡 중
        }else{
            adapter_size = music.getScoreLen();//작곡완료 연주중
        }
        int len =adapter_size;
        int cnt = len/8;
        len -= (cnt*8);
        if(len > 0){
            cnt++;
        }
        if(cnt == 0){
            cnt =1;
        }
        return cnt;
    }

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
