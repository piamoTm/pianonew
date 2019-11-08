package com.example.pianoadroid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//작곡 MakeMusic 액티비티에서 사용되는 리사이클러뷰 어댑터
//아두이노로부터 블루투스 통신으로 'C' 라는 데이터가 오면 악보에 도를 그리는 역할

public class MakeMusic_RecyclerAdapter  extends RecyclerView.Adapter<MakeMusic_RecyclerAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Music> listData = new ArrayList<>();
    private ArrayList<String> db_value = new ArrayList<>();
    private Music music; //노래 한곡
    //============================================================================================
//    // EVENT 관련
//    public interface Playlist_viewClickListener{
//        void onPlatlistClicked(int position, String id);
//
//    }
//
//    private PlayList_RecyclerAdapter.Playlist_viewClickListener mListener = null;
//
//    // Refri_ViewClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
//    public void setOnPlaylist_ViewClickListener(PlayList_RecyclerAdapter.Playlist_viewClickListener listener){
//        this.mListener = listener;
//    }
    //============================================================================================


    public MakeMusic_RecyclerAdapter() {
    }

    public MakeMusic_RecyclerAdapter(ArrayList<Music> listData, ArrayList<String> db_value, Music music) {
        this.listData = listData;
        this.db_value = db_value;
        this.music = music;
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
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount(){
        // RecyclerView item의 총 개수 입니다.
        // 오선지의 개수
        return listData.size();
    }

    void addItem(Music data) {
        // 외부에서 item을 추가시킬 함수입니다.
        // 오선지를 추가하는 함수
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        //레이아웃 설정용 변수
        RelativeLayout relative;
        RelativeLayout.LayoutParams layoutParams;
        float dp;

        //음표png를 넣을 이미지뷰 8개
        ImageView[] imageViews  = new ImageView[8];
        int[] imageViewID = new int[8]; //이미지뷰 아이디 8개


        ItemViewHolder(View itemView) {
            super(itemView);
            relative = itemView.findViewById(R.id.relative_1);

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
            dp = itemView.getResources().getDisplayMetrics().density;
            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));


        }
        // 계이름(도,레,..)을 받아오면 xml에 동적으로 계이름에 맞게 이미지를 설정함
        public void makeScore(ArrayList<String> noteArr, float dp){
            int width = 35;
            int space = 35;


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

        void onBind(Music data) {

            // 임시로 디비에서 넘어온 악보들을 담는 배열 객체 선언
            if(data.getId() == 1){
                db_value.add(data.getTitle());
//            db_value.add("레");
//            db_value.add("도");
//            db_value.add("미");
//            db_value.add("도");
//            db_value.add("파");
//            db_value.add("파");
//            db_value.add("도");
                // db_value.add("솔");


                // 함수호출
                makeScore(db_value, dp);
                Log.e("db_value: ", db_value.size() + " ");

            }else if (data.getId() == 0){

            }



//            mTitle.setText(data.getTitle());
//            mTitle.setText(data.getWriter());
//            mId = data.getId();
//
//
//
//
//            mLay.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Intent intent = new Intent(v.getContext(),MainActivity.class);
////                            intent.putExtra("id", item.getId());
////                            v.getContext().startActivity(intent);
//                    Toast.makeText(v.getContext(),"선택됨 id: "+mId,Toast.LENGTH_SHORT).show();
//                }
//            });


        }

    }


}
