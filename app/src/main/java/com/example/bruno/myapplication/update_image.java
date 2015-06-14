package com.example.bruno.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.*;
import org.opencv.android.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.opencv.android.OpenCVLoader;
import org.opencv.*;
import static com.example.bruno.myapplication.Convolucao.computeConvolution3x3;
import static org.opencv.highgui.Highgui.imread;
import static org.opencv.imgproc.Imgproc.calcHist;


public class update_image extends ActionBarActivity {
    static
    {
        System.loadLibrary("opencv_java");
        if(!OpenCVLoader.initDebug()) {
            Log.d("ERROR", "Unable to load OpenCV");
        } else {
            Log.d("SUCCESS", "OpenCV loaded");
        }
    }
    static FileOutputStream out = null;
    static final int REQUEST_IMAGE_CAMERA = 1;
    static ImageView imageview;
    static Bitmap btimg;
    static Bitmap original;
    static Boolean seekBarStatus = false;
    private static final String TAG = "MyActivity";

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    System.loadLibrary("ScannerApp");
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

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

            if(requestCode == REQUEST_IMAGE_CAMERA) {
                if(data != null){
                    Bundle extras = data.getExtras();
                    Bitmap photo = (Bitmap) extras.get("data");

                    original = photo;
                    btimg = photo;
                    imageview.setImageBitmap(original);
                } else {
                    backToHome(getCurrentFocus());
                }

            }

    }

    public void toFinishScreen(View v) throws IOException {
        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream fOut = null;
        File file = new File(path, btimg.toString() + ".jpg"); // the File to save to
        fOut = new FileOutputStream(file);

        Bitmap pictureBitmap = btimg; // obtaining the Bitmap
        pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
        fOut.flush();
        fOut.close(); // do not forget to close the stream

        MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());

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
        //OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_6, this, mLoaderCallback);
        imageview = (ImageView) findViewById(R.id.image_view_result);
        launchCamera(new View(this));
        FrameLayout invert = (FrameLayout)findViewById(R.id.ivtImage);
        FrameLayout mono = (FrameLayout)findViewById(R.id.monoChrome);
        final FrameLayout origin = (FrameLayout)findViewById(R.id.original);
        FrameLayout decH = (FrameLayout)findViewById(R.id.decHoriz);
        FrameLayout decV = (FrameLayout)findViewById(R.id.decVert);
        FrameLayout lap = (FrameLayout)findViewById(R.id.laplas);
        FrameLayout sgray = (FrameLayout)findViewById(R.id.seekgray);
        btimg = original;
        origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btimg = original;
                imageview.setImageBitmap(btimg);
            }
        });

        invert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invertImage(original);
            }
        });

        mono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBlackAndWhite(original);
            }
        });

        decH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deteccaoH(original);
            }
        });

        decV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deteccaoV(original);
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laplaciano(original);
            }
        });

        sgray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mat imgMat = new Mat();
                Utils.bitmapToMat(btimg, imgMat);
                Imgproc.cvtColor(imgMat, imgMat, Imgproc.COLOR_RGB2GRAY);

                //Calculate histogram
                java.util.List<Mat> matList = new LinkedList<Mat>();
                matList.add(imgMat);
                Mat histogram = new Mat();
                MatOfFloat ranges = new MatOfFloat(0, 256);
                MatOfInt histSize = new MatOfInt(255);
                Imgproc.calcHist(
                        matList,
                        new MatOfInt(0),
                        new Mat(),
                        histogram,
                        histSize,
                        ranges);

// Create space for histogram image
                Mat histImage = Mat.zeros(100, (int) histSize.get(0, 0)[0], CvType.CV_8UC1);
// Normalize histogram
                Core.normalize(histogram, histogram, 1, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
                for (int i = 0; i < (int) histSize.get(0, 0)[0]; i++) {
                    Core.line(
                            histImage,
                            new org.opencv.core.Point(i, histImage.rows()),
                            new org.opencv.core.Point(i, histImage.rows() - Math.round(histogram.get(i, 0)[0])),
                            new Scalar(255, 255, 255),
                            1, 8, 0);
                }
                Bitmap bm = Bitmap.createBitmap(histImage.cols(), histImage.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(histImage, bm);

                imageview.setImageBitmap(bm);
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
    public static void deteccaoH(Bitmap src) {
        double[][] SharpConfig = new double[][] {
                { 1, 2, 1},
                { 0, 0, 0 },
                { -1, -2, -1}
        };
        Convolucao convMatrix = new Convolucao(3);
        convMatrix.applyConfig(SharpConfig);

        btimg = Convolucao.computeConvolution3x3(src, convMatrix);
        imageview.setImageBitmap(btimg);
    }
    public static void deteccaoV(Bitmap src) {
        double[][] SharpConfig = new double[][] {
                { -1, 0, 1},
                { -2, 0, 2},
                { -1, 0, 1}
        };
        Convolucao convMatrix = new Convolucao(3);
        convMatrix.applyConfig(SharpConfig);

        btimg = Convolucao.computeConvolution3x3(src, convMatrix);
        imageview.setImageBitmap(btimg);
    }

    public static void laplaciano(Bitmap src) {
        double[][] SharpConfig = new double[][] {
                { 0, -1, 0},
                { -1, 4 , -1},
                { 0, -1, 0}
        };
        Convolucao convMatrix = new Convolucao(3);
        convMatrix.applyConfig(SharpConfig);

        btimg = Convolucao.computeConvolution3x3(src, convMatrix);
        imageview.setImageBitmap(btimg);
    }

    public static ArrayList<int[]> imageHistogram(Bitmap input) {

        int[] rhistogram = new int[256];
        int[] ghistogram = new int[256];
        int[] bhistogram = new int[256];

        for(int i=0; i<rhistogram.length; i++) rhistogram[i] = 0;
        for(int i=0; i<ghistogram.length; i++) ghistogram[i] = 0;
        for(int i=0; i<bhistogram.length; i++) bhistogram[i] = 0;

        for(int i=0; i<input.getWidth(); i++) {
            for(int j=0; j<input.getHeight(); j++) {

                int red = Color.red(input.getPixel(i, j));
                int green = Color.green(input.getPixel (i, j));
                int blue = Color.blue(input.getPixel (i, j));

                // Increase the values of colors
                rhistogram[red]++; ghistogram[green]++; bhistogram[blue]++;

            }
        }

        ArrayList<int[]> hist = new ArrayList<int[]>();
        hist.add(rhistogram);
        hist.add(ghistogram);
        hist.add(bhistogram);

        return hist;

    }

    private static ArrayList<int[]> histogramEqualizationLUT(Bitmap input) {

        // Get an image histogram - calculated values by R, G, B channels
        ArrayList<int[]> imageHist = imageHistogram(input);

        // Create the lookup table
        ArrayList<int[]> imageLUT = new ArrayList<int[]>();

        // Fill the lookup table
        int[] rhistogram = new int[256];
        int[] ghistogram = new int[256];
        int[] bhistogram = new int[256];

        for(int i=0; i<rhistogram.length; i++) rhistogram[i] = 0;
        for(int i=0; i<ghistogram.length; i++) ghistogram[i] = 0;
        for(int i=0; i<bhistogram.length; i++) bhistogram[i] = 0;

        long sumr = 0;
        long sumg = 0;
        long sumb = 0;

        // Calculate the scale factor
        float scale_factor = (float) (255.0 / (input.getWidth() * input.getHeight()));

        for(int i=0; i<rhistogram.length; i++) {
            sumr += imageHist.get(0)[i];
            int valr = (int) (sumr * scale_factor);
            if(valr > 255) {
                rhistogram[i] = 255;
            }
            else rhistogram[i] = valr;

            sumg += imageHist.get(1)[i];
            int valg = (int) (sumg * scale_factor);
            if(valg > 255) {
                ghistogram[i] = 255;
            }
            else ghistogram[i] = valg;

            sumb += imageHist.get(2)[i];
            int valb = (int) (sumb * scale_factor);
            if(valb > 255) {
                bhistogram[i] = 255;
            }
            else bhistogram[i] = valb;
        }

        imageLUT.add(rhistogram);
        imageLUT.add(ghistogram);
        imageLUT.add(bhistogram);

        return imageLUT;

    }
}
