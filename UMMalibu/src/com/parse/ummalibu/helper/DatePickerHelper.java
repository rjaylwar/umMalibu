package com.parse.ummalibu.helper;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.parse.ummalibu.objects.UmberRequest;

import java.util.Calendar;

/**
 * Created by rjaylward on 10/8/15
 */
public class DatePickerHelper {

    private AppCompatActivity mActivity;
    private boolean mStartPromptOnCurrentTime;
    private OnDatePickedListener mListener;
    private boolean mTimePickerCanceled = false;

    public DatePickerHelper(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void showPicker(UmberRequest request, boolean startOnCurrentTime, OnDatePickedListener listener) {
        mStartPromptOnCurrentTime = startOnCurrentTime;
        mListener = listener;
        showDatePicker(request);
    }

    private void showDatePicker(final UmberRequest request) {
        final Calendar cal = Calendar.getInstance();

        if(!mStartPromptOnCurrentTime)
            cal.setTime(request.getEta());
        final DatePickerDialog datePickerDialog;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            datePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    onDateSetMethod(request, cal, year, monthOfYear, dayOfMonth);
                }
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        } else {
            datePickerDialog = new DatePickerDialog(mActivity, null, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onDateSetMethod(request, cal, datePickerDialog.getDatePicker().getYear(), datePickerDialog.getDatePicker().getMonth(), datePickerDialog.getDatePicker().getDayOfMonth());
                }
            });
            datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    datePickerDialog.dismiss();
                }
            });

            datePickerDialog.setCancelable(true);
            datePickerDialog.setCanceledOnTouchOutside(true);
        }
        datePickerDialog.show();
    }

    private void onDateSetMethod(UmberRequest request, Calendar cal, int year, int monthOfYear, int dayOfMonth) {
        cal.set(year, monthOfYear, dayOfMonth);
        showTimePicker(cal, request);
    }

    private void onTimePickedMethod(Calendar cal, UmberRequest request, int hourOfDay, int minute) {
        if(!mTimePickerCanceled) {
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);

            mListener.onDatePicked(request, cal);
        }
        else Log.d("Time Picker", "Canceled");
    }

    private void showTimePicker(final Calendar cal, final UmberRequest request) {
        Calendar today = Calendar.getInstance();
        final TimePickerDialog timePickerDialog;
        if(!mStartPromptOnCurrentTime)
            today.setTime(request.getEta());

        timePickerDialog = new TimePickerDialog(mActivity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                onTimePickedMethod(cal, request, hourOfDay, minute);
            }
        }, today.get(Calendar.HOUR_OF_DAY), today.get(Calendar.MINUTE), false);

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mTimePickerCanceled = true;
                    timePickerDialog.dismiss();
                }
            });
            timePickerDialog.setCancelable(true);
            timePickerDialog.setCanceledOnTouchOutside(true);
        }
        timePickerDialog.show();
    }

    public interface OnDatePickedListener {
        void onDatePicked(UmberRequest request, Calendar cal);
    }

}
