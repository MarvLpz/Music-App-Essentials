package com.example.marvin.kuwerdas.song.picker;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marvin.kuwerdas.OnSwipeListener;
import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.VibrateListener;
import com.example.marvin.kuwerdas.song.SongFragment;
import com.example.marvin.kuwerdas.song.adapter.ChordItemAdapter;
import com.example.marvin.kuwerdas.song.picker.model.Accidental;
import com.example.marvin.kuwerdas.song.picker.model.DetailedChord;
import com.example.marvin.kuwerdas.song.picker.model.Number;
import com.example.marvin.kuwerdas.song.picker.model.Scale;

import org.w3c.dom.Text;

import java.util.List;

import static android.content.Context.VIBRATOR_SERVICE;

public class ChordPickerAdapter extends RecyclerView.Adapter<ChordPickerAdapter.PickerViewHolder> {

    public enum PickerType { TYPE_PICKER_SELECTOR,TYPE_PICKER_CHORD_DISPLAY }

    private PickerType type;
    private Context context;
    private List strings;
    private Object selectedItem;
    private DetailedChord chord;

    public ChordPickerAdapter(Context context, List strings, DetailedChord chord, PickerType type) {
        this.context = context;
        this.strings = strings;
        this.chord = chord;
        this.type = type;
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
    public PickerViewHolder onCreateViewHolder(ViewGroup parent, int c) {
        View view;

        if(type == PickerType.TYPE_PICKER_CHORD_DISPLAY) {
            view = LayoutInflater.from(context).inflate(R.layout.chord_display_picker_item, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.chord_picker_item, parent, false);
        }
        return new PickerViewHolder(view,type);
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

            holder.tvData.setPadding(25,0,25,0);
            holder.tvData.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.tvData.setOnTouchListener( new ChoiceTouchListener());
        }
        else {
                text = strings.get(position).toString();
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

        PickerViewHolder(View itemView, PickerType type) {
            super(itemView);

            tvData = itemView.findViewById(type==PickerType.TYPE_PICKER_CHORD_DISPLAY ? R.id.tvChordDisplayItem : R.id.tvChordPickerItem);
        }
    }

    class ChoiceTouchListener implements View.OnTouchListener{

        GestureDetector gestureDetector;


        View view;
        float initialTouchX = -1;
        boolean notTouched = true;

        public ChoiceTouchListener() {
            gestureDetector=new GestureDetector(context,new OnSwipeListener() {

                @Override
                public boolean onSwipe(Direction direction) {
                    if (direction == Direction.up || direction == Direction.down) {
                        //do your stuff

                    } else if (direction == Direction.right || direction == Direction.left) {
                        //do your stuff
                        handler.removeCallbacks(mLongPressed);
                    }
                    return true;
                }


            });
        }

        VibrateListener vibrateListener = SongFragment.getInstance();

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            view = v;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler.postDelayed(mLongPressed, 500);

                    initialTouchX = event.getRawX();
                    //This is where my code for movement is initialized to get original location.

                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    handler.removeCallbacks(mLongPressed);

//                    if(Math.abs(event.getRawX() - initialTouchX) <= 2 && !goneFlag) {
//                        //Code for single click
//                        return false;
//                    }
                    break;
                case MotionEvent.ACTION_MOVE:
//                    handler.removeCallbacks(mLongPressed);
                    //Code for movement here. This may include using a window manager to update the view
                    break;
            }
//            return gestureDetector.onTouchEvent(event);
            return true;
        }

        boolean goneFlag = false;

        //Put this into the class
        final Handler handler = new Handler();
        Runnable mLongPressed = new Runnable() {
            public void run() {
                goneFlag = true;
                vibrateListener.vibrate(100);

                ChordItemAdapter getTheChord = new ChordItemAdapter();
                getTheChord.getChord(((TextView) view.findViewById(R.id.tvChordDisplayItem)).getText().toString());

                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
            }
        };

    }

}
