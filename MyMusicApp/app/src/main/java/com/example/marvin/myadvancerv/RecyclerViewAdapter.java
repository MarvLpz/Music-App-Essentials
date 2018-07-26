package com.example.marvin.myadvancerv;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;

import com.example.marvin.myadvancerv.OnStartDragListener;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private KeyListener listener;
    Context con;
    private List<MyList> myData;
    private ItemClickCallback itemClickCallback;
    private long lastTouchTime = 0;
    private long currentTouchTime = 0;
    ArrayList<MyList> lstMyList;
    private boolean onBind;
    int j =0;
    public static boolean textChanged = false;

    public interface ItemClickCallback{
        void onItemClick(int p);
        void onSecondaryIconClick(int p);
        void updateMe();
    }

    private  OnStartDragListener mDragStartListener;

    public void setItemClickCallback(final ItemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback;
    }

    public RecyclerViewAdapter(Context context, List<MyList> _myData, OnStartDragListener DragStartListener) {
        mDragStartListener =  DragStartListener;
        con = context;
        myData = _myData;

    }

    TextView textView;

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view;
        LayoutInflater  inflater = LayoutInflater.from(con);
        view = inflater.inflate(R.layout.frame_layout,parent,false);
        int itemPosition = parent.indexOfChild(view);
        textView = (TextView)view.findViewById(R.id.fl_tv_id);
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!onBind) {
                    List<String> arr = Arrays.asList(editable.toString().split("\n\n"));

                    Log.d("TAG", "split array: " + arr.size());
                    if (arr.size() > 1) {
                        for (String a : arr.subList(1, arr.size())) {
                            myData.add(new MyList(a));
                            notifyDataSetChanged();
                        }
                    }
                    else{
                        int a = parent.indexOfChild(textView);
                        if(a>-1 && a<=myData.size()) {
                            Log.d("TAG", "index is: " + a);
                            myData.get(a).setItem(editable.toString());
                        }
                    }
                }
            }
        });
//        view = inflater.inflate(R.id.frame_clickable_id,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        onBind = true;
        holder.tv.setText(myData.get(position).getItem());
//        holder.tv.setTag(position);
        holder.dragText.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View v, MotionEvent event) {
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
            mDragStartListener.onStartDrag(holder);
            }
                return false;
            }
        }
        );
        onBind = false;
    }
//notifyDataSetChanged();
        /*holder.tv.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                *//*MainActivity sample = new MainActivity();
                sample.onresume();*//*
                Log.d("TOUCH","THIS IS IT");
//                itemClickCallback.updateMe();
//                boolean isFound = myData.get(sample).getItem().contains("\n\n");
                boolean isFound = holder.tv.getText().toString().contains("\n\n");
                if (isFound == true) {
                    Log.d("TOUCH","CONTAINS");
                }
                return false;
            }
        });*/

//        container = String.valueOf(holder.tv.getText());

/*    public void updateChanges(List<MyList> newlist) {

        myData.clear();
        myData.addAll(newlist);
        this.notifyDataSetChanged();

    }*/

@Override
    public int getItemCount() {
        return myData.size();
    }

//    private class MyCustomEditTextListener implements TextWatcher {
//        private int position;
//
//        public void updatePosition(int position) {
//            this.position = position;
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//            // no op
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//            myData.get(position).Item = charSequence.toString();
////            myData.get(position).getItem().contains("\n\n");
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//            // no op
//        }
//    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener,ItemTouchHelperViewHolder{
//    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        EditText tv;
        EditText editText;
        TextView dragText;
        FrameLayout frameLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (EditText) itemView.findViewById(R.id.fl_tv_id);
            editText = (EditText) itemView.findViewById(R.id.edt_input);
            dragText = (TextView) itemView.findViewById(R.id.frame_clickable_id);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.frame_id);
//            tv.addTextChangedListener(this);
//            MyTextWatcher textWatcher = new MyTextWatcher(tv);
//

