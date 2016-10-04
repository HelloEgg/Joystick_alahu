package com.example.cholmink.joystick_alahu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;

import retrofit.Call;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by cholmink on 16. 9. 21..
 */

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    float width;
    float height;
    float centerX;
    float centerY;
    float min;
    float posX;
    float posY;
    float radius;
    int i;
    int size = 20;
    int minRadius;
    int maxRadius;
    int maxX;
    int maxY;
    int tmpRadius;
    Bitmap droid;
    RectF rectF;
    float rotate;

    double angle;
    double power;

    double angle2;

    GameLoop gameLoop;

    long movedTime= 0;
    long untilTime = 0;

    private NetworkService networkService;
    ApplicationController app;

    public CameraView(Context context, Camera camera) {
        super(context);
        networkService = ApplicationController.getInstance().getNetworkService();
        app = (ApplicationController) getContext().getApplicationContext();
//
//        mCamera = camera;
//        mCamera.setDisplayOrientation(180);
//        mHolder = getHolder();
//        mHolder.addCallback(this);
//        mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            //when the surface is created, we can set the camera to draw images in this surfaceholder
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();

            gameLoop = new GameLoop(this);
            gameLoop.setRunning(true);
            gameLoop.start();

        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

        this.width = width;
        this.height = height;
        min = Math.min(width, height);

        centerX = width / 2;
        centerY = height / 2;
        posX = centerX;
        posY = centerY;
        radius = min / 12;
        rectF = new RectF(posX - radius, posY - radius, posX + radius, posY + radius);


        minRadius = (int) (min / 250);
        maxRadius = (int) (min / 220);


        if (maxRadius == minRadius) maxRadius += minRadius;

        if (mHolder.getSurface() == null)//check if the surface is ready to receive camera data
            return;

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            //this will happen when you are trying the camera if it's not running
        }

        //now, recreate the camera preview
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceChanged " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCamera.stopPreview();
        mCamera.release();

        gameLoop.setRunning(false);
        gameLoop = null;

    }

    private SurfaceHolder mHolder;
    private Camera mCamera;


    public void move(final double angle, double power) {
        Log.d("test", "movedTime:"+movedTime);
        Log.d("test", "untilTime:"+untilTime);
        this.angle = angle;
        this.power = power;

        if(System.currentTimeMillis() > untilTime){
            if (angle >= 0.1) {
                turningon();
                untilTime = System.currentTimeMillis() +500;
            }

            if (angle <= -0.1) {
                turningoff();
                untilTime = System.currentTimeMillis() +500;
            }

            Log.d("test", "moved");
        } else {
        }


//
//        SystemClock.sleep(500);


    }

    public void rotate(double angle2) {
        initialize();
        this.angle2 = angle2;


    }

    public void initialize() {
        if (angle2 == 0) rotate = 0;
        else rotate = (float) Math.toDegrees(angle2) - 90;
    }

    private void turningon() {
        Call<dummy> turnon = networkService.turnonLight();
        turnon.enqueue(new Callback<dummy>() {
            double realangle = angle;

            @Override
            public void onResponse(Response<dummy> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Log.i("MyTag", "angle value" + realangle);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void turningoff() {
        Call<dummy> turnoff = networkService.turnoffLight();
        turnoff.enqueue(new Callback<dummy>() {
            double realangle = angle;

            @Override
            public void onResponse(Response<dummy> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Log.i("MyTag", "angle value" + realangle);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
