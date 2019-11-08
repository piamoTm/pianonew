package com.example.pianoadroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlayList_RecyclerAdapter extends RecyclerView.Adapter<PlayList_RecyclerAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Music> listData = new ArrayList<>();

    public PlayList_RecyclerAdapter(ArrayList<Music> listData) {
        this.listData = listData;
    }

    //============================================================================================
        // EVENT 관련
        public interface Playlist_viewClickListener{
            void onPlatlistClicked(int position, String id);

        }

        private Playlist_viewClickListener mListener = null;

        // Refri_ViewClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
        public void setOnPlaylist_ViewClickListener(Playlist_viewClickListener listener){
            this.mListener = listener;
        }
        //============================================================================================

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
            // return 인자는 ViewHolder 입니다.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleritem_playlist, parent, false);
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

        private LinearLayout mLay;
        private TextView mTitle; // 노래 제목
        private TextView mWriter; //작곡가
        private  int mId;   // 노래 sqlite id


        ItemViewHolder(View itemView) {
            super(itemView);
            mLay = itemView.findViewById(R.id.lay_item);
            mTitle = itemView.findViewById(R.id.title);
            mWriter = itemView.findViewById(R.id.writer);


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
        }

        void onBind(Music data) {
            mTitle.setText(data.getTitle());
            mWriter.setText(data.getWriter());
            mId = data.getId();

            mLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),MusicTest.class);
                    intent.putExtra("id", mId);
                    v.getContext().startActivity(intent);
                    Toast.makeText(v.getContext(),"선택됨 id: "+mId,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
