package com.example.marvin.kuwerdas.db;

import android.os.AsyncTask;

import com.example.marvin.kuwerdas.song.model.Line;
import com.example.marvin.kuwerdas.song.model.Song;
import com.example.marvin.kuwerdas.song.model.Verse;
import com.example.marvin.kuwerdas.song.util.SongUtil;

public class SongDatabaseUtils {
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

    public static class UpdateTempoDatabaseTask extends AsyncTask<Void,Void,Void> {
        Song song;

        public UpdateTempoDatabaseTask(Song song){
            this.song = song;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            database.songDao().updateTempo(song.getUid(),song.getTempo());

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
        }
    }


    public static class DeleteSongsDatabaseTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.songDao().deleteAllSongs();
            return null;
        }
    }

    public static void insertSampleSongs(){
        new DeleteSongsDatabaseTask().execute();
        new SongDatabaseUtils.InsertSongDatabaseTask(SongUtil.asSong("Mundo","IV of Spades","San darating ang mga salita\n" +
                "Na nanggagaling sa aming dalawa\n" +
                "Kung lumisan ka, wag naman sana\n" +
                "Ika'y kumapit na, nang di makawala\n" +
                "\n" +
                "Aking sinta, ikaw na ang tahanan at mundo\n" +
                "Sa pagbalik, mananatili na sa piling mo\n" +
                "Mundo’y magiging ikaw\n" +
                "\n" +
                "Wag mag-alala kung nahihirapan ka\n" +
                "Halika na, sumama ka\n" +
                "Pagmasdan ang mga tala\n" +
                "\n" +
                "Aking sinta, ikaw na ang tahanan at mundo\n" +
                "Sa pagbalik, mananatili na sa piling mo\n" +
                "Mundo'y magiging ikaw\n" +
                "\n" +
                "Limutin na ang mundo\n" +
                "Nang magkasama tayo\n" +
                "Sunod sa bawat galaw\n" +
                "Hindi na maliligaw\n")).execute();


        new SongDatabaseUtils.InsertSongDatabaseTask(SongUtil.asSong("Ligaya","Eraserheads","Ilang awit pa ba ang aawitin, o giliw ko?\n" +
                "Ilang ulit pa ba ang uulitin, o giliw ko?\n" +
                "tatlong oras na akong nagpapacute sa iyo\n" +
                "di mo man lang napapansin ang bagong t-shirt ko\n" +
                "\n" +
                "Ilang isaw pa ba ang kakain, o giliw ko?\n" +
                "Ilang tanzan pa ba ang iipunin, o giliw ko?\n" +
                "gagawin ko ang lahat pati ang thesis mo\n" +
                "wag mo lang ipagkait ang hinahanap ko\n" +
                "\n" +
                "Sagutin mo lang ako, aking sinta'y walang humpay, na ligaya\n" +
                "at asahang iibigin ka, sa tanghali, sa gabi at umaga\n" +
                "Wag ka sanang magtanong at magduda\n" +
                "dahil ang puso ko'y walang pangamba\n" +
                "na tayo'y mabubuhay na tahimik at buong ligaya\n" +
                "\n" +
                "Ilang ahit pa ba ang aahitin, o giliw ko?\n" +
                "Ilang hirit pa ba ang hihiritin, o giliw ko?\n" +
                "di naman ako mangyakis tulad nang iba\n" +
                "pinapangako ko sa iyo na igagalang ka\n" +
                "\n" +
                "Aasahang iibigin ka, sa tanghali, sa gabi at umaga\n" +
                "Wag ka sanang magtanong at magduda\n" +
                "dahil ang puso ko'y walang pangamba\n" +
                "na tayo'y mabubuhay na tahimik at buong Ligaya")).execute();

        new SongDatabaseUtils.InsertSongDatabaseTask(SongUtil.asSong("Kisapmata","Rivermaya","Nitong umaga lang, Pagka lambing-lambing\n" +
                "Ng iyong mga matang, Hayup kung tumingin.\n" +
                "Nitong umaga lang, Pagka galing-galing\n" +
                "Ng iyong sumpang, Walang aawat sa atin.\n" +
                "\n" +
                "O kay bilis namang maglaho ng Pag-ibig mo sinta,\n" +
                "Daig mo pa ang isang kisapmata.\n" +
                "Kaninay nariyan lang o ba't Bigla namang nawala.\n" +
                "Daig mo pa ang isang kisapmata.\n" +
                "\n" +
                "Kani-kanina lang, Pagka ganda-ganda\n" +
                "Ng pagkasabi mong Sana'y tayo na nga.\n" +
                "Kani-kanina lang, Pagka saya-saya\n" +
                "Ng buhay kong Bigla na lamang nagiba.\n" +
                "\n" +
                "O kay bilis namang maglaho ng Pag-ibig mo sinta,\n" +
                "Daig mo pa ang isang kisapmata.\n" +
                "Kaninay nariyan lang o ba't Bigla namang nawala.\n" +
                "Daig mo pa ang isang kisapmata.")).execute();

        new SongDatabaseUtils.InsertSongDatabaseTask(SongUtil.asSong("Harana","Parokya ni Edgar","Uso pa ba ang harana\n" +
                "Marahil ikaw ay nagtataka\n" +
                "Sino ba 'tong mukhang gago\n" +
                "Nagkandarapa sa pagkanta\n" +
                "At nasisintunado sa kaba\n" +
                "Mayron pang dalang mga rosas\n" +
                "Suot nama'y maong na kupas\n" +
                "At nariyan pa ang barkada\n" +
                "Naka-porma naka-barong\n" +
                "Sa awiting daig pa ang minus one\n" +
                "At sing-along\n" +
                "\n" +
                "Puno ang langit ng bituin\n" +
                "At kay lamig pa ng hangin\n" +
                "Sa 'yong tingin ako'y nababaliw giliw\n" +
                "At sa awitin kong ito\n" +
                "Sana'y maibigan mo\n" +
                "Ibubuhos ko ang buong puso ko\n" +
                "Sa isang munting harana\n" +
                "Para sa'yo\n" +
                "\n" +
                "Di ba parang isang sine\n" +
                "Isang pelikulang romantiko\n" +
                "Di ba't ikaw ang bidang artista\n" +
                "At ako ang 'yong leading man\n" +
                "Sa istoryang nagwawakas\n" +
                "Sa pag-ibig na wagas\n" +
                "\n" +
                "Puno ang langit ng bituin\n" +
                "At kay lamig pa ng hangin\n" +
                "Sa 'yong tingin ako'y nababaliw giliw\n" +
                "At sa awitin kong ito\n" +
                "Sana'y maibigan mo\n" +
                "Ibubuhos ko ang buong puso ko\n" +
                "Sa isang munting harana\n" +
                "Para sa 'yo")).execute();

        new SongDatabaseUtils.InsertSongDatabaseTask(SongUtil.asSong("Sa Ngalan ng Pag-ibig","December Avenue","Uso pa ba ang harana\n" +
                "Marahil ikaw ay nagtataka\n" +
                "Sino ba 'tong mukhang gago\n" +
                "Nagkandarapa sa pagkanta\n" +
                "At nasisintunado sa kaba\n" +
                "Mayron pang dalang mga rosas\n" +
                "Suot nama'y maong na kupas\n" +
                "At nariyan pa ang barkada\n" +
                "Naka-porma naka-barong\n" +
                "Sa awiting daig pa ang minus one\n" +
                "At sing-along\n" +
                "\n" +
                "Puno ang langit ng bituin\n" +
                "At kay lamig pa ng hangin\n" +
                "Sa 'yong tingin ako'y nababaliw giliw\n" +
                "At sa awitin kong ito\n" +
                "Sana'y maibigan mo\n" +
                "Ibubuhos ko ang buong puso ko\n" +
                "Sa isang munting harana\n" +
                "Para sa'yo\n" +
                "\n" +
                "Di ba parang isang sine\n" +
                "Isang pelikulang romantiko\n" +
                "Di ba't ikaw ang bidang artista\n" +
                "At ako ang 'yong leading man\n" +
                "Sa istoryang nagwawakas\n" +
                "Sa pag-ibig na wagas\n" +
                "\n" +
                "Puno ang langit ng bituin\n" +
                "At kay lamig pa ng hangin\n" +
                "Sa 'yong tingin ako'y nababaliw giliw\n" +
                "At sa awitin kong ito\n" +
                "Sana'y maibigan mo\n" +
                "Ibubuhos ko ang buong puso ko\n" +
                "Sa isang munting harana\n" +
                "Para sa 'yo")).execute();
    }
}
