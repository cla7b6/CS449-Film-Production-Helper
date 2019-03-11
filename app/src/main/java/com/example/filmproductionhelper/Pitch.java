package com.example.filmproductionhelper;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//code learned from: https://www.youtube.com/watch?v=fJEFZ6EOM9o

public class Pitch extends AppCompatActivity {

        //state all variables worked with
        private EditText editText;
        private Button saveButton;
        private String text;
        public static final String SHARED_PREFS = "sharedPrefs";
        public static final String TEXT = "text";

        @Override //connect variables with XML
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pitch);

            editText = findViewById(R.id.treatment_edittext);
            //applyTextButton = findViewById(R.id.apply_b);
            saveButton = findViewById(R.id.save_b);

            /*applyTextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText(editText.getText().toString());
                }
            });*/

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
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(TEXT, editText.getText().toString());
            editor.apply();

            Toast.makeText(this, "Data saved.", Toast.LENGTH_SHORT).show();
        }

        //Load Button function - uses SharedPreferences method to load previous edit
        public void loadData() {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            text = sharedPreferences.getString(TEXT, "Insert text here.");
        }

        //Update Edit Box - sets text
        public void updateViews() {
            editText.setText(text);
        }
    }
