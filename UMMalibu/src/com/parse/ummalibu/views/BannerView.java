package com.parse.ummalibu.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.parse.ummalibu.R;

/**
 * Created by rjaylward on 10/22/15
 */
public class BannerView extends FrameLayout {

    private TextView mTextView;
    private View mClose;

    public BannerView(Context context) {
        super(context);
        init();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_banner, this, true);
        setBackgroundColor(getResources().getColor(R.color.um_green_button_pressed));

        mTextView = (TextView) findViewById(R.id.df_text);
        mClose = findViewById(R.id.df_close);

        mClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibility(GONE);
            }
        });
    }

    public void setBodyClickListener(OnClickListener listener) {
        setOnClickListener(listener);
    }

    public void setTitle(String title) {
        mTextView.setText(title);
    }
}
