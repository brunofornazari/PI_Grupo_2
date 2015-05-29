package com.example.bruno.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class aboutus extends ActionBarActivity {
    Fonte fonte = new Fonte(this);

    public void toHome(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void toFinal(View v){
        Intent i = new Intent(this, image_done.class);
        startActivity(i);
    }

    public void testData(View v){
        final Bundle data = getIntent().getExtras();
        if(data == null){
            toHome(v);
        } else {
            toFinal(v);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Button back;

        TextView bruno, nathan, roberto, btn;
        btn = (TextView)findViewById(R.id.backbutton);
        bruno = (TextView) findViewById(R.id.bruno);
        nathan = (TextView) findViewById(R.id.nathan);
        roberto = (TextView) findViewById(R.id.roberto);
        TextView[] textos = {bruno, nathan, roberto, btn};
        fonte.fontChange(textos);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aboutus, menu);
        return true;
    }

}
