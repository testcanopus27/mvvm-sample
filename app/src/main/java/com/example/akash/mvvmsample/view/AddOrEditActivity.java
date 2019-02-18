package com.example.akash.mvvmsample.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.akash.mvvmsample.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddOrEditActivity extends AppCompatActivity {

    private EditText edTitle;
    private EditText edDesc;
    private NumberPicker nmPicker;
    private int numberPicked=0;
    private int id=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edDesc = findViewById(R.id.ed_desc);
        edTitle = findViewById(R.id.ed_ed_Title);
        nmPicker = findViewById(R.id.numberpicker);

        Intent intent = getIntent();
        if (intent.hasExtra("title")){
            setTitle("Edit Actibity");
            edDesc.setText(intent.getStringExtra("title"));
            edTitle.setText(intent.getStringExtra("desc"));
            nmPicker.setValue(intent.getIntExtra("nm",0));
            id = intent.getIntExtra("id",-1);

        }else {
            setTitle("Add Note");
        }


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
      //  setTitle("Add Note");


        nmPicker.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        nmPicker.setMaxValue(10);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        nmPicker.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        nmPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                //tv.setText("Selected Number : " + newVal);
                numberPicked = newVal;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.help_btn:
                saveNote();
                break;

                default:
                    super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote(){
        String title = edTitle.getText().toString().trim();
        String desc = edDesc.getText().toString().trim();
        int number = nmPicker.getValue();

        if (title.isEmpty() || desc.isEmpty()){
            Toast.makeText(this, "Please enter text", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this , MainActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("desc", desc);
        intent.putExtra("nm", number);
        intent.putExtra("id", id);
        setResult(RESULT_OK, intent);
        finish();

    }
}
