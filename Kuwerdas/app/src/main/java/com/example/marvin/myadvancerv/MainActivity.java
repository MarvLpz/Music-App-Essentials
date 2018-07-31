package com.example.marvin.myadvancerv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.marvin.myadvancerv.song.adapter.itemtouch.ItemTouchHelperViewHolder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickCallback{
    private final String TAG = MainActivity.class.getSimpleName();
    ArrayList<MyList> lstMyList;
    RecyclerView recyclerView;
    public static final float ALPHA_FULL = 1.0f;
    RecyclerViewAdapter Adapter;

    FrameLayout frameLayout;
    TextView textView;
    Button button;
    EditText editText;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.frame_clickable_id);

        lstMyList = new ArrayList<>();

        lstMyList.add (new MyList("Item 1"));
        lstMyList.add (new MyList("Item 2"));
        lstMyList.add (new MyList("Item 3"));

        recyclerView = (RecyclerView) findViewById(R.id.rv_id);
        Adapter = new RecyclerViewAdapter(this, lstMyList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(Adapter);
        Adapter.setItemClickCallback(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);

        textView = (TextView) findViewById(R.id.tv_try);
        button = (Button) findViewById(R.id.btn_chk);
        editText = (EditText) findViewById(R.id.edt_input);
        String s = "HelloThere";
        button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (isNonPrintable(editText.getText().toString()) == true){
            textView.setText("TRUE");
        }
        else {
            textView.setText("FALSE");
        }
    }
});
//        isNonPrintable("HelloThere");

    }
     boolean isNonPrintable(String s){
        for(int i=0; i<s.length(); ++i){
            char c = s.charAt(i);
            if(c == '\n') // fill in the ascii oct values
                return true;
        }

        return false;
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP| ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT)
                 {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
               moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;


            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                deleteItem(viewHolder.getAdapterPosition());
            }

                     @Override
                     public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                         // We only want the active item to change
                         if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                             if (viewHolder instanceof ItemTouchHelperViewHolder) {
                                 // Let the view holder know that this item is being moved or dragged
                                 ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                                 itemViewHolder.onItemSelected();
                                /* editText2 = (EditText) findViewById(R.id.fl_tv_id);
                                 editText2.setFocusable(false);*/
                             }
                         }
                         super.onSelectedChanged(viewHolder, actionState);

                     }
                     @Override
                     public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                         super.clearView(recyclerView, viewHolder);

                         viewHolder.itemView.setAlpha(ALPHA_FULL);

                         if (viewHolder instanceof ItemTouchHelperViewHolder) {
                             // Tell the view holder it's time to restore the idle state
                             ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                             itemViewHolder.onItemClear();
//                             editText2 = (EditText) findViewById(R.id.fl_tv_id);
//                             editText2.setFocusable(true);
                         }
                     }
        };
        return simpleItemTouchCallback;
    }


    private void moveItem(int oldPos,int newPos){
        MyList item = (MyList) lstMyList.get(oldPos);
        lstMyList.remove(oldPos);
        lstMyList.add(newPos,item);
        Adapter.notifyItemMoved(oldPos,newPos);
        /*editText2 = (EditText) findViewById(R.id.fl_tv_id);
        editText2.setFocusable(false);
        editText2.setFocusable(true);
        */

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
        Adapter.notifyDataSetChanged();
    }
}
