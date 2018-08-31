package com.example.marvin.kuwerdas.song;

import android.app.Activity;
import android.content.ClipData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.example.marvin.kuwerdas.VibrateListener;
import com.example.marvin.kuwerdas.db.SongDatabaseUtils;
import com.example.marvin.kuwerdas.song.adapter.TitleViewHolder;
import com.example.marvin.kuwerdas.song.adapter.itemtouch.OnStartDragListener;
import com.example.marvin.kuwerdas.R;
import com.example.marvin.kuwerdas.db.SongDatabase;
import com.example.marvin.kuwerdas.search.SearchFragment;
import com.example.marvin.kuwerdas.song.adapter.ChordItemAdapter;
import com.example.marvin.kuwerdas.song.adapter.VerseItemAdapter;
import com.example.marvin.kuwerdas.song.adapter.itemtouch.VerseItemTouchHelperCallback;
import com.example.marvin.kuwerdas.song.model.Chord;
import com.example.marvin.kuwerdas.song.model.Line;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.model.Verse;
import com.example.marvin.kuwerdas.song.picker.ChordPickerAdapter;
import com.example.marvin.kuwerdas.song.picker.PickerItems;
import com.example.marvin.kuwerdas.song.picker.model.Accidental;
import com.example.marvin.kuwerdas.song.picker.model.DetailedChord;
import com.example.marvin.kuwerdas.song.picker.model.DetailedChordIndex;
import com.example.marvin.kuwerdas.song.picker.model.Letter;
import com.example.marvin.kuwerdas.song.picker.model.Number;
import com.example.marvin.kuwerdas.song.picker.model.Scale;
import com.example.marvin.kuwerdas.song.util.Transposer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.VIBRATOR_SERVICE;


public class SongFragment extends Fragment implements OnStartDragListener, SearchFragment.OnChangeSong, TitleViewHolder.ChordTransposer, VibrateListener{

    private static SongFragment single_instance = null;

    public SongFragment(){
        single_instance = this;
    }

    public static SongFragment getInstance()
    {
        if (single_instance == null)
            single_instance = new SongFragment();

        return single_instance;
    }

    private VerseItemAdapter adapter;
    private RecyclerView recyclerView;
    private VerseItemTouchHelperCallback verseItemTouchHelperCallback;
    private ItemTouchHelper itemTouchHelper;
    private SongDatabase database;
    private ProgressBar progressBar;
    private View songContainer;
    private View view;
    private FloatingActionButton fabEdit;

    private Button btnAddChord;
    private Button btnDeleteChord;

    public static Song song;
    private static boolean isLoadedFromDB = false;
    public static boolean isSongEdited = false;
    public static boolean isInDeleteMode = false;
    public static SongEditMode mode = SongEditMode.READ_ONLY;
    public static SongEditMode2 mode2 = SongEditMode2.LYRICS;
    public static SongEditMode3 mode3 = SongEditMode3.NONE;

    private Vibrator mVibrator;

    private TextView tv_DragChord1;
    private TextView tv_DragChord2;
    private TextView tv_DragChord3;
    private TextView tv_DragChord4;
    private TextView tv_DragChord5;
    private TextView tv_DragChord6;
    private TextView tv_DragChord7;


    String get_accidental = "",get_scale= "",get_number= "";

