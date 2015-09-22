package com.parse.ummalibu.Volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by rjaylward on 9/22/15.
 */
public class VolleyRequestQueue {

    private static VolleyRequestQueue instance;
    private RequestQueue mRequestQueue;
    private Context mContext;

    private VolleyRequestQueue(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if(mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext()); //Want it to live through the entire application, no leaks

        return mRequestQueue;
    }

    public static synchronized VolleyRequestQueue getInstance(Context context) {
        if(instance == null)
            instance = new VolleyRequestQueue(context);
        return instance;
    }

    public <T> void add(Request<T> request) {
        getRequestQueue().add(request);
    }

    public void cancelAll(final String filter) {
        getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {

            @Override
            public boolean apply(Request<?> request) {
                return request.getTag().equals(filter);
            }

        });
    }

}

