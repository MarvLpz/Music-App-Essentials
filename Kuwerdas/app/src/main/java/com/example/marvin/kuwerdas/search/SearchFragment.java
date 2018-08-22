package com.example.marvin.kuwerdas.search;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.marvin.kuwerdas.MainActivity;
import com.example.marvin.kuwerdas.OnChangeFragment;
import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.db.SongDatabase;
import com.example.marvin.kuwerdas.db.SongDatabaseUtils;
import com.example.marvin.kuwerdas.search.adapter.HeaderViewHolder;
import com.example.marvin.kuwerdas.search.adapter.SongItemAdapter;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.util.SongUtil;

import java.util.List;

public class SearchFragment extends Fragment implements SongItemAdapter.RecyclerViewItemClickListener, MainActivity.OnNewSearchResult{

    public static OnChangeSong SongLoader;
    private static final String DATABASE_NAME = "SONG_DATABASE";

    private RecyclerView rvSearchResults;
    private SongItemAdapter adapter;
    private SongDatabase database;
    private View view;

    @Override
    public boolean onNewSearchResult(List<Song> songs) {
        if(adapter!=null) {
            adapter.updateItems(songs);
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
        MainActivity.SearchResultListener = this;
    }

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

    private void setUpItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.LTGRAY);
                xMark = ContextCompat.getDrawable(getContext(), R.drawable.trash);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) getResources().getDimension(R.dimen.swipeWidth);
                initiated = true;
            }

            // not important, we don't want drag & drop
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();

                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();

                if(viewHolder instanceof HeaderViewHolder)
                    return;

                new DeleteSongFromDatabaseTask(adapter.getSong(swipedPosition)).execute();

                Snackbar.make(view,"Deleted song",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if(viewHolder instanceof HeaderViewHolder)
                    return;

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                c.save();
                c.clipRect(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                xMark.draw(c);
                c.restore();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(rvSearchResults);
    }

    private void setUpAnimationDecoratorHelper() {
        rvSearchResults.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.LTGRAY);
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this lfeave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
    }

    public void init(){
        rvSearchResults = (RecyclerView) view.findViewById(R.id.rvSearchResults);
        setUpItemTouchHelper();
        setUpAnimationDecoratorHelper();
        database = SongDatabase.getSongDatabase(getActivity());
        rvSearchResults.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new SongItemAdapter(this);
        rvSearchResults.setAdapter(adapter);

        new GetSongsFromDatabaseTask().execute();
    }

    @Override
    public void recyclerViewListItemClicked(View v, int position) {
        if(SongLoader!=null){
            SongLoader.onChangeSong(adapter.getSong(position));
            if(MainActivity.FragmentSwitcher!=null){
                MainActivity.FragmentSwitcher.change(OnChangeFragment.Frags.SONG);

            }
        }
    }

    private class DeleteSongFromDatabaseTask extends AsyncTask<Void,Void,List<Song>>{

        Song song;

        public DeleteSongFromDatabaseTask(Song song){
            this.song = song;
        }

        @Override
        protected List<Song> doInBackground(Void... voids) {
            database.songDao().deleteSong(song);

            return database.songDao().getAllSongs();
        }

        @Override
        protected void onPostExecute(List<Song> songs) {
            super.onPostExecute(songs);

            onNewSearchResult(songs);
        }
    }

    private class GetSongsFromDatabaseTask extends AsyncTask<Void,Void,List<Song>>{

        @Override
        protected List<Song> doInBackground(Void... voids) {
            return database.songDao().getAllSongs();
        }

        @Override
        protected void onPostExecute(List<Song> songs) {
            super.onPostExecute(songs);

            onNewSearchResult(songs);
        }
    }
}
