package com.parse.ummalibu.Volley;

import com.android.volley.VolleyError;

/**
 * Created by rjaylward on 9/22/15.
 */
public interface VolleyRequestListener<T> {

    void onResponse(T response);

    void onErrorResponse(VolleyError error);

}
