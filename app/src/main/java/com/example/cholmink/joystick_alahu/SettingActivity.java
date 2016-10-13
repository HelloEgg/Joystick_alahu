package com.example.cholmink.joystick_alahu;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by cholmink on 16. 9. 30..
 */
    public class SettingActivity extends AppCompatActivity {

    Button screen, control;
    private TextView screenview, controlview;
    private IP_Data ip_data = new IP_Data();
    String screendata, controldata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        controldata = String.valueOf(ip_data.getControl_ip());
        screendata = String.valueOf(ip_data.getScreen_ip());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_setting);
        Button screen = (Button)findViewById(R.id.screen);
        Button control = (Button) findViewById(R.id.control);
        TextView screenview = (TextView) findViewById(R.id.screenTextView);
        TextView controlview = (TextView) findViewById(R.id.controltextView);
        screenview.setText(screendata);
        controlview.setText(controldata);

        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("Test", "온클릭 들어와요");
                android.app.AlertDialog.Builder alertDialogBuilder =
                        new android.app.AlertDialog.Builder(SettingActivity.this);
                alertDialogBuilder.setTitle("Click OK to change the screen IP Setting");
                alertDialogBuilder.setMessage("Current IP address is :" + screendata);

                final EditText inputscreen = new EditText(SettingActivity.this);
                inputscreen.setInputType(InputType.TYPE_CLASS_TEXT);
                alertDialogBuilder.setView(inputscreen);
                alertDialogBuilder.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        ip_data.screen_ip = inputscreen.getText().toString();
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
            }
        });


    }



}
