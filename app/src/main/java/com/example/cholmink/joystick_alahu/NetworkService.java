package com.example.cholmink.joystick_alahu;
import retrofit.Call;

import retrofit.http.GET;

/**
 * Created by cholmink on 16. 9. 25..
 */
public interface NetworkService {
        @GET("/on")
    Call<dummy> turnonLight();
    @GET("/off")
    Call<dummy> turnoffLight();
}
