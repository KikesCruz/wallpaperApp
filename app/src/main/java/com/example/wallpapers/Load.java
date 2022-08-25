package com.example.wallpapers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Load extends AppCompatActivity {
    TextView app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load);

        //Cambio de fuente para el titulo
       String font_title = "fonts/dinosaur.ttf";
       Typeface tf = Typeface.createFromAsset(Load.this.getAssets(), font_title);

       app_name = findViewById(R.id.app_name);

        final int time = 3000; //tiempo de duración

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //codigo que se ejecutara para load
                Intent intent = new Intent(Load.this, MainActivityAdmin.class);
                startActivity(intent);
                finish();
            }
        }, time);

       app_name.setTypeface(tf);
    }
}