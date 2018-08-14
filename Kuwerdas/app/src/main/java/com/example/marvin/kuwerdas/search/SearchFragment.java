package com.example.marvin.kuwerdas.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marvin.kuwerdas.MainActivity;
import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.db.SongDatabase;
import com.example.marvin.kuwerdas.search.adapter.SongItemAdapter;
import com.example.marvin.kuwerdas.song.model.Song;

import java.util.List;

public class SearchFragment extends Fragment implements SongItemAdapter.RecyclerViewItemClickListener, MainActivity.OnNewSearchResult{


    @Override
    public boolean onNewSearchResult(List<Song> songs) {
        if(adapter!=null) {
            adapter.updateItems(songs);
            Log.d("UPDATE ITEM", "Your query: " + songs);
            Log.d("UPDATE ITEM", "Your items: " + adapter.getItemCount());
            return true;
        }
        return false;
    }


    public interface OnClickSearchItem{
        boolean onClickSearchItem(Song item);
    }

    public interface OnChangeSong{
        public void onChangeSong(Song song);
    }


    public SearchFragment(){
        MainActivity.callback = this;
    }

    public static OnChangeSong callback;
    private static final String DATABASE_NAME = "SONG_DATABASE";

    private RecyclerView rvSearchResults;
    private SongItemAdapter adapter;
    private SongDatabase database;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        view = inflater.inflate(R.layout.fragment_search, null);
        init();
        return view;
    }

    public void init(){
        rvSearchResults = (RecyclerView) view.findViewById(R.id.rvSearchResults);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new SongItemAdapter(this);
        rvSearchResults.setAdapter(adapter);
    }

    @Override
    public void recyclerViewListItemClicked(View v, int position) {
        Log.d("TAGGY","item clicked at position: " + position);
        if(callback!=null){
            callback.onChangeSong(adapter.getSong(position));
            if(MainActivity.onChangeFragment!=null){
                MainActivity.onChangeFragment.change(OnChangeFragment.Frags.SONG);

            }
        }
//        Intent mIntent = new Intent(this, SongActivity.class);
//        mIntent.putExtra("Song", adapter.getSong(position));
//
//        startActivity(mIntent);
    }

}
