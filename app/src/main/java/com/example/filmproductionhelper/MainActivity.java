package com.example.filmproductionhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;


    //have button go to new activity: https://www.youtube.com/watch?v=bgIUdb-7Rqo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button = findViewById(R.id.testfilmbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTestFilm();
            }
        });

    }

    public void openTestFilm() {
        Intent intent = new Intent(this, TestFilm.class);
        startActivity(intent);

    }
}
