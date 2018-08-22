package com.example.marvin.kuwerdas.song.picker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marvin.kuwerdas.R;

import java.util.List;

public class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.PickerViewHolder> {
    private Context context;
    private List<String> strings;

    public PickerAdapter(Context context, List<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public PickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chord_picker_item, parent, false);
        return new PickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PickerViewHolder holder, int position) {
        holder.tvData.setText(strings.get(position));
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    class PickerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvData;

        PickerViewHolder(View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tvChordPickerItem);
        }
    }
}
