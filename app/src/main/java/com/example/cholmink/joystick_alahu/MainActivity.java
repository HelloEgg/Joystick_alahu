package com.example.cholmink.joystick_alahu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements JoyStick.JoyStickListener {
    private Camera mCamera = null;
    private CameraView mCameraView = null;
    private IP_Data ip_data = new IP_Data();

    GameView gameView;
    ImageButton setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setting = (ImageButton)findViewById(R.id.imageButton);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);

            }
        });
        try{
            mCamera = Camera.open();//you can use open(int) to use different cameras
        } catch (Exception e){
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }
        mCameraView = new CameraView(this, mCamera);
//        FrameLayout camera_view = (FrameLayout)findViewById(R.id.camera_view);
//        camera_view.addView(mCameraView);
        VideoView videoView = (VideoView)findViewById(R.id.myVideo);
        String vidAddress = "http://www.ebookfrenzy.com/android_book/movie.mp4";
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);
        Uri vidUri = Uri.parse(vidAddress);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(vidUri);

        videoView.requestFocus();
        videoView.start();

        JoyStick joy1 = (JoyStick) findViewById(R.id.joy1);
        joy1.setListener(this);
        joy1.setPadColor(Color.parseColor("#55ffffff"));
        joy1.setButtonColor(Color.parseColor("#55ff0000"));

        JoyStick joy2 = (JoyStick) findViewById(R.id.joy2);
        joy2.setListener(this);
        joy2.enableStayPut(true);
        joy2.setPadBackground(R.drawable.pad);
        joy2.setButtonDrawable(R.drawable.button);
    }

    @Override
    public void onMove(JoyStick joyStick, double angle, double power) {
        switch (joyStick.getId()) {
            case R.id.joy1:
                mCameraView.move(angle, power);
                break;
            case R.id.joy2:
                mCameraView.rotate(angle);
                break;
        }
    }
}
