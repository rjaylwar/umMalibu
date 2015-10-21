package com.parse.ummalibu.util;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by rjaylward on 10/21/15
 */
public class ProgressDialogHelper {

    /**
     * Creates a generic progress dialog with the specified message
     *
     * @param activity the activity which hosts the dialog. This must be an activity, not a context.
     * @param msgResId the resId for the message to display
     * @return a progress dialog
     */
    public static ProgressDialog buildDialog(Activity activity, int msgResId) {
        return buildDialog(activity, activity.getApplicationContext().getString(msgResId));
    }

    /**
     * Creates a generic progress dialog with the specified message
     *
     * @param activity the activity which hosts the dialog. This must be an activity, not a context.
     * @param msg the message to display
     * @return a progress dialog
     */
    public static ProgressDialog buildDialog(Activity activity, String msg) {
        ProgressDialog dialog = new ProgressDialog(activity);

        dialog.setMessage(msg);
        dialog.setCancelable(false);

        return dialog;
    }

}