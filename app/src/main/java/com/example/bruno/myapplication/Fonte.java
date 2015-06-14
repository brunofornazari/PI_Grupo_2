package com.example.bruno.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Dell on 27/05/2015.
 */
public class Fonte {
    Context c;

    public Fonte(Context contexto){
        c = contexto;
    }

    public void fontChange(TextView[] texto){
        final String fontPath = "fonts/Lobster 1.4.otf";
        Typeface tf = Typeface.createFromAsset(c.getAssets(), fontPath);
        for(TextView txt: texto ) {
            txt.setTypeface(tf);
        }
    }

    public void fontChange(TextView texto){
        final String fontPath = "fonts/Lobster 1.4.otf";
        Typeface tf = Typeface.createFromAsset(c.getAssets(), fontPath);
        texto.setTypeface(tf);
    }

    public void fontChange(Button[] texto){
        final String fontPath = "fonts/Lobster 1.4.otf";
        Typeface tf = Typeface.createFromAsset(c.getAssets(), fontPath);
        for(TextView txt: texto ) {
            txt.setTypeface(tf);
        }
    }

    public void fontChange(Button texto){
        final String fontPath = "fonts/Lobster 1.4.otf";
        Typeface tf = Typeface.createFromAsset(c.getAssets(), fontPath);
        texto.setTypeface(tf);
    }
}
