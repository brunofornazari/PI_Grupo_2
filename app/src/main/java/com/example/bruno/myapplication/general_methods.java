package com.example.bruno.myapplication;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by Bruno on 19/04/2015.
 */
public class general_methods extends ActionBarActivity {
    public void fontChange(TextView[] texto){
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
}
