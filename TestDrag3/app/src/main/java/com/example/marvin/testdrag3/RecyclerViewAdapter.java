package com.example.marvin.testdrag3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    Context context;
    private String[] arr1;

    public RecyclerViewAdapter(Context con,String[] _arr1) {
        this.context = con;
        this.arr1 = _arr1;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.frame_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.firstText.setText(arr1[position]);
    }

    @Override
    public int getItemCount() {
        return arr1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView firstText;
        FrameLayout frameLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.frame_clickable_id);
            firstText = (TextView) itemView.findViewById(R.id.fl_tv_id);
        }

    }
}
