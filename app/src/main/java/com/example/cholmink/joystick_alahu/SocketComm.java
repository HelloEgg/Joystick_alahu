package com.example.cholmink.joystick_alahu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by cholmink on 16. 10. 6..
 */
public class SocketComm extends Activity{
    Socket s;
    BufferedReader dis;
    DataOutputStream dos;
    String msg;
    String ipaddress="172.24.1.1";
    int portnum = 8888;


    boolean onAir = false;

    Handler handler = new Handler();


//    View.OnClickListener bHandler = new View.OnClickListener() {

//        @Override
//        public void onClick(View v) {
//            switch(v.getId()){
//                case R.id.btnConnect :
////				connect("192.168.0.197",12345);
//                    connect1("192.168.0.21",12345);
//                    break;
//                case R.id.btnSend :
//                    sendMsg();
//                    break;
//            }
//
//        }
//    };
    public void sendMsg(){
        try{
            msg = "a";
            Log.i("Test", "dos value:" + dos);
            dos.writeBytes(msg);
            dos.flush();
        }catch(Exception e){
            Log.i("Test", "Error messg" + e);
        }
    }

    void close(){
        try{
            onAir = false;
            dis.close();
            dos.close();
            s.close();
        }catch(Exception e){

        }
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        close();
    }
    //Code for incoming signal
//    class ReadThread extends Thread{
//        String msg = "";
//        public void run(){
//            while(onAir){
//                try{
//                    msg = dis.readLine();
//                    handler.post(new Runnable(){
//                        public void run(){
//                            tv.setText(msg);
//                        }
//                    });
//                }catch(Exception e){
//                    toastShow("읽기 오류 : " +  e);
//                }
//            }
//        }
//    }
    void connectThread(){
        new Thread(){
            @Override
            public void run() {
                connect(ipaddress, portnum);
            }
        }.start();
    }
    private void connect(String ip, int port){
        try{
            //Getting message from server
            s = new Socket(ip, port);
            dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
            Log.i("Test", "connect 함수 들옴");
            dos = new DataOutputStream(s.getOutputStream());
//            ReadThread trd = new ReadThread();
            Log.i("MyTag", "value of dos in connect class: " + dos);
            onAir = true;
//            trd.start();
//			String msg = dis.readUTF();
//			tv.setText(msg);

        }catch(Exception e){
            toastShow("접속오류 : " + e);
//			Toast.makeText(this, "접속오류 : " + e, Toast.LENGTH_SHORT).show();
        }
    }
    private void toastShow(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    /** Called when the activity is first created. */


}
