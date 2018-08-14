package com.example.marvin.kuwerdas.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.song.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongItemAdapter extends RecyclerView.Adapter<SongItemViewHolder>{
    List<Song> songList;
    View view;
    static RecyclerViewItemClickListener clickListener;

    public interface RecyclerViewItemClickListener {
        public void recyclerViewListItemClicked(View v, int position);
    }


    public SongItemAdapter(){
        this.songList = new ArrayList<>();
    }

    public SongItemAdapter(RecyclerViewItemClickListener clickListener){
        this.clickListener = clickListener;
        this.songList = new ArrayList<>();
    }

    public void updateItems(List<Song> songList){
        this.songList = songList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item_layout, parent, false);
        return new SongItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongItemViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.setItemData(song.getSongTitle(),song.getArtist(),song.getDateModified());
    }

    @Override
    public int getItemCount() {
        return (songList == null) ? 0 : songList.size();
    }

    public Song getSong(int position){
        return songList.get(position);
    }
}
