package com.parse.ummalibu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by rjaylward on 9/24/15.
 */
public class SearchLayout extends RelativeLayout {

    private EditText mPickUpLocationName;
    private OnSearchListener mListener;
    private TextView mTitle;

    public SearchLayout(Context context) {
        super(context);
        initViews();
    }

    public SearchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public SearchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_search_layout, this, true);

        mPickUpLocationName = (EditText) findViewById(R.id.search_pickup_location);
        mPickUpLocationName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onTextClicked();
            }
        });
        mTitle = (TextView) findViewById(R.id.search_pickup_location_title);

        ImageView clearButton = (ImageView) findViewById(R.id.search_clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPickUpLocationName.setText("");
                mPickUpLocationName.setCursorVisible(false);
                mListener.onClearButtonClicked();
            }
        });

        ImageView searchButton = (ImageView) findViewById(R.id.search_search_button);
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLocationButtonClicked();
            }
        });
    }

    public void setOnSearchListener(OnSearchListener listener) {
        mListener = listener;
    }

    public interface OnSearchListener {
        void onTextClicked();
        void onLocationButtonClicked();
        void onClearButtonClicked();
    }

    public void setTitleText(String text) {
        mTitle.setText(text);
    }

    public void setTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    public void setLocationName(String text) {
        mPickUpLocationName.setText(text);
    }

}
