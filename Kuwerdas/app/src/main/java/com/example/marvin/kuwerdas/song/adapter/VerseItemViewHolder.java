package com.example.marvin.kuwerdas.song.adapter;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.song.SongFragment;
import com.example.marvin.kuwerdas.song.adapter.itemtouch.ItemTouchHelperViewHolder;
import com.example.marvin.kuwerdas.song.model.Verse;

public class VerseItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    // define the View objects
    private RecyclerView recyclerView;
    public ImageButton dragVerse;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public EditText getTextView() {
        return textView;
    }

    public void setFocusable(boolean val){
        if(val)
        {
            dragVerse.setVisibility(View.VISIBLE);
            textView.setFocusableInTouchMode(true);
            textView.setFocusable(true);
            adapter.notifyDataSetChanged();
        }
        else {
            textView.setFocusable(false);
            dragVerse.setVisibility(View.INVISIBLE);

        }
    }

    private EditText textView;

    public LineItemAdapter getAdapter() {
        return adapter;
    }

    private LineItemAdapter adapter;

    public VerseItemViewHolder(View itemView) {
        super(itemView);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.rvVerses);
        textView = (EditText) itemView.findViewById(R.id.etTitle);
        dragVerse = (ImageButton) itemView.findViewById(R.id.ibSwipeVerse);
    }

    public void setVerseLinesData(final Verse data){
        adapter = new LineItemAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        recyclerView.setAdapter(adapter);
        textView.setText(data.getTitle());
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                data.setTitle(s.toString());
                SongFragment.isSongEdited = true;
            }
        });
    }

    @Override
    public void onItemSelected() {
//        if (itemView. == 0){}
        itemView.setBackgroundColor(Color.LTGRAY);
        recyclerView.clearFocus();
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(Color.WHITE);
    }
}