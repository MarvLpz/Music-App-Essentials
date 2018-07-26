package com.example.marvin.myadvancerv;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    Context con;
    private List<MyList> myData;
    private ItemClickCallback itemClickCallback;
    private boolean onBind;
    int positionPlacer;
    public interface ItemClickCallback{
        void onItemClick(int p);
        void onSecondaryIconClick(int p);
    }
    public void setItemClickCallback(final ItemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback;
    }
    public RecyclerViewAdapter(Context context, List<MyList> _myData) {
    con = context;
    myData = _myData;

    }

    EditText textView;
    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view;
        LayoutInflater  inflater = LayoutInflater.from(con);
        view = inflater.inflate(R.layout.frame_layout,parent,false);
        textView = (EditText)view.findViewById(R.id.fl_tv_id);
//        textView.setTag(positionPlacer);


        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*String[] arr = charSequence.toString().split("\n\n");
                for ( String ss : arr) {
                    myData.add (new MyList(ss));
                }*/
                if (!onBind) {
                    List<String> arr = Arrays.asList(charSequence.toString().split("\n\n"));
                    if (arr.size() > 1) {
//                        int position = myData.get().Item;
                        for (String a : arr.subList(1, arr.size())) {
                            myData.add(positionPlacer +1, new MyList(a));
                            notifyDataSetChanged();
                        }
                    }
                }
                else{
                    int a = parent.indexOfChild(textView);
                    if(a>-1 && a<=myData.size()) {
                        Log.d("TAG", "index is: " + a);
                        myData.get(a).setItem(charSequence.toString());
                    }
                }
            }



            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        onBind = true;
        holder.tv.setText(myData.get(position).getItem());
        holder.tv.setTag(position);
        onBind = false;

        holder.tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                positionPlacer = position;
                Log.d("POSTION", String.valueOf(positionPlacer));
//                holder.tv.setTag(position);
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return myData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,ItemTouchHelperViewHolder{
        TextView tv;
        FrameLayout frameLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.fl_tv_id);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.frame_clickable_id);
            frameLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.frame_clickable_id){
                itemClickCallback.onItemClick(getAdapterPosition());
            }
            else {
                itemClickCallback.onSecondaryIconClick(getAdapterPosition());
            }
        }

        @Override
        public void onItemSelected() {
//            editText2 = (EditText) findViewById(R.id.fl_tv_id);
//            tv.setFocusable(false);
            itemView.setBackgroundColor(Color.LTGRAY);

        }

        @Override
        public void onItemClear() {
//            tv.setFocusable(true);

            itemView.setBackgroundColor(0);
        }
    }
}
