package com.example.marvin.kuwerdas.song.picker;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.song.picker.model.Accidental;
import com.example.marvin.kuwerdas.song.picker.model.DetailedChord;
import com.example.marvin.kuwerdas.song.picker.model.Number;
import com.example.marvin.kuwerdas.song.picker.model.Scale;

import java.util.List;

public class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.PickerViewHolder> {
    private Context context;
    private List strings;
    private Object selectedItem;
    private DetailedChord chord;

    public PickerAdapter(Context context, List strings, DetailedChord chord) {
        this.context = context;
        this.strings = strings;
        this.chord = chord;
    }

    public Object getSelectedItem(){
        return selectedItem;
    }

    public boolean setSelectedItem(int pos){
        if(strings!=null && (pos<strings.size() && pos>=0))
        {
            selectedItem = strings.get(pos);
            if(strings.get(pos) instanceof Accidental){
                chord.setAccidental((Accidental) strings.get(pos));
            }
            else if(strings.get(pos) instanceof Scale){
                chord.setScale((Scale) strings.get(pos));
            }
            else if(strings.get(pos) instanceof Number){
                chord.setNumber((Number) strings.get(pos));
            }

            return true;
        }
        return false;
    }

    @Override
    public PickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chord_picker_item, parent, false);
        return new PickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PickerViewHolder holder, int position) {
        String text;
        if(strings.get(position) instanceof Accidental) {
            text = ((Accidental)strings.get(position)).getLabel();
        }
        else if(strings.get(position) instanceof Scale) {
            text = ((Scale)strings.get(position)).getLabel();
        }
        else if(strings.get(position) instanceof Number) {
            text  = ((Number)strings.get(position)).getLabel();
        }
        else if(strings.get(position) instanceof DetailedChord) {
            text  = ((DetailedChord)strings.get(position)).getChord();

        }
        else {
                text = strings.get(position).toString();
                Log.d("OBJECT","INSTANCE IS " + strings.get(position).getClass().getName());
        }
        holder.tvData.setText(text);
        holder.tvData.setTextSize(strings.get(position) instanceof DetailedChord ? 25 : 18);
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
