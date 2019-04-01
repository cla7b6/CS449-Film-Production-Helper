package com.example.filmproductionhelper;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//code learned from: https://www.youtube.com/watch?v=fJEFZ6EOM9o
//distinguishing SharedPreferences: https://stackoverflow.com/questions/11747687/sharedpreferences-wont-share-between-activities

public class Treatment extends AppCompatActivity {

    //state all variables worked with
    private EditText editText;
    private Button saveButton;
    private String text;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    @Override //connect variables with XML
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        editText = findViewById(R.id.pitch_edittext);
        saveButton = findViewById(R.id.treatment_saveb);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(editText.getText().toString());
                saveData();
            }
        });
        loadData();
        updateViews();
    }

    //Save Button function - uses SharedPreferences method to save text, apply to editor, and notify user if save
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("TSHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, editText.getText().toString());
        editor.apply();

        Toast.makeText(this, "Data saved.", Toast.LENGTH_SHORT).show();
    }

    //Load Button function - uses SharedPreferences method to load previous edit
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("TSHARED_PREFS", MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "Insert text here.");
    }

    //Update Edit Box - sets text
    public void updateViews() {
        editText.setText(text);
    }
}
