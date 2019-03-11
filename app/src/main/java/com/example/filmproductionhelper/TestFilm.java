package com.example.filmproductionhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//how to push code to github repository: https://www.youtube.com/watch?v=-dAr6VnmomM
//for future feature(s): https://www.youtube.com/watch?v=EcfUkjlL9RI

public class TestFilm extends AppCompatActivity {
    private Button button;
    @Override

    //make all buttons clickable
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_film);

        button = findViewById(R.id.pitchbutton); //pitch button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPitch();
            }
        });

        button = findViewById(R.id.treatmentbutton); //treatment button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTreatment();
            }
        });

        button = findViewById(R.id.shotlbutton); //shot list button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShotList();
            }
        });

        button = findViewById(R.id.materiallbutton); //material list button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaterialList();
            }
        });

        button = findViewById(R.id.materialbbutton); //material basics button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaterialBasics();
            }
        });

        button = findViewById(R.id.storyboardbutton); //storyboard button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoryboard();
            }
        });

        button = findViewById(R.id.callsheetbutton); //call sheet button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCallSheet();
            }
        });

        button = findViewById(R.id.clapperbutton); //clapper button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClapper();
            }
        });
    }

    //open Pitch
    public void openPitch(){
        Intent intent = new Intent(this, Pitch.class);
        startActivity(intent);
    }

    //open Treatment
    public void openTreatment(){
        Intent intent = new Intent(this, Treatment.class);
        startActivity(intent);
    }

    //open Shot List
    public void openShotList(){
        Intent intent = new Intent(this, ShotList.class);
        startActivity(intent);
    }

    //open Material List
    public void openMaterialList(){
        Intent intent = new Intent(this, MaterialList.class);
        startActivity(intent);
    }

    //open Material Basics
    public void openMaterialBasics(){
        Intent intent = new Intent(this, MaterialBasics.class);
        startActivity(intent);
    }

    //open Storyboard
    public void openStoryboard(){
        Intent intent = new Intent(this, MaterialBasics.class);
        startActivity(intent);
    }

    //open Call Sheet
    public void openCallSheet(){
        Intent intent = new Intent(this, CallSheet.class);
        startActivity(intent);
    }

    //open Clapper
    public void openClapper(){
        Intent intent = new Intent(this, Clapper.class);
        startActivity(intent);
    }
}
