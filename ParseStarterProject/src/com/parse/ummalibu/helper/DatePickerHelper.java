package com.parse.ummalibu.helper;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cal.set(year, monthOfYear, dayOfMonth);
                showTimePicker(cal, request);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showTimePicker(final Calendar cal, final UmberRequest request) {
        Calendar today = Calendar.getInstance();

        if(!mStartPromptOnCurrentTime)
            today.setTime(request.getEta());

        TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);

                mListener.onDatePicked(request, cal);
            }
        }, today.get(Calendar.HOUR_OF_DAY), today.get(Calendar.MINUTE), false);

        timePickerDialog.show();
    }

    public interface OnDatePickedListener {
        void onDatePicked(UmberRequest request, Calendar cal);
    }

}