//            tv.setCursorVisible(false);
//            tv.setFocusable(false);
//            dragText.setOnClickListener(this);
           /* tv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d("beforeTextChanged","TOUCHED");
//                    myData.get(0).Item = "new";
//                    notifyDataSetChanged();
                    return false;
                }
            });*/
        }

        @Override
        public void onClick(View view) {
/*                if (view.getId() != R.id.frame_clickable_id){
//                    if (view != null) {
//                }
//                    view.setClickable(false);
                }*/
/*
                else  if (view.getId() == R.id.frame_clickable_id){
                itemClickCallback.onItemClick(getAdapterPosition());
            }
*/
            if (view.getId() == R.id.fl_tv_id){
                itemClickCallback.onItemClick(getAdapterPosition());

            }

            else {
                itemClickCallback.onSecondaryIconClick(getAdapterPosition());
            }
        }
   /*     private int position;

        public void updatePosition(int position) {
            this.position = position;
        }*/

    /*    @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            Log.d("New Ontext","New ONTEXT");
            myData.get(position).Item = charSequence.toString();

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
            myData.add (new MyList("NEW" + ++j));
        }*/
        /*@Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d("beforeTextChanged","beforeTextChanged");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

//            Log.d("SIZE",myData.get(0).getItem());
            *//*Log.d("afterTextChanged","afterTextChanged" );*//*
         *//*   Log.d("onTextChanged",myData.get(0).Item );
//            textChanged = true;
//            myData.set(1,"sdf");
            myData.get(0).Item = "NEW" + ++j;*//*

            *//*for (int sample = 0; sample < myData.size(); ++sample) {
                boolean isFound = myData.get(position).getItem().contains("\n\n");
                if (isFound == true) {
                    Log.d("onTextChanged","onTextChanged");
                }}*//*
               *//* if (frmlyt_edttext.getText().toString() ==  lstMyList.get(sample).getItem()){
//                frmlyt_edttext.addTextChangedListener(textWatcher);
                }*//*

//            notifyDataSetChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

            Log.d("afterTextChanged","afterTextChanged" );
            Log.d("afterTextChanged",myData.get(position).Item );
//            textChanged = true;
//            myData.set(1,"sdf");
//            myData.get(0).Item = "NEW";
//            notifyDataSetChanged();
//            myData.getAdapter()).notifyDataSetChanged();
//            ((BaseAdapter) myData.getAdapter()).notifyDataSetChanged();
//            textChanged = true;
            *//*if (textChanged == true){
                Log.d("afterTextChanged","TRUE" );
            }*//*
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));

//           MyViewHolder.notifyDataSetChanged();
            *//*for (int sample = 0; sample < myData.size(); ++sample) {
//             myData.get(sample);
//             String text = "hello \n\n there";
//             editText.setText(String.valueOf(myData.get(sample).getItem()));

                myData.get(sample).Item = myData.get(sample).Item;

                boolean isFound = myData.get(sample).getItem().contains("\n\n");
                if (isFound == true) {
//                 myData.add (new MyList(text));
//                    editText.setText("IT CONTAINS " + sample);
                    Log.d("TEXT","TextFound");
//                    myData.add (new MyList("NEW ITEM"));
                    String[] arr = myData.get(sample).getItem().split("\n\n");
                    for ( String ss : arr) {
                        myData.add (new MyList(editText.getText().toString().split("\n\n")[j]));
                        ++j;
                }
                }
            }*//*
        }*/

        @Override
        public void onItemSelected() {
           /* MainActivity sampz = new MainActivity();
            sampz.onresume();*/
//            itemClickCallback.updateMe();
//            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
//            itemView.setBackgroundColor(0);

        }
    }
    public class MyTextWatcher implements TextWatcher {

        private EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
/*            int position = (int) editText.getTag();
            Log.d("NEW METHOD ",String.valueOf(position));
            myData.get(position).Item = "NEW" + position;
            Log.d("NEW METHOD",myData.get(position).Item);
            myData.add(new MyList("Newer"));
            refreshBlockOverlay(position);*/
//            editText.setText("NEW");
//            Log.d("NEW METHOD ",editText.getText().toString());
            /*myData.get(position).Item = "NEW";
            Log.d("NEW METHOD",myData.get(position).Item);
            notifyDataSetChanged();*/
            // Do whatever you want with position

        }

        @Override
        public void afterTextChanged(Editable s) {
            String[] arr = String.valueOf(s).split("\n\n");

            for(String a : arr){
                myData.add(new MyList(a));
            }
//            notifyDataSetChanged();
        }
    }
    public void refreshBlockOverlay(int position) {
        notifyItemChanged(position);
    }
}
