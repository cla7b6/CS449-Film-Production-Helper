package com.example.filmproductionhelper;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//sound implementation from: https://www.youtube.com/watch?time_continue=87&v=9oj4f8721LM
//image change: https://www.youtube.com/watch?v=EMxYdhoRqlg
public class Clapper extends AppCompatActivity {

    private ImageView imgview;
    private int current_image;
    int[] images = {R.drawable.clapopen, R.drawable.clapclosed};
    private Button buttonsbm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clapper);
        buttonclick();
    }

    public void buttonclick(){
        final MediaPlayer sound = MediaPlayer.create(this,R.raw.clapper);
        imgview = findViewById(R.id.openclapper);
        buttonsbm =findViewById(R.id.clapbutton);
        buttonsbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        sound.start();
                        current_image++;
                        current_image = current_image % images.length;
                        imgview.setImageResource(images[current_image]);
                    }
                }
        );
    }
}
