package com.example.akash.mvvmsample.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.akash.mvvmsample.R;
import com.example.akash.mvvmsample.model.Note;
import com.example.akash.mvvmsample.view_model.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private FloatingActionButton flActionBtn;
    private NoteAdapter noteAdapter;
    private RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv = findViewById(R.id.recycler_view);
        flActionBtn = findViewById(R.id.floating_button);
        noteAdapter = new NoteAdapter();

        flActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent =  new Intent(MainActivity.this, AddOrEditActivity.class);
                startActivityForResult(intent ,1 );
            }
        });

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getLiveDataAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Toast.makeText(MainActivity.this, "onDataChanged", Toast.LENGTH_SHORT).show();
                noteAdapter.setNotes(notes);
            }
        });
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(noteAdapter);

        noteAdapter.setOnItemClickListener(new NoteAdapter.onItemClickListener() {
            @Override
            public void OnItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this , AddOrEditActivity.class);
                intent.putExtra("title", note.getTittle());
                intent.putExtra("desc", note.getDescription());
                intent.putExtra("nm", note.getPriority());
                intent.putExtra("id", note.getId());
                startActivityForResult(intent, 2 );
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                Note note = noteAdapter.getNoteAt(viewHolder.getAdapterPosition());
                noteViewModel.deleteNote(note);
            }
        }).attachToRecyclerView(rcv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK){
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("desc");
            int number = data.getIntExtra("nm",0);

            Note note = new Note(title , desc, number);
            Toast.makeText(this, "Note Added !", Toast.LENGTH_SHORT).show();
            noteViewModel.insertNote(note);

        }else if (requestCode==2 && resultCode==RESULT_OK){
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("desc");
            int number = data.getIntExtra("nm",0);
            int id = data.getIntExtra("id",-1);

            if (id==-1){
                Toast.makeText(this, "Something went wrong !", Toast.LENGTH_SHORT).show();

            }else {
                Note note = new Note(title , desc, number);
                note.setId(id);
                noteViewModel.updateNote(note);
                Toast.makeText(this, "Note Updated !", Toast.LENGTH_SHORT).show();

            }
        }

        else {
            Toast.makeText(this, "Note not added !", Toast.LENGTH_SHORT).show();

        }
    } @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.deleteall, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all:
                noteViewModel.deleteAllNotes();
                break;

            default:
                super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }




}
