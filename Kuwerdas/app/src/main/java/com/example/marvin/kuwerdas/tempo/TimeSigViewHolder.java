package com.example.marvin.kuwerdas.tempo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.marvin.kuwerdas.R;

class TimeSigViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private View itemView;

    public TimeSigViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.tvTimeSig);
    }

    public TextView getTextView() {
        return textView;
    }

    public View getItemView() {
        return itemView;
    }

}
