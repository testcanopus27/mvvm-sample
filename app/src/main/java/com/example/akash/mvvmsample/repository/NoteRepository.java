package com.example.akash.mvvmsample.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.akash.mvvmsample.NoteDao;
import com.example.akash.mvvmsample.db.NoteDataBase;
import com.example.akash.mvvmsample.model.Note;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NoteDataBase noteDataBase  = NoteDataBase.getInstance(application);
        noteDao = noteDataBase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note){

        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);

    }

    public void delete(Note note){
        new deleteNoteAsyncTask(noteDao).execute(note);

    }

    public void deleteAllNotes(){
        new deleteAllNoteAsyncTask(noteDao).execute();

    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static  class InsertNoteAsyncTask extends AsyncTask<Note , Void , Void>{

        private  NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
    private static  class UpdateNoteAsyncTask extends AsyncTask<Note , Void , Void>{

        private  NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    private static  class deleteNoteAsyncTask extends AsyncTask<Note , Void , Void>{

        private  NoteDao noteDao;

        private deleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static  class deleteAllNoteAsyncTask extends AsyncTask<Void , Void , Void>{

        private  NoteDao noteDao;

        private deleteAllNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }


}
