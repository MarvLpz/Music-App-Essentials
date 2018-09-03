package com.example.marvin.kuwerdas.tempo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marvin.kuwerdas.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

class TimeSigAdapter extends RecyclerView.Adapter<TimeSigViewHolder>{

    private List<TimeSignature> timeSignatures;

    public TimeSigAdapter() {
        timeSignatures = Arrays.asList(TimeSignature.four_four,
                TimeSignature.two_four,
                TimeSignature.three_four,TimeSignature.six_eight);
    }

    @NonNull
    @Override
    public TimeSigViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_signature_item, parent, false);
        return new TimeSigViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSigViewHolder holder, int position) {
        holder.getTextView().setText(timeSignatures.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return timeSignatures.size();
    }
}
