package com.example.marvin.kuwerdas.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marvin.kuwerdas.MainActivity;
import com.example.marvin.kuwerdas.OnChangeFragment;
import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.util.SongUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.marvin.kuwerdas.search.SearchFragment.SongLoader;

public class SongItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Song> songList;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

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

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.create_new_song_layout, parent, false));
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item_layout, parent, false);
        return new SongItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder h = (HeaderViewHolder) holder;
            h.setText("CREATE NEW SONG");
            h.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SongLoader.onChangeSong(SongUtil.asSong("","",""));
                    MainActivity.FragmentSwitcher.change(OnChangeFragment.Frags.SONG);
                }
            });

        } else if (holder instanceof SongItemViewHolder)  {
            Song song = songList.get(position-1);
            ((SongItemViewHolder) holder).setItemData(song.getSongTitle(), song.getArtist(), song.getDateModified());
            //cast holder to VHItem and set data
        }
    }

    @Override
    public int getItemCount() {
        if (songList == null) {
            return 0;
        }

        if (songList.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return songList.size() + 1;
    }

    public Song getSong(int position){
        return songList.get(position-1);
    }

}
