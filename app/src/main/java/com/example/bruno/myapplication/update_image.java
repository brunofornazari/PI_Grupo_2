package com.example.bruno.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


public class update_image extends ActionBarActivity {

    final static int REQUEST_IMAGE_CAMERA = 1;
    static ImageView imageview;
    static Bitmap btimg;
    public void backToHome(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void launchCamera(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAMERA && requestCode == RESULT_OK){
            Bundle extras = data.getExtras();
            btimg = (Bitmap) extras.get("data");
            imageview.setImageBitmap(btimg);
        }
    }

    public void toFinishScreen(View v){
        Intent i = new Intent(this, image_done.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        btimg.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        i.putExtra("image", byteArray);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image);

        imageview = (ImageView) findViewById(R.id.image_view_result);
        //Drawable imgsrc = getResources().getDrawable(R.drawable.effect_icon);
        //btimg = ((BitmapDrawable) imgsrc).getBitmap();
        //imageview.setImageBitmap(btimg);
        launchCamera(new View(this));
        FrameLayout invert = (FrameLayout)findViewById(R.id.ivtImage);
        invert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invertImage(btimg);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_update_image_2, menu);
        return true;
    }

    //FILTROS DA FOTO
    public static void invertImage(Bitmap original){
        Bitmap finalImage = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());

        int A, R, G, B;
        int pixelColor;
        int h = original.getHeight();
        int w = original.getWidth();

        for(int y = 0 ; y < h; y++){
            for(int x = 0; x < w; x++){
                pixelColor = original.getPixel(x, y);
                A = Color.alpha(pixelColor);
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);

                finalImage.setPixel(x, y, Color.argb(A,R,G,B));
            }
        }
        btimg = finalImage;
        imageview.setImageBitmap(btimg);
    }
}
