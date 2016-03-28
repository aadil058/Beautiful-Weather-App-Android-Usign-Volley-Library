package com.aadil.volleyweatherapp;

import com.android.volley.Response;

import org.json.JSONObject;

public class CustomJsonObjectRequest extends com.android.volley.toolbox.JsonObjectRequest {

    private Priority mPriority;

    public CustomJsonObjectRequest(int method, String url,
                                   Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public void setPriority(Priority priority) {
        mPriority = priority;
    }

    @Override
    public Priority getPriority() {
        return mPriority == null ? Priority.NORMAL : mPriority;
    }
}
