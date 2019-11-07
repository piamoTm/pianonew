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

public class MakeMusic_RecyclerAdapter  extends RecyclerView.Adapter<MakeMusic_RecyclerAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Music> listData = new ArrayList<>();

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
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Music data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10;


        //RelativeLayout relative_1;

        RelativeLayout.LayoutParams layoutParams;
        float dp;


        ItemViewHolder(View itemView) {
            super(itemView);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            img4 = itemView.findViewById(R.id.img4);
            img5 = itemView.findViewById(R.id.img5);
            img6 = itemView.findViewById(R.id.img6);
            img7 = itemView.findViewById(R.id.img7);
            img8 = itemView.findViewById(R.id.img8);
            img9 = itemView.findViewById(R.id.img9);
            img10 = itemView.findViewById(R.id.img10);


            dp = itemView.getResources().getDisplayMetrics().density;

            // 이미지뷰의 레이아웃을 가로 35dp, 세로 35dp로 지정
            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
            // layout_above적용

            //layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
            //layoutParams.bottomMargin = (int)(7*dp);
            //img1.setVisibility(View.VISIBLE);
            //img1.setLayoutParams(layoutParams);



            // 임시로 디비에서 넘어온 악보들을 담는 배열 객체 선언
            ArrayList<String> db_value=  new ArrayList<>();
            db_value.add("솔");
            db_value.add("레");
            db_value.add("도");
            db_value.add("미");
            db_value.add("도");
            db_value.add("파");
            db_value.add("파");
            db_value.add("도");
            db_value.add("라");
            db_value.add("라");


            // 함수호출
            M_name(db_value,dp);
            Log.e("db_value: ", db_value.size() + " ");

//            mLay.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    if(pos != RecyclerView.NO_POSITION){
//                        if(mListener != null) {
//                            Music item = listData.get(pos);
//                            Toast.makeText(v.getContext(),"선택됨 id: "+item.getId(),Toast.LENGTH_SHORT).show();
////                            Intent intent = new Intent(v.getContext(),MainActivity.class);
////                            intent.putExtra("id", item.getId());
////                            v.getContext().startActivity(intent);
//
//                            //notifyItemChanged(pos) ;
//                        }
//                    }
//                }
//            });
            // 계이름(도,레,..)을 받아오면 xml에 동적으로 계이름에 맞게 이미지를 설정함

        }
        // 계이름(도,레,..)을 받아오면 xml에 동적으로 계이름에 맞게 이미지를 설정함
        public void M_name(ArrayList<String> list, float dp){

            //dp = getResources().getDisplayMetrics().density;
            // 이미지뷰의 레이아웃을 가로 35dp, 세로 35dp로 지정
            //layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));

            // 배열에 있는 계이름 수만큼 반복문을 돌림
            for (int i = 0; i < list.size(); i++){

                // 이 if문은 신경쓰지 말것
                if(list.get(i).equals("도") || list.get(i).equals("레") || list.get(i).equals("미") || list.get(i).equals("파") || list.get(i).equals("솔") || list.get(i).equals("라") || list.get(i).equals("시")){
                    Log.e("list값: ", list.get(i));
                    //Log.e("list값22: ", list.get(0));
                    Log.e("i: ", i + "");

                    // 여기서 부터 배열 첫번째 계이름을 찾음
                    if(i == 0){

                        Log.e("배열 0번째: ", list.get(0));

                        if(list.get(0).equals("도")){
                            Log.e("0번째: ", "도");
                            // 이미지 크기 조절
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                            layoutParams.bottomMargin = (int)(7*dp);
                            img1.setImageResource(R.drawable.do_icon);

                        }else if(list.get(0).equals("레")){
                            Log.e("0번째: ", "레");
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);

                        }else if(list.get(0).equals("미")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }else if(list.get(0).equals("파")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);

                        }else if(list.get(0).equals("솔")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }else if(list.get(0).equals("라")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);

                        }else if(list.get(0).equals("시")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }

                        img1.setVisibility(View.VISIBLE);
                        img1.setLayoutParams(layoutParams);

                    }else if(i == 1){
                        Log.e("배열 1번째: ", list.get(1));

                        if(list.get(1).equals("도")){
                            // 이미지 크기 조절
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                            layoutParams.bottomMargin = (int)(7*dp);
                            img2.setImageResource(R.drawable.do_icon);

                        }else if(list.get(1).equals("레")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);

                        }else if(list.get(1).equals("미")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                            layoutParams.bottomMargin = (int)(7*dp);


                        }else if(list.get(1).equals("파")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);

                        }else if(list.get(1).equals("솔")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }else if(list.get(1).equals("라")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);

                        }else if(list.get(1).equals("시")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img1);
                            layoutParams.bottomMargin = (int)(7*dp);
                        }

                        img2.setVisibility(View.VISIBLE);
                        img2.setLayoutParams(layoutParams);

                    }else if(i == 2){
                        Log.e("배열 2번째: ", list.get(2));

                        if(list.get(2).equals("도")){
                            // 이미지 크기 조절
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                            layoutParams.bottomMargin = (int)(7*dp);
                            img3.setImageResource(R.drawable.do_icon);

                        }else if(list.get(2).equals("레")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);

                        }else if(list.get(2).equals("미")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                            layoutParams.bottomMargin = (int)(7*dp);


                        }else if(list.get(2).equals("파")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);

                        }else if(list.get(2).equals("솔")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }else if(list.get(2).equals("라")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);

                        }else if(list.get(2).equals("시")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img2);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }

                        img3.setVisibility(View.VISIBLE);
                        img3.setLayoutParams(layoutParams);
                    }else if(i == 3){
                        Log.e("배열 3번째: ", list.get(3));

                        if(list.get(3).equals("도")){
                            // 이미지 크기 조절
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                            layoutParams.bottomMargin = (int)(7*dp);
                            img4.setImageResource(R.drawable.do_icon);

                        }else if(list.get(3).equals("레")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);

                        }else if(list.get(3).equals("미")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                            layoutParams.bottomMargin = (int)(7*dp);


                        }else if(list.get(3).equals("파")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);

                        }else if(list.get(3).equals("솔")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }else if(list.get(3).equals("라")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);

                        }else if(list.get(3).equals("시")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img3);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }

                        img4.setVisibility(View.VISIBLE);
                        img4.setLayoutParams(layoutParams);

                    }else if(i == 4){
                        Log.e("배열 4번째: ", list.get(4));

                        if(list.get(4).equals("도")){
                            // 이미지 크기 조절
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                            layoutParams.bottomMargin = (int)(7*dp);
                            img5.setImageResource(R.drawable.do_icon);

                        }else if(list.get(4).equals("레")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);

                        }else if(list.get(4).equals("미")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                            layoutParams.bottomMargin = (int)(7*dp);


                        }else if(list.get(4).equals("파")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);

                        }else if(list.get(4).equals("솔")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }else if(list.get(4).equals("라")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);

                        }else if(list.get(4).equals("시")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img4);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }

                        img5.setVisibility(View.VISIBLE);
                        img5.setLayoutParams(layoutParams);

                    }else if(i == 5){
                        Log.e("배열 5번째: ", list.get(5));

                        if(list.get(5).equals("도")){
                            // 이미지 크기 조절
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                            layoutParams.bottomMargin = (int)(7*dp);
                            img6.setImageResource(R.drawable.do_icon);

                        }else if(list.get(5).equals("레")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);

                        }else if(list.get(5).equals("미")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                            layoutParams.bottomMargin = (int)(7*dp);


                        }else if(list.get(5).equals("파")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);

                        }else if(list.get(5).equals("솔")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }else if(list.get(5).equals("라")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);

                        }else if(list.get(5).equals("시")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img5);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }

                        img6.setVisibility(View.VISIBLE);
                        img6.setLayoutParams(layoutParams);

                    }else if(i == 6){
                        Log.e("배열 6번째: ", list.get(6));

                        if(list.get(6).equals("도")){
                            // 이미지 크기 조절
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                            layoutParams.bottomMargin = (int)(7*dp);
                            img7.setImageResource(R.drawable.do_icon);

                        }else if(list.get(6).equals("레")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);

                        }else if(list.get(6).equals("미")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                            layoutParams.bottomMargin = (int)(7*dp);


                        }else if(list.get(6).equals("파")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);

                        }else if(list.get(6).equals("솔")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }else if(list.get(6).equals("라")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);

                        }else if(list.get(6).equals("시")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img6);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }

                        img7.setVisibility(View.VISIBLE);
                        img7.setLayoutParams(layoutParams);

                    }else if(i == 7){
                        Log.e("배열 7번째: ", list.get(7));

                        if(list.get(7).equals("도")){
                            // 이미지 크기 조절
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                            layoutParams.bottomMargin = (int)(7*dp);
                            img8.setImageResource(R.drawable.do_icon);

                        }else if(list.get(7).equals("레")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);

                        }else if(list.get(7).equals("미")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                            layoutParams.bottomMargin = (int)(7*dp);


                        }else if(list.get(7).equals("파")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);

                        }else if(list.get(7).equals("솔")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }else if(list.get(7).equals("라")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);

                        }else if(list.get(7).equals("시")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img7);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }

                        img8.setVisibility(View.VISIBLE);
                        img8.setLayoutParams(layoutParams);

                    }else if(i == 8){
                        Log.e("배열 8번째: ", list.get(8));

                        if(list.get(8).equals("도")){
                            // 이미지 크기 조절
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img8);
                            layoutParams.bottomMargin = (int)(7*dp);
                            img9.setImageResource(R.drawable.do_icon);

                        }else if(list.get(8).equals("레")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img8);

                        }else if(list.get(8).equals("미")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img8);
                            layoutParams.bottomMargin = (int)(7*dp);


                        }else if(list.get(8).equals("파")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img8);

                        }else if(list.get(8).equals("솔")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img8);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }else if(list.get(8).equals("라")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img8);

                        }else if(list.get(8).equals("시")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img8);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }

                        img9.setVisibility(View.VISIBLE);
                        img9.setLayoutParams(layoutParams);

                    }else if(i == 9){
                        Log.e("배열 9번째: ", list.get(9));

                        if(list.get(9).equals("도")){
                            // 이미지 크기 조절
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view1);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img9);
                            layoutParams.bottomMargin = (int)(7*dp);
                            img10.setImageResource(R.drawable.do_icon);

                        }else if(list.get(9).equals("레")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img9);

                        }else if(list.get(9).equals("미")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view2);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img9);
                            layoutParams.bottomMargin = (int)(7*dp);


                        }else if(list.get(9).equals("파")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img9);

                        }else if(list.get(9).equals("솔")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img9);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }else if(list.get(9).equals("라")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view4);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img9);

                        }else if(list.get(9).equals("시")){
                            layoutParams = new RelativeLayout.LayoutParams((int)(35*dp), (int)(35*dp));
                            layoutParams.addRule(RelativeLayout.ABOVE, R.id.view3);
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img9);
                            layoutParams.bottomMargin = (int)(7*dp);

                        }

                        img10.setVisibility(View.VISIBLE);
                        img10.setLayoutParams(layoutParams);
                    }

                }

            }

        }
        void onBind(Music data) {
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
