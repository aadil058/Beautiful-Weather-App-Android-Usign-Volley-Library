/*

We need to invoke and create an instance of this class when application launches for this add
android:name=".RequestWeather" to Manifest File

By default, Volley sets the priority of the request to NORMAL. Usually that would be fine,
but in our application we have two requests that are quite different and we therefore need to have a
different priority in the queue. Fetching the weather data needs to have a higher priority than fetching
the URL of the random image.

For that reason, we need to customize the JsonRequest class. Create a new class named CustomJsonRequest.java,
and make sure it extends JsonObjectRequest.

mInstance and mRequestQueue Method was shared by by all class ir for all functions , anywhere it is used

 */

package com.aadil.volleyweatherapp;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestWeather extends Application {

    private RequestQueue mRequestQueue;
    private static RequestWeather mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static synchronized RequestWeather getInstance() {
        return mInstance;
    }

    public <T> void add(Request <T> request) {
        request.setTag("tag");
        mRequestQueue.add(request);
    }

    public void cancel() {
        mRequestQueue.cancelAll("tag");
    }
}
