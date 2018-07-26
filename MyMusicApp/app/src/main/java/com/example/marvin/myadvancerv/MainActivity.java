package com.example.marvin.myadvancerv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickCallback, OnStartDragListener {

    private final String TAG = MainActivity.class.getSimpleName();
    ArrayList<MyList> lstMyList;
//    ArrayList<String> lstMyList2;
    RecyclerView recyclerView;
    RecyclerViewAdapter Adapter;
    TextView dragText;
    TextView textView;
    Button button;
    EditText editText;
    int i =0;
    private long lastTouchTime = 0;
    private long currentTouchTime = 0;
    private KeyListener listener;
    private ItemTouchHelper mItemTouchHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_id);

        dragText = (TextView) findViewById(R.id.frame_clickable_id);
        lstMyList = new ArrayList<>();
        lstMyList.add (new MyList("Verse 1"));
        lstMyList.add (new MyList("Chorus 1"));
        lstMyList.add (new MyList("Verse 2"));
        Adapter = new RecyclerViewAdapter(this, lstMyList,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(Adapter);
        Adapter.setItemClickCallback(this);
        mItemTouchHelper = new ItemTouchHelper(createHelperCallback());
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        textView = (TextView) findViewById(R.id.tv_try);
        button = (Button) findViewById(R.id.btn_chk);
        editText = (EditText) findViewById(R.id.edt_input);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().isEmpty()){
                    incrementMethod(editText.getText().toString());
                    editText.setText(String.valueOf(i));}
                }
        }
        );
    }

    private void incrementMethod(String s){
        String[] arr = s.split("\n\n");
        for ( String ss : arr) {
                lstMyList.add (new MyList(ss));
            }
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP| ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT)
                 {
                     public static final float ALPHA_FULL = 1.0f;
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                deleteItem(viewHolder.getAdapterPosition());
            }
        };

        return simpleItemTouchCallback;
    }

    private void moveItem(int oldPos,int newPos){

        MyList item = (MyList) lstMyList.get(oldPos);
        lstMyList.remove(oldPos);
        lstMyList.add(newPos,item);
        Adapter.notifyItemMoved(oldPos,newPos);
    }

    private void deleteItem(final int position) {
        lstMyList.remove(position);
        Adapter.notifyItemRemoved(position);
    }

    @Override
    public void onItemClick(int p) {
        MyList item = (MyList) lstMyList.get(p);
    }

    @Override
    public void onSecondaryIconClick(int p) {
        MyList item = (MyList) lstMyList.get(p);
    }

    @Override
    public void updateMe() {
        Adapter.notifyDataSetChanged();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
//        Log.d("MOVE","I CLICKED THIS");
        mItemTouchHelper.startDrag(viewHolder);

    }
    public void onresume()
    {
//        Log.d("TRY", "try");
        Adapter.notifyDataSetChanged();

    }
}
