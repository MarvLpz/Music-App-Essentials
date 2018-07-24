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
    EditText frmlyt_edttext;
    int j =0;
    int i =0;
    private long lastTouchTime = 0;
    private long currentTouchTime = 0;
    private KeyListener listener;
    private ItemTouchHelper mItemTouchHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("CREATED","CREATED");
        dragText = (TextView) findViewById(R.id.frame_clickable_id);
        frmlyt_edttext = (EditText) findViewById(R.id.fl_tv_id);

        lstMyList = new ArrayList<>();
//        lstMyList2 = new ArrayList<String>();
        lstMyList.add (new MyList("Verse 1"));
        lstMyList.add (new MyList("Chorus 1"));
        lstMyList.add (new MyList("Verse 2"));
//        lstMyList.get(0).Item = "new";
//        lstMyList.set(1,"");

//        lstMyList.set( 2, "New");
        recyclerView = (RecyclerView) findViewById(R.id.rv_id);
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
//                lstMyList.add (new MyList(""));
//                incrementMethod(editText.getText().toString());
                incrementMethod(editText.getText().toString());
                editText.setText(String.valueOf(i));
//                incrementMethod(lstMyList.get(0).toString());
//                incrementMethod("HELLO");
               /* lstMyList.remove(0);
                Adapter.notifyItemRemoved(0);*/
            }
        });
//        frmlyt_edttext.getTag();

/*        frmlyt_edttext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
                Log.d("CHANGED","CHANGED");
            }
        });*/

//        editText.addTextChangedListener(textWatcher);
/*        String newValue = "I like sheep.";
        int updateIndex = 1;
        lstMyList.set(updateIndex, newValue);
        Adapter.notifyItemChanged(updateIndex);*/
//        editText.setText(String.valueOf(lstMyList.size()));
//        editText.setText(String.valueOf(lstMyList.get(1).getItem()));

         /*for (int sample = 0; sample < lstMyList.size(); ++sample) {
//             lstMyList.get(sample);
//             String text = "hello \n\n there";
//             editText.setText(String.valueOf(lstMyList.get(sample).getItem()));
             boolean isFound = lstMyList.get(sample).getItem().contains("\n\n");
             if (isFound == true) {
//                 lstMyList.add (new MyList(text));
                 editText.setText("IT CONTAINS " + sample);
             }
         }*/

        /*for (int sample = 0; sample < lstMyList.size(); ++sample) {
            lstMyList.get(sample).getItem().contains("\n\n");
            if (frmlyt_edttext.getText().toString() ==  lstMyList.get(sample).getItem()){
//                frmlyt_edttext.addTextChangedListener(textWatcher);
            }
        }*/
    /*    frmlyt_edttext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("textchange true","TRUEEE");
                return false;
            }
        });*/
    }

    private void incrementMethod(String s){
            String[] arr = s.split("\n\n");
            for ( String ss : arr) {
                lstMyList.add (new MyList(editText.getText().toString().split("\n\n")[j]));
                ++j;
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
                // Notify the adapter of the move
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                deleteItem(viewHolder.getAdapterPosition());
            }
                     /*@Override
                     public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                         // We only want the active item to change
                         Log.d("NEW","NEW TOUCH");
//                         Adapter.notifyDataSetChanged();
                         if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
//                             Log.d("NEW","NEW TOUCH");
                             if (viewHolder instanceof ItemTouchHelperViewHolder) {
//                                 Log.d("NEW","NEW TOUCH");
                                 // Let the view holder know that this item is being moved or dragged
                                 ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                                 itemViewHolder.onItemSelected();
                             }
                         }
                         super.onSelectedChanged(viewHolder, actionState);
                     }*/
                    /* @Override
                     public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                         super.clearView(recyclerView, viewHolder);

                         viewHolder.itemView.setAlpha(ALPHA_FULL);

                         if (viewHolder instanceof ItemTouchHelperViewHolder) {
                             // Tell the view holder it's time to restore the idle state
                             ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                             itemViewHolder.onItemClear();
                         }
                     }*/
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


        /*if (Adapter.textChanged == true){
            Log.d("textchange true","TRUEEE");
        }*/
    }

    @Override
    public void onSecondaryIconClick(int p) {
        MyList item = (MyList) lstMyList.get(p);
//        Adapter.notifyDataSetChanged();
    }

    @Override
    public void updateMe() {
        Adapter.notifyDataSetChanged();
        Log.d("clicker","CLICKED THIS");
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
