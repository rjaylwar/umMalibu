package com.parse.ummalibu.volley;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rjaylward on 9/22/15.
 */
public class StringObjectVolleyRequester extends AbsVolleyRequester {

    public StringObjectVolleyRequester(Context context) {
        super(context);
    }

    @Override
    protected Request buildRequest(AppCompatActivity activity, int requestType, final String url, final JsonObject
            requestBody, final VolleyRequestListener listener, int timeout, boolean handleResponse) {

        Request jsonObjectRequest = buildRequest(activity, requestType, url, requestBody == null ? new JsonObject().toString() : requestBody.toString(), listener, timeout, handleResponse);

        return jsonObjectRequest;
    }

    protected Request buildRequest(AppCompatActivity activity, int requestType, final String url, final String
            requestBody, final VolleyRequestListener listener, int timeout, boolean handleResponse) {

        StringRequest stringRequest = new StringRequest(requestType, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
                if(listener != null)
                    listener.onResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onVolleyError(error, url, listener);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return buildAuthenticationHeader();
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        if(timeout == 0)
            timeout = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag("JSON Request");

        return stringRequest;
    }

    private Map<String, String> buildAuthenticationHeader() {
        Map<String, String> header = new HashMap<>();

        header.put("X-Parse-REST-API-Key", "DmMChll2DEBaeXA6m7dNtkVeFlnEbwL9En9RElyU");
        header.put("X-Parse-Application-Id", "M8lsPPcaku0od8yzToKaHKR11TUFlsZuRPorClFt");

        return header;
    }
}

