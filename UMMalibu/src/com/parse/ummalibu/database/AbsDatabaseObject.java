package com.parse.ummalibu.database;

import android.content.Context;
import android.net.Uri;

/**
 * Created by rjaylward on 9/22/15.
 */
public abstract class AbsDatabaseObject implements ApiResponse {

    public abstract void saveResponse(final Context context);

    public abstract Uri getUri();

}
