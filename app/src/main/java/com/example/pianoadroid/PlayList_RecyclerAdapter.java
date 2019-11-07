package com.example.pianoadroid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayList_RecyclerAdapter extends RecyclerView.Adapter<PlayList_RecyclerAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Music> listData = new ArrayList<>();

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

        private TextView mTitle; // 노래 제목 ㄴ
        private TextView mWriter; //작곡가
        private  int mId;   // 노래 sqlite id


        ItemViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.title);
            mWriter = itemView.findViewById(R.id.writer);

        }

        void onBind(Music data) {
            mTitle.setText(data.getTitle());
            mTitle.setText(data.getWriter());
            mId = data.getId();
        }
    }
}