    float dX;
    float dY;
    int lastAction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        view = inflater.inflate(R.layout.fragment_song, null);
//        if(getActivity().getActionBar()!=null)
//            getActivity().getActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        SearchFragment.SongLoader = this;
        init();
        initializeChordMenuToolbar();
//        showPicker();
        return view;
    }

    private Toolbar toolbar;

    @Override
    public void vibrate(int ms) {
        if(mVibrator!=null)
            mVibrator.vibrate(ms);
    }

    public enum SongEditMode{
        EDIT, READ_ONLY
    }
    public enum SongEditMode2{
        MUSIC, LYRICS
    }
    public enum SongEditMode3{
        NONE, CHORD_DRAWER_UP,CHORD_DRAWER_DOWN
    }


    private void init(){
        database = SongDatabase.getSongDatabase(getContext());
        toolbar = view.findViewById(R.id.tbChordEditor);
        toolbar.setVisibility(View.GONE);
        mVibrator = (Vibrator)getActivity().getSystemService(VIBRATOR_SERVICE);
        progressBar = view.findViewById(R.id.pbSong);
        songContainer = view.findViewById(R.id.songContainer);
        fabEdit = view.findViewById(R.id.fabToggleEdit);
        recyclerView = view.findViewById(R.id.rvSong);
        //            ItemTouchHelper.Callback callback = new VerseItemTouchHelperCallback(adapter);
//        adapter = new VerseItemAdapter(song, this);

        fabEdit.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                setEditMode(mode != SongEditMode.EDIT);
                return true;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        showProgressBar(true);
        Log.d("SONG","loaded init");
        if(song!=null){
            if(isLoadedFromDB) {
                new GetSongDetailsDatabaseTask().execute();
                return;
            }
            else
                onChangeSong(song);
            showProgressBar(false);
        }

    }

    public void setEditMode(boolean setToEdit){
       if(setToEdit){ //Set to Edit mode
           recyclerView.setClickable(true);
           Flubber.with().animation(Flubber.AnimationPreset.FADE_IN).createFor(toolbar).start();
           mode = SongEditMode.EDIT;
           fabEdit.setImageResource(R.drawable.t);
           mode2 = SongEditMode2.LYRICS;
           mode3 = SongEditMode3.CHORD_DRAWER_DOWN;

           Toast.makeText(view.getContext(),"Edit mode enabled",Toast.LENGTH_SHORT).show();

           fabEdit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (isSongEditable()) {
                       if (mode2 == SongEditMode2.MUSIC) {
                           fabEdit.setImageResource(R.drawable.t);
                           mode2 = SongEditMode2.LYRICS;
                           toolbar.setVisibility(View.GONE);
                           isInDeleteMode = false;
                           if (adapter != null)
                               adapter.notifyDataSetChanged();
                       } else {
                           fabEdit.setImageResource(R.drawable.m);
                           mode2 = SongEditMode2.MUSIC;
                           hideKeyboard(getActivity());
                           toolbar.setVisibility(View.VISIBLE);
                           if (adapter != null)
                               adapter.notifyDataSetChanged();
                       }
                   }
               }
           });

       } else { //Set to Read Only mode
           isInDeleteMode = false;
           mode3 = SongEditMode3.NONE;
           ChordItemAdapter.getTriggerDelBtn(isInDeleteMode);
           recyclerView.setClickable(false);
           fabEdit.setImageResource(R.drawable.edit);
           hideKeyboard(getActivity());
           saveSongToDatabase();
           mode = SongEditMode.READ_ONLY;
           toolbar.setVisibility(View.GONE);
           Flubber.with().animation(Flubber.AnimationPreset.FADE_OUT).createFor(toolbar).start();

           if (adapter!=null)
               adapter.notifyDataSetChanged();
       }
    }

    public void showProgressBar(boolean val){
        progressBar.setVisibility(val ? View.VISIBLE : View.GONE);
        songContainer.setVisibility(val ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDetach() {
        saveSongToDatabase();
        super.onDetach();
    }

    @Override
    public void onPause() {
        saveSongToDatabase();
        super.onPause();
    }

    @Override
    public void transposeUp() {
        changeKey(true);
    }

    @Override
    public void transposeDown() {
        changeKey(false);
    }


    private final class touchMe implements View.OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - event.getRawX();
                    dY = view.getY() - event.getRawY();
                    lastAction = MotionEvent.ACTION_DOWN;

                    /*LinearLayout.LayoutParams params = new LinearLayout.
                            LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10,10,10,10);
                    view.setLayoutParams(params);*/
                    break;

                case MotionEvent.ACTION_MOVE:
                    view.setY(event.getRawY() + dY);
                    view.setX(event.getRawX() + dX);
                    lastAction = MotionEvent.ACTION_MOVE;
                    break;

                case MotionEvent.ACTION_UP:
                    if (lastAction == MotionEvent.ACTION_DOWN)
//                        Toast.makeText(con, "Clicked!", Toast.LENGTH_SHORT).show();
                        break;

                default:
                    return false;
            }
            return true;
        }
    }

    private void changeKey(boolean dir){
        Log.d("SONG","song: " + song.getArtist() + " - " + song.getSongTitle());
        for(int v=0;v<song.getVerses().size();v++){
            Verse verse = song.getVerses().get(v);

            Boolean hasNoChords = true;
            for(Line line : verse.getLines()){
                for(int l=0;l<line.getChordSet().size();l++){
                    Chord chord = line.getChordSet().get(l);

                    if(!chord.getChord().equals(Chord.EMPTY_CHORD)) {
                        chord.setChord(dir ? Transposer.transposeChordUp(chord.getChord()) : Transposer.transposeChordDown(chord.getChord()));
                        hasNoChords = false;
                    }
                }
            }
            if(!hasNoChords)
                adapter.notifyItemChanged(v+1);
        }
        SongFragment.isSongEdited = true;

        showProgressBar(false);
    }


    private void initializeChordAdder(){
        View inflatedLayout = getLayoutInflater().inflate(R.layout.chords_list_toolbar,null);
        toolbar.removeAllViews();
        toolbar.addView(inflatedLayout);
        Flubber.with().animation(Flubber.AnimationPreset.FADE_IN).createFor(inflatedLayout).start();

        initChordPanel(inflatedLayout);

        (inflatedLayout.findViewById(R.id.btnBackChords)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeChordMenuToolbar();
            }
        });

        (inflatedLayout.findViewById(R.id.btnExpandChords)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeChordPickerToolbar();
            }
        });
    }

    private class AddChordOnClickListener implements View.OnClickListener{

        boolean clicked = false;
        @Override
        public void onClick(View v) {
            if(isInDeleteMode) {
                isInDeleteMode = false;
                ChordItemAdapter.getTriggerDelBtn(isInDeleteMode);
                adapter.notifyDataSetChanged();
            }
            initializeChordPickerToolbar();
        }
    }


    public void initializeChordMenuToolbar(){
        mode3 = SongEditMode3.CHORD_DRAWER_DOWN;
        View chordEditorLayout = getLayoutInflater().inflate(R.layout.chord_editor_toolbar,null);
        toolbar.removeAllViews();
        toolbar.addView(chordEditorLayout);
        Flubber.with().animation(Flubber.AnimationPreset.FADE_IN).createFor(chordEditorLayout).start();

        btnAddChord = chordEditorLayout.findViewById(R.id.btnAddChord);
        btnDeleteChord = chordEditorLayout.findViewById(R.id.btnDeleteChord);
        btnAddChord.setOnClickListener(new AddChordOnClickListener());
        btnDeleteChord.setOnClickListener(new DeleteChordOnClickListener());
        btnDeleteChord.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(isInDeleteMode) {
                    ChordItemAdapter.getTriggerDelBtn(isInDeleteMode = false);
                    Toast.makeText(getContext(), "Exited delete mode", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
                return true;
            }
        });
    }

    public class DeleteChordOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(!isInDeleteMode) {
                isInDeleteMode = true;
                ChordItemAdapter.getTriggerDelBtn(isInDeleteMode);
                Toast.makeText(getContext(), "In delete mode", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
//                linearLayout.setVisibility(View.INVISIBLE);
//                FloatAdd.setAlpha(127);

        }
    }

    public void notifyDataSetChanged(){
        adapter.notifyDataSetChanged();
    }
    public final class ChoiceTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN){

                ChordItemAdapter getTheChord = new ChordItemAdapter();
                switch(v.getId()) {
                    case R.id.tv_dragChord1:
                        getTheChord.getChord
                                (tv_DragChord1.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                    case R.id.tv_dragChord2:
                        getTheChord.getChord
                                (tv_DragChord2.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                    case R.id.tv_dragChord3:
                        getTheChord.getChord
                                (tv_DragChord3.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                    case R.id.tv_dragChord4:
                        getTheChord.getChord
                                (tv_DragChord4.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                    case R.id.tv_dragChord5:
                        getTheChord.getChord
                                (tv_DragChord5.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                    case R.id.tv_dragChord6:
                        getTheChord.getChord
                                (tv_DragChord6.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                    case R.id.tv_dragChord7:
                        getTheChord.getChord
                                (tv_DragChord7.getText().toString() + get_accidental + get_scale + get_number);
                        break;
                }


                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data,shadowBuilder,v,0);
                return true;
            }
            else {
                return false;
            }
        }
    }
    public void initChordPanel(View view){
        tv_DragChord1 = (TextView) view.findViewById(R.id.tv_dragChord1);
        tv_DragChord2 = (TextView) view.findViewById(R.id.tv_dragChord2);
        tv_DragChord3 = (TextView) view.findViewById(R.id.tv_dragChord3);
        tv_DragChord4 = (TextView) view.findViewById(R.id.tv_dragChord4);
        tv_DragChord5 = (TextView) view.findViewById(R.id.tv_dragChord5);
        tv_DragChord6 = (TextView) view.findViewById(R.id.tv_dragChord6);
        tv_DragChord7 = (TextView) view.findViewById(R.id.tv_dragChord7);

        tv_DragChord1.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord2.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord3.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord4.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord5.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord6.setOnTouchListener(new ChoiceTouchListener());
        tv_DragChord7.setOnTouchListener(new ChoiceTouchListener());

    }

    private void initializeChordPickerToolbar(){
        mode3 = SongEditMode3.CHORD_DRAWER_UP;
        View view = getLayoutInflater().inflate(R.layout.chord_picker_toolbar,null);

        toolbar.removeAllViews();
        toolbar.addView(view);
        Flubber.with().animation(Flubber.AnimationPreset.FADE_IN).createFor(view).start();

        (view.findViewById(R.id.btnBackChords)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeChordMenuToolbar();
            }
        });
        RecyclerView rvAccidental = view.findViewById(R.id.accidental);
        RecyclerView rvScale = view.findViewById(R.id.scale);
        RecyclerView rvNumber = view.findViewById(R.id.number);
        RecyclerView rvChord= view.findViewById(R.id.tvSampleChordPicker);

        PickerItems itemSet = new PickerItems();
        DetailedChord display = new DetailedChord(Letter.C,Accidental.natural, Scale.major, Number.none);

        DetailedChord displayChord = new DetailedChord(Letter.C,Accidental.natural,Scale.major,Number.none);
        List<DetailedChord> filterResults = DetailedChordIndex.getChords(displayChord);

        ChordPickerAdapter adapterAccidental = initializeRecyclerViewPicker(rvAccidental,displayChord, itemSet.pickerAccidentals,filterResults);
        ChordPickerAdapter adapterScale= initializeRecyclerViewPicker(rvScale,displayChord,itemSet.pickerScale,filterResults);
        ChordPickerAdapter adapterNumber = initializeRecyclerViewPicker(rvNumber,displayChord,itemSet.pickerNumber,filterResults);
        chordAdapter = initializeChordRecyclerViewPicker(rvChord,filterResults);
    }

    ChordPickerAdapter chordAdapter;

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onChangeSong(Song item) {
        if(item!=null){
            song = item;
            isLoadedFromDB = song.getUid()!=0;
            if(song.getVerses()!=null) {
                adapter = new VerseItemAdapter(song, this,this);
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
/*                itemTouchHelper = new ItemTouchHelper(new VerseItemTouchHelperCallback(getActivity(),adapter,song.getVerses()).createHelperCallback());
                itemTouchHelper.attachToRecyclerView(recyclerView);*/
                verseItemTouchHelperCallback = new VerseItemTouchHelperCallback(getContext(),adapter);
                itemTouchHelper = new ItemTouchHelper(verseItemTouchHelperCallback.createHelperCallback());
                itemTouchHelper.attachToRecyclerView(recyclerView);
                isSongEdited = false;
            }
        }
        else
            isLoadedFromDB = false;

        showProgressBar(false);
    }

    private class GetSongDetailsDatabaseTask extends AsyncTask<Void,Void,Song> {

        @Override
        protected Song doInBackground(Void... voids) {
            if(song!=null && song.getUid()!=0)
                return database.songDao().getSongWithVerses(song.getUid());

            return null;
        }

        @Override
        protected void onPostExecute(Song s) {
            super.onPostExecute(s);

            if(s!=null) {
                Log.d("SONG","onPostExecute getsongdetails");
                onChangeSong(s);
            }

            showProgressBar(false);
        }
    }

    private void saveSongToDatabase() {
        if (song != null && isSongEdited) {
//            song.setSongTitle(adapter.getSongTitle());
//            song.setArtist(adapter.getSongArtist());
//            song.setTempo(adapter.getSongTempo());
//            song.setKey(adapter.getSongKey());
            song.setDateModified((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date()));
            Log.d("SEARCH","verse size for " + song.getSongTitle() + ": " + song.getVerses().size());

            if (!isLoadedFromDB) {
                (new SongDatabaseUtils.InsertSongDatabaseTask(song)).execute();
                isLoadedFromDB = true;
            }
            else {
                (new SongDatabaseUtils.UpdateSongDatabaseTask(song)).execute();
            }

            Toast.makeText(getContext(), "Saved changes to \'" +
                    song.getSongTitle().substring(0,song.getSongTitle().length()<=20 ? song.getSongTitle().length() : 20) +
                            (song.getSongTitle().length()>25 ? "...\'" : "\'"),
                    Toast.LENGTH_SHORT).show();
            isSongEdited = false;
            mode = SongEditMode.READ_ONLY;
        }
    }

    public ChordPickerAdapter initializeRecyclerViewPicker(final RecyclerView recyclerView, final DetailedChord displayChord, final List items, final List<DetailedChord> results){
        int maxItems = 1;
        int recyclerViewHeight = 0;

        TypedValue tv = new TypedValue();

        if (getActivity().getTheme().resolveAttribute(android.R.attr.listPreferredItemHeight, tv, true)) {

            // Calculating recyclerView height from listItemHeight
            // we are using in our recyclerView list item.
            recyclerViewHeight = maxItems * TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            Log.d("PICKER","it went in here");
        }

        final ChordPickerAdapter adapter = new ChordPickerAdapter(getContext(),items,displayChord, ChordPickerAdapter.PickerType.TYPE_PICKER_SELECTOR);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        layoutParams.height = recyclerViewHeight;
        recyclerView.setLayoutParams(layoutParams);

        final LinearLayoutManager manager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        final LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(manager);
        recyclerView.setOnFlingListener(snapHelper);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(manager);

                    // Getting position of the center/snapped item.
                    int pos = manager.getPosition(centerView);
                    adapter.setSelectedItem(pos);
                    results.clear();
                    results.addAll(DetailedChordIndex.getChords(displayChord));

                    if(chordAdapter!=null)
                        chordAdapter.notifyDataSetChanged();

                    if(results.size()>0)
                        displayChord.setValue(results.get(0));
                    else{
                        displayChord.setValue(null);
                    }

                    Log.d("PICKER", pos + " - " + items.get(pos));
                }
            }
        });
        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                recyclerView.scrollToPosition(0);
                Log.d("PICKER","naglong click");
                return true;
            }
        });

        return adapter;
    }

    public ChordPickerAdapter initializeChordRecyclerViewPicker(final RecyclerView recyclerView, final List<DetailedChord> items){
        final DetailedChord displayChord = new DetailedChord(Letter.C,Accidental.natural,Scale.major,Number.none);

        final ChordPickerAdapter adapter = new ChordPickerAdapter(getContext(),items,displayChord, ChordPickerAdapter.PickerType.TYPE_PICKER_CHORD_DISPLAY);

        final LinearLayoutManager manager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        final LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(manager);
        recyclerView.setOnFlingListener(snapHelper);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(manager);

                    // Getting position of the center/snapped item.
                    int pos = manager.getPosition(centerView);
                    adapter.setSelectedItem(pos);

                }
            }
        });

        return adapter;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isSongEditable(){
        return mode == SongEditMode.EDIT;
    }
}
