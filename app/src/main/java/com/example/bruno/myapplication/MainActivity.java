package com.example.bruno.myapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    ImageButton mainBtn;


    public void fontChange(TextView[] texto){
        final String fontPath = "fonts/Lobster 1.4.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        for(TextView txt: texto ) {
            txt.setTypeface(tf);
        }
    }
    public void fontChange(Button[] texto){
        final String fontPath = "fonts/Lobster 1.4.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        for(TextView txt: texto ) {
            txt.setTypeface(tf);
        }
    }
    public void fontChange(TextView texto){
        final String fontPath = "fonts/Lobster 1.4.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        texto.setTypeface(tf);
    }
    public void fontChange(Button texto){
        final String fontPath = "fonts/Lobster 1.4.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        texto.setTypeface(tf);
    }

    public void toUpdateImage(){
        Intent i = new Intent(this, update_image.class);
        startActivity(i);
    }

    public void aboutUs(View v){
        Intent i = new Intent(this, aboutus.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aboutus = (Button) findViewById(R.id.aboutus);
        fontChange(aboutus);

        mainBtn = (ImageButton) findViewById(R.id.mainBtn);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mainBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button2));
               toUpdateImage();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
