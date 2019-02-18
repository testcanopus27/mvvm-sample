package com.example.akash.mvvmsample.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akash.mvvmsample.R;
import com.example.akash.mvvmsample.model.Note;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes = new ArrayList<>();
    onItemClickListener onItemClickListener;


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent ,false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        Note currentNote = notes.get(position);
        holder.title.setText(currentNote.getTittle());
        holder.Desc.setText(currentNote.getDescription());
        holder.priority.setText(currentNote.getPriority()+"");
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position){
        return notes.get(position);
    }
    class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView Desc;
        private TextView priority;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            Desc = itemView.findViewById(R.id.description);
            priority = itemView.findViewById(R.id.priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener!=null && getAdapterPosition()!=RecyclerView.NO_POSITION)
                    onItemClickListener.OnItemClick(notes.get(getAdapterPosition()));
                }
            });

        }

    }

    public interface onItemClickListener{

        void OnItemClick(Note note);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener){

        this.onItemClickListener = onItemClickListener;
    }
}
