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
            Bitmap photo = (Bitmap) extras.get("data");
            imageview.setImageBitmap(photo);
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
        Drawable imgsrc = getResources().getDrawable(R.drawable.effect_icon);
        btimg = ((BitmapDrawable) imgsrc).getBitmap();
        imageview.setImageBitmap(btimg);

        FrameLayout invert = (FrameLayout)findViewById(R.id.ivtImage);
        invert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invertImage(btimg);
            }
        });
        //launchCamera(new View(this));
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

    public static void createBlackAndWhite(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);


                bmOut.setPixel(x, y, Color.argb(A, gray, gray, gray));
            }
        }
        btimg = bmOut;
        imageview.setImageBitmap(btimg);
    }




}
