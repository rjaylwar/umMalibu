package com.parse.ummalibu.volley;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

/**
 * Created by rjaylward on 9/22/15.
 */
public abstract class AbsVolleyRequester {

    protected Context mContext;

    public AbsVolleyRequester(Context context) {
        mContext = context.getApplicationContext(); //To avoid leaks
    }

    public void makeGetRequest(AppCompatActivity activity, String url, final VolleyRequestListener listener) {
        VolleyRequestQueue queue = VolleyRequestQueue.getInstance(activity.getApplicationContext());
        Request request = buildRequest(activity, Request.Method.GET, url, null, listener, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, true);
        queue.add(request);
    }

    public void makeLargeGetRequest(AppCompatActivity activity, String url, final VolleyRequestListener listener) {
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        Request request = buildRequest(activity, Request.Method.GET, url, null, listener, 10000, true);
        queue.add(request);
    }

    public void makePostRequest(AppCompatActivity activity, String url, JsonObject requestBody, VolleyRequestListener listener) {
        makePostRequest(activity, url, requestBody, listener, true);
    }

    public void makePostRequest(AppCompatActivity activity, String url, JsonObject requestBody, VolleyRequestListener listener, boolean handleResponse) {
        VolleyRequestQueue queue = VolleyRequestQueue.getInstance(activity.getApplicationContext());
        Request request = buildRequest(activity, Request.Method.POST, url, requestBody, listener, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, handleResponse);

        queue.add(request);
    }

    public void makePutRequest(AppCompatActivity activity, String url, JsonObject requestBody, VolleyRequestListener listener) {
        makePutRequest(activity, url, requestBody, listener, true);
    }

    public void makePutRequest(AppCompatActivity activity, String url, JsonObject requestBody, VolleyRequestListener listener, boolean handleResponse) {
        VolleyRequestQueue queue = VolleyRequestQueue.getInstance(activity.getApplicationContext());
        Request request = buildRequest(activity, Request.Method.PUT, url, requestBody, listener, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, handleResponse);

        queue.add(request);
    }

    public void makeDeleteRequest(AppCompatActivity activity, String url, VolleyRequestListener listener) {
        VolleyRequestQueue queue = VolleyRequestQueue.getInstance(activity.getApplicationContext());
        Request request = buildRequest(activity, Request.Method.DELETE, url, null, listener, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, false);

        queue.add(request);
    }

    protected abstract Request buildRequest(AppCompatActivity activity, int requestType, String url, JsonObject requestBody,
                                            VolleyRequestListener listener, int timeout, boolean handleResponse);

    protected void onVolleyError(VolleyError error, String url, VolleyRequestListener listener) {
        try {
            if(error instanceof TimeoutError)
                Log.d("request timed out", url);
            else {
                Log.d("error status code", Integer.toString(error.networkResponse.statusCode));

                Log.d("hopefully message?", new String(error.networkResponse.data, "UTF-8"));
            }
        } catch(Exception e) {
            Log.d(null, e.toString());
        }

        if(listener != null)
            listener.onErrorResponse(error == null ? new VolleyError("error was null") : error);
    }

}

