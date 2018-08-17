package com.example.marvin.kuwerdas.db;

import android.os.AsyncTask;

import com.example.marvin.kuwerdas.song.model.Line;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.model.Verse;

public class DatabaseUtils {
    private static SongDatabase database = null;

    public static void initialize(SongDatabase db){
        database = db;
    }

    public static class InsertSongDatabaseTask extends AsyncTask<Void,Void,Integer> {
        Song song;

        public InsertSongDatabaseTask(Song song){
            this.song = song;
        }


        @Override
        protected Integer doInBackground(Void... voids) {
            if(database!=null)
                return database.songDao().insertSong(song);

            return null;
        }

        @Override
        protected void onPostExecute(Integer id) {
        }
    }

    public static class DeleteSongFromDatabaseTask extends AsyncTask<Void,Void,Void> {

        Song song;

        public DeleteSongFromDatabaseTask(Song song) {
            this.song = song;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(database!=null)
                database.songDao().deleteSong(song);

            return null;
        }
    }

    public static class DeleteVerseFromDatabaseTask extends AsyncTask<Void,Void,Void> {

        Verse verse;

        public DeleteVerseFromDatabaseTask(Verse verse) {
            this.verse = verse;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(database!=null)
                database.songDao().deleteVerse(verse);

            return null;
        }
    }

    public static class DeleteLineFromDatabaseTask extends AsyncTask<Void,Void,Void> {

        Line line;

        public DeleteLineFromDatabaseTask(Line line) {
            this.line = line;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(database!=null)
                database.songDao().deleteLine(line);

            return null;
        }
    }

    public static class UpdateSongDatabaseTask extends AsyncTask<Void,Void,Void> {
        Song song;

        public UpdateSongDatabaseTask(Song song){
            this.song = song;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            database.songDao().updateSong(song);

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
        }
    }
}
