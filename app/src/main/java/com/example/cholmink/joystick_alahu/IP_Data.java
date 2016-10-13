package com.example.cholmink.joystick_alahu;

/**
 * Created by cholmink on 16. 9. 30..
 */
public class IP_Data {
    public String control_ip = "172.24.1.1";
    public String screen_ip = "172.24.1.1/video";

    public String getScreen_ip(){
        return screen_ip;
    }
    public String getControl_ip(){
        return control_ip;
    }
}
