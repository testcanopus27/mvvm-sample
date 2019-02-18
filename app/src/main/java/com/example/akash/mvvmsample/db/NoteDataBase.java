package com.example.akash.mvvmsample.db;

import android.content.Context;
import android.os.AsyncTask;

import com.example.akash.mvvmsample.NoteDao;
import com.example.akash.mvvmsample.model.Note;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    private static  NoteDataBase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDataBase getInstance(Context context) {

        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDataBase.class,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomDatabaseCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomDatabaseCallback =  new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void ,Void , Void>{

        private NoteDao noteDao;

        PopulateDBAsyncTask (NoteDataBase noteDataBase){
            noteDao = noteDataBase.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("title1" , "description1" ,1));
            noteDao.insert(new Note("title2" , "description2" ,2));
            noteDao.insert(new Note("title3" , "description3" ,3));
            noteDao.insert(new Note("title4" , "description4" ,4));
            noteDao.insert(new Note("title5" , "description5" ,5));
            return null;
        }
    }
}
