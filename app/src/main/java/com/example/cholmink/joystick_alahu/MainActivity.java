package com.example.cholmink.joystick_alahu;

import android.graphics.Color;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements JoyStick.JoyStickListener {
    private Camera mCamera = null;
    private CameraView mCameraView = null;

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            mCamera = Camera.open();//you can use open(int) to use different cameras
        } catch (Exception e){
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }
        mCameraView = new CameraView(this, mCamera);
        FrameLayout camera_view = (FrameLayout)findViewById(R.id.camera_view);
        camera_view.addView(mCameraView);


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
