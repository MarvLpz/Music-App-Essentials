package com.example.marvin.kuwerdas.song.adapter;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.db.SongDatabaseUtils;
import com.example.marvin.kuwerdas.search.adapter.HeaderViewHolder;
import com.example.marvin.kuwerdas.song.SongFragment;
import com.example.marvin.kuwerdas.song.adapter.itemtouch.ItemClickCallback;
import com.example.marvin.kuwerdas.song.adapter.itemtouch.ItemTouchHelperAdapter;
import com.example.marvin.kuwerdas.song.adapter.itemtouch.OnStartDragListener;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.model.Verse;
import com.example.marvin.kuwerdas.song.util.SongUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerseItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
//        implements ItemTouchHelperAdapter
{
    private static final String TAG = "TAGGY";

    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_TITLE = 3;

    private List<Verse> mVerses;
    private List<Verse> versesToDelete;

    private SongFragment songFragment;

    private Song songDetails;

    private OnStartDragListener mDragStartListener;


    public VerseItemAdapter(Song songDetails, SongFragment songFragment, OnStartDragListener dragStartListener){
        this.mVerses = songDetails.getVerses();
        this.songDetails = songDetails;
        this.songFragment = songFragment;
        mDragStartListener = dragStartListener;
        versesToDelete = new ArrayList<>();

        Log.d("SONG","verse item count: " + getItemCount() + " - " + mVerses.size());
        for(Verse v : mVerses){
            Log.d("SONG","verse: " + v.getUid() + " - " + v.getTitle());
        }
        Log.d("SONG","---------------------------------------------");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_FOOTER){
            Log.d("SONG","Loaded footer");
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.create_new_song_layout,parent,false));
        } else if (viewType == TYPE_TITLE){
            Log.d("SONG","Loaded title");
            return new TitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.title_layout,parent,false),songFragment);
        }

        Log.d("SONG","Loaded verse");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.verse_layout, parent, false);
        return new VerseItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VerseItemViewHolder) {
            ((VerseItemViewHolder) holder).setVerseLinesData(mVerses.get(position-1));
            ((VerseItemViewHolder) holder).setFocusable(SongFragment.mode.equals(SongFragment.SongEditMode.EDIT));
            ((VerseItemViewHolder) holder).dragVerse.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        mDragStartListener.onStartDrag(holder);
                    }
                    return false;
                }
            });
        }

        else if(holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).setSongDetails(songDetails);
            ((TitleViewHolder) holder).setFocusableTitle(SongFragment.mode.equals(SongFragment.SongEditMode.EDIT));
            ((TitleViewHolder) holder).setFocusableArtist(SongFragment.mode.equals(SongFragment.SongEditMode.EDIT));
        }

        else if (holder instanceof HeaderViewHolder){
            HeaderViewHolder h = (HeaderViewHolder) holder;
            h.setText("CREATE NEW VERSE");
            h.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mVerses.add(SongUtil.asVerses("").get(0));
                    notifyItemInserted(mVerses.size());
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        Log.d("SONG","Called getItemViewType");
        if (isPositionFooter(position))
            return TYPE_FOOTER;
        if (isPositionTitle(position))
            return TYPE_TITLE;
        return TYPE_ITEM;
    }

    private boolean isPositionFooter(int position) {
        return position == mVerses.size() + 1;
    }

    private boolean isPositionTitle(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        if (mVerses == null) {
            return 0;
        }

       else if (mVerses.size() == 0) {
            //Return 1 here to show nothing
            return 2;
        }

        else
            Log.d("RETURNSIZE",String.valueOf(mVerses.size()));
        // Add extra view to show the footer view
        return mVerses.size() + 2;
//        return mVerses.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

//    @Override
//    public boolean onItemMove(int fromPosition, int toPosition) {
//        if (fromPosition < mVerses.size() && toPosition < mVerses.size()) {
//            if (fromPosition < toPosition) {
//                for (int i = fromPosition; i < toPosition; i++) {
//                    Collections.swap(mVerses, i, i + 1);
//                }
//            } else {
//                for (int i = fromPosition; i > toPosition; i--) {
//                    Collections.swap(mVerses, i, i - 1);
//                }
//            }
//            notifyItemMoved(fromPosition, toPosition);
//        }
//        return true;
//    }
//
//    @Override
//    public void onItemDismiss(final RecyclerView.ViewHolder viewHolder, final RecyclerView recyclerView) {
//        final int adapterPosition = viewHolder.getAdapterPosition();
//        final Verse mVerse = mVerses.get(adapterPosition-1);
//        Snackbar snackbar = Snackbar
//                .make(recyclerView, "Verse Deleted", Snackbar.LENGTH_LONG)
//                .setAction("UNDO", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mVerses.add(adapterPosition, mVerse);
//                        notifyItemInserted(adapterPosition);
//                        versesToDelete.remove(mVerse);
//                    }
//                });
//        snackbar.show();
//        mVerses.remove(adapterPosition-1);
//        notifyItemRemoved(adapterPosition-1);
//        versesToDelete.add(mVerse);
//        new SongDatabaseUtils.DeleteVerseFromDatabaseTask(mVerse).execute();
//    }

    public List<Verse> getItems() {
        return mVerses;
    }
}

