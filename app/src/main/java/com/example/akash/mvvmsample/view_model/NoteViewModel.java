package com.example.akash.mvvmsample.view_model;

import android.app.Application;

import com.example.akash.mvvmsample.model.Note;
import com.example.akash.mvvmsample.repository.NoteRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> liveDataAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository =  new NoteRepository(application);
        liveDataAllNotes = noteRepository.getAllNotes();
    }

    public void insertNote(Note note){
        noteRepository.insert(note);
    }

    public void updateNote(Note note){
        noteRepository.update(note);
    }

    public void deleteNote(Note note){
        noteRepository.delete(note);
    }

    public void deleteAllNotes(){
        noteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getLiveDataAllNotes(){
        return liveDataAllNotes;
    }



}
