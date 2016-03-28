/*

[IMP] I want ratio of 70 : 30 for image and weather info on all screen sizes so in the xml layout i used android:weight
       android attribute

Also change app theme to NoActionBar

We should avoid creating a request queue each time you schedule a request by invoking Volley.newRequestQueue,
because you don't want to end up with memory leaks and other unwanted problems.
To achieve this, create a new class.

For better ui create a new folder named fonts inside assets and setTypeFace.
you have to create it at the same level as the java folder

 */

package com.aadil.volleyweatherapp;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.*;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView textDegrees, textWeather, textError;
    private ImageView imageView;

    final static String url = "http://marsweather.ingenology.com/v1/latest/";
    String imageUrl = "http://www.kitesurfing.com.au/wp-content/uploads/2013/06/Weather-Partly-cloudy-day-icon1.png";

    RequestWeather requestWeather = RequestWeather.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        textDegrees = (TextView) findViewById(R.id.degrees);
        textError = (TextView) findViewById(R.id.error);
        textWeather = (TextView) findViewById(R.id.weather);

        imageView = (ImageView) findViewById(R.id.image);

        textDegrees.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf"));
        textWeather.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf"));

        loadWeatherData();
        loadImage();
    }

    private void loadWeatherData() {

        CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(Request.Method.GET, url,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            String minTemp, maxTemp, atmo;
                            int avgTemp;

                            response = response.getJSONObject("report");

                            minTemp = response.getString("min_temp"); minTemp = minTemp.substring(0, minTemp.indexOf("."));
                            maxTemp = response.getString("max_temp"); maxTemp = maxTemp.substring(0, maxTemp.indexOf("."));

                            avgTemp = (Integer.parseInt(minTemp)+Integer.parseInt(maxTemp))/2;

                            atmo = response.getString("atmo_opacity");


                            textDegrees.setText(avgTemp+"°");
                            textWeather.setText(atmo);

                        } catch (Exception e) {
                            textError.setText(e.getMessage());
                        }
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textError.setText(error.getMessage());
                    }
                }
        );

        jsonObjectRequest.setPriority(Request.Priority.HIGH);
        requestWeather.add(jsonObjectRequest);
    }


    void loadImage() {

        ImageRequest imageRequest = new ImageRequest(imageUrl,

                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        System.out.println("called again");
                        imageView.setImageBitmap(response);
                    }
                },

                0,
                0,
                ImageView.ScaleType.FIT_XY,
                Bitmap.Config.ARGB_8888,

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error " + error.getLocalizedMessage());
                    }
                }
        );

        requestWeather.add(imageRequest);
    }
}
