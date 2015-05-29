package com.example.bruno.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class image_done extends ActionBarActivity {

    TextView finalMessage, aboutUs, startAgain;
    static ImageView img;
    Fonte fonte = new Fonte(this);
    public void toAboutUs(View view){
        Intent i = new Intent(this, aboutus.class);
        i.putExtra("final", true);
        startActivity(i);
    }

    public Bitmap testData(View v){
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap btm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return btm;
        //img = (ImageView) findViewById(R.id.imgfinal);
        //img.setImageBitmap((Bitmap) data.get("image"));

    }

    public void backToHome(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_done);
        img = (ImageView) findViewById(R.id.imgfinal);
        img.setImageBitmap(testData(new View(this)));

        finalMessage = (TextView) findViewById(R.id.finalMessage);
        aboutUs = (TextView) findViewById(R.id.aboutus);
        startAgain = (TextView) findViewById(R.id.startAgain);
        TextView[] texto = {finalMessage, aboutUs, startAgain};
        fonte.fontChange(texto);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_done, menu);
        return true;
    }


}
