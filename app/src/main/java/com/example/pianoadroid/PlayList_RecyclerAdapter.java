package com.example.pianoadroid;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlayList_RecyclerAdapter extends RecyclerView.Adapter<PlayList_RecyclerAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Music> listData = new ArrayList<>();
    private int menuIndex;

    private final int MENU_READ = 0;//연습하기 버튼
    private final int MENU_WRITE = 1;//작곡하기버튼

    private final int WRITE_NEW = 111;//작곡하기 -> 새로만들기
    private final int WRITE_OLD = 222;//작곡하기 ->기존곡
    private Context context;
    private OnItemClickListener onItemClickListener;

    private  DBMyProductHelper_Write db;

    public PlayList_RecyclerAdapter(ArrayList<Music> listData, int menuIndex, Context context, OnItemClickListener onItemClickListener) {
        this.listData = listData;
        this.menuIndex = menuIndex;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void setListData(ArrayList<Music> listData) {
        this.listData = listData;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
            // return 인자는 ViewHolder 입니다.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_playlist, parent, false);
            return new ItemViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.

            if (menuIndex == MENU_READ){ //연습하기
                holder.onBind(listData.get(position),MENU_READ);
            }else{ //MENU_WRITE //작곡하기
                if(position == 0){
                    //Intex 0은 +버튼
                    holder.onBindMakePlusBtn();

               }else{
                    holder.onBind(listData.get(position-1),MENU_WRITE);
                }
            }
        }

        @Override // RecyclerView 아이템의 총 개수 입니다.
        public int getItemCount() {
            int itemCnt = 0;
            if(menuIndex == MENU_READ){ //연습하기
                itemCnt= listData.size(); //노래목록 만큼만

            }else if(menuIndex == MENU_WRITE){//작곡하
                itemCnt= listData.size() + 1; //노래목록 만큼에 [+]버튼용 하나 추가
            }
            return itemCnt;
        }

        void addItem(Music data) {
            // 외부에서 item을 추가시킬 함수입니다.
            listData.add(data);
        }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout mLay;
        private TextView mTitle; // 노래 제목
        private TextView mWriter; //작곡가
        private  int mId;   // 노래 sqlite id
        private ImageView mAddimg;
        private ImageView mDelimg;


        ItemViewHolder(View itemView) {
            super(itemView);
            mLay = itemView.findViewById(R.id.lay_item);
            mTitle = itemView.findViewById(R.id.title);
            mWriter = itemView.findViewById(R.id.writer);
            mAddimg = itemView.findViewById(R.id.addimg);
            mDelimg = itemView.findViewById(R.id.delete_btn);

        }

        void onBind(Music data ,int Index) {
            mTitle.setText(data.getTitle()); //타이틀
            mWriter.setText(data.getWriter()); //작가
            mId = data.getId();//노래 아이디
            if(Index == 1){// 작곡일때
                mAddimg.setVisibility(View.INVISIBLE);//이미지
            }else{  //연주하기 일때
                mAddimg.setVisibility(View.INVISIBLE);//이미지
                mDelimg.setVisibility(View.INVISIBLE);//삭제 이미지
            }


            mLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if(menuIndex == MENU_READ){
                        intent = new Intent(v.getContext(), MusicTest.class);
                    }else{
                        intent = new Intent(v.getContext(), MakeMusic.class);
                    }
                    intent.putExtra("id", mId);
                    intent.putExtra("from", WRITE_OLD);
                    v.getContext().startActivity(intent);
                    //Toast.makeText(v.getContext(),"선택됨 id: "+mId,Toast.LENGTH_SHORT).show();
                }
            });

            //작곡하기에만  있는 삭제하기 버튼
            mDelimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {

                            listData.remove(pos -1);
                            notifyItemRemoved(pos -1);
                            notifyDataSetChanged();
                            //notifyItemChanged(pos -1);
                            //notifyItemRangeChanged(pos - 1, listData.size());
                    }

                    //SQLite db helper init 초기화
                    db = new DBMyProductHelper_Write(context);
                    // DB Lite 에서 클릭된 악보 삭제하기
                    db.deleteMusic(mId);

                }
            });
        }

        void onBindMakePlusBtn(){ //+버튼
            mTitle.setVisibility(View.INVISIBLE); //타이틀
            mWriter.setVisibility(View.INVISIBLE);//작가
            mDelimg.setVisibility(View.INVISIBLE); // 삭제 버튼
            //mAddimg.setVisibility(View.VISIBLE);//이미지


           mLay.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   onItemClickListener.onItemClick();
               }
           });
        }
    }

    public interface OnItemClickListener {
        void onItemClick();
    }
}
