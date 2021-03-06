package com.example.marvin.kuwerdas.search.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marvin.kuwerdas.MainActivity;
import com.example.marvin.kuwerdas.OnChangeFragment;
import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.db.SongDatabaseUtils;
import com.example.marvin.kuwerdas.search.SearchFragment;
import com.example.marvin.kuwerdas.song.SongFragment;
import com.example.marvin.kuwerdas.song.adapter.itemtouch.ItemTouchHelperAdapter;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.model.Verse;
import com.example.marvin.kuwerdas.song.util.SongUtil;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.example.marvin.kuwerdas.search.SearchFragment.SongLoader;


public class SongItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
implements ItemTouchHelperAdapter {
    private List<Song> songList;
    private List<Song> deleteContainer;
    private Context context;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private boolean showFirstItem = true;

    static RecyclerViewItemClickListener clickListener;


    public void showInsertNewSongView(boolean cond){
        notifyItemChanged(0);
        showFirstItem = cond;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(final RecyclerView.ViewHolder viewHolder) {
        final int position = viewHolder.getAdapterPosition();
        final Song mSong = songList.get(position-1);
        deleteContainer = new ArrayList<>();

        if (position != 0  && position != songList.size() + 1) {
            deleteContainer.add(mSong);
            songList.remove(position-1);
        }



        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete song")
                .setMessage("Are you sure you want to delete this song?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Snackbar snackbar = Snackbar.make(viewHolder.itemView.getRootView().findViewById(R.id.id_searchFragment), "Song Deleted", Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(Color.WHITE);
                        snackbar.show();
                        (new SongDatabaseUtils.DeleteSongFromDatabaseTask(mSong)).execute();
                        notifyItemRemoved(position);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        songList.add(position-1, mSong);
                        notifyItemRemoved(position);
                        notifyItemInserted(position);
                        deleteContainer.remove(mSong);
                    }
                })
                .show();
    }

    public interface RecyclerViewItemClickListener {
        public void recyclerViewListItemClicked(View v, int position);
    }


    public SongItemAdapter(SearchFragment frag){
        this.clickListener = (RecyclerViewItemClickListener) frag;
        this.context = frag.getContext();
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
            h.getItemView().setVisibility(showFirstItem ? View.VISIBLE : View.GONE);
            h.setText("INSERT NEW SONG");
            h.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buildNewSongDialog(v.getContext()).show();
                }
            });

        } else if (holder instanceof SongItemViewHolder)  {
            Song song = songList.get(position-1);
            ((SongItemViewHolder) holder).setItemData(song.getSongTitle(), song.getArtist(), song.getDateModified());
            //cast holder to VHItem and set data
        }
    }

    public AlertDialog.Builder buildNewSongDialog(final Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Insert New Song");

        builder.setPositiveButton("New blank song", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SongLoader.onChangeSong(SongUtil.asSong("","",""));
                MainActivity.FragmentSwitcher.change(OnChangeFragment.Frags.SONG);
            }
        });

        builder.setNegativeButton("Generate from clipboard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String lyrics = ((ClipboardManager) c.getSystemService(CLIPBOARD_SERVICE)).getText().toString() ;
                SongLoader.onChangeSong(SongUtil.asSong("","",lyrics));
                SongFragment.isSongEdited = true;
                MainActivity.FragmentSwitcher.change(OnChangeFragment.Frags.SONG);
            }
        });

        return builder;
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
        return songList.get(position);
    }

}
