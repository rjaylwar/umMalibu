package com.parse.ummalibu.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.Marker;
import com.parse.ummalibu.R;
import com.parse.ummalibu.objects.UmberRequest;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rjaylward on 10/5/15
 */
public class RiderControlsView extends RelativeLayout {

    private RelativeLayout mControlsLayout;
    private CircleImageView mRiderView;
    private CircleImageView mControlsView;
    private TextView mTitle;

    private UmberRequest mUmberRequest;
    private OnRiderClickedListener mListener;
    private Marker mPickUpMarker;
    private Marker mDestMarker;

    private boolean mIsUpAndOut = false;

    private int mStatus;
    public static final int UNSTARTED = 100;
    public static final int STARTED = 101;
    public static final int PICKED_UP = 102;
    public static final int FINISHED = 103;
    public static boolean mIsAnimating;

    public RiderControlsView(Context context) {
        super(context);
        init();
    }

    public RiderControlsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RiderControlsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public UmberRequest getRequest() {
        return mUmberRequest;
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.umber_rider_circle_view, this, true);

        mControlsLayout = (RelativeLayout) findViewById(R.id.rider_controls_layout);
        mRiderView = (CircleImageView) findViewById(R.id.rider_circle);
        mControlsView = (CircleImageView) findViewById(R.id.rider_circle_controls);
        mTitle = (TextView) findViewById(R.id.rider_circle_controls_title);

        mRiderView.setBorderWidth((int) getResources().getDimension(R.dimen.border_size));
        mRiderView.setBorderColorResource(R.color.black);

        mControlsView.setBorderWidth((int) getResources().getDimension(R.dimen.border_size));
        mControlsView.setBorderColorResource(R.color.black);

        mRiderView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onRiderClicked(mUmberRequest);

                if (!mIsAnimating) {
                    if (mIsUpAndOut)
                        animateDownOrIn();
                    else
                        animateUpOrOut();
                }
            }
        });
        mControlsView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null)
                    mListener.onControlClicked(mUmberRequest);
            }
        });
        mRiderView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(mListener != null)
                    mListener.onRiderLongClickd();
                return true;
            }
        });
    }

    public void setRequest(UmberRequest umberRequest, OnRiderClickedListener listener) {
        mUmberRequest = umberRequest;
        mListener = listener;
        checkState();
        Glide.with(getContext()).load(umberRequest.getRiderImageUrl()).into(mRiderView);
    }

    public void updateRequest(UmberRequest umberRequest) {
        mUmberRequest = umberRequest;
        checkState();
    }

    private void checkState() {
        if(mUmberRequest.isComplete()) {
            mStatus = FINISHED;
            setVisibility(INVISIBLE);
        } else if(mUmberRequest.isPickedUp()) {
            mStatus = PICKED_UP;
            mControlsView.setFillColorResource(R.color.um_grey);
            mTitle.setText(R.string.finish);

            if(getVisibility() == INVISIBLE)
                setVisibility(VISIBLE);

        } else if(mUmberRequest.isStarted()) {
            mStatus = STARTED;
            mControlsView.setFillColorResource(R.color.um_highlight_blue);
            mTitle.setText(R.string.pick_up);

            if(getVisibility() == INVISIBLE)
                setVisibility(VISIBLE);

        } else if(mUmberRequest.isClaimed()) {
            mStatus = UNSTARTED;
            mControlsView.setFillColorResource(R.color.um_green);
            mTitle.setText(R.string.start);

            if(getVisibility() == INVISIBLE)
                setVisibility(VISIBLE);
        }
    }

    public int getStatus() {
        return mStatus;
    }

    public Marker getPickUpMarker() {
        return mPickUpMarker;
    }

    public void setPickUpMarker(Marker pickUpMarker) {
        mPickUpMarker = pickUpMarker;
    }

    public Marker getDestinationMarker() {
        return mDestMarker;
    }

    public void setDestinationMarker(Marker destMarker) {
        mDestMarker = destMarker;
    }

    public interface OnRiderClickedListener {
        void onRiderClicked(UmberRequest request);
        void onControlClicked(UmberRequest request);
        void onRiderLongClickd();
    }

    private void animateDownOrIn() {
        //TODO animation
        mIsUpAndOut = false;
        Animation animationFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.abc_fade_in);
        animationFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mControlsLayout.setAlpha(0.0f);
                mIsAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mControlsLayout.setAlpha(1.0f);
                mIsAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        mControlsLayout.startAnimation(animationFadeIn);
    }

    private void animateUpOrOut() {
        //TODO animation
        mIsUpAndOut = true;
        Animation animationFadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.abc_fade_out);
        animationFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mControlsLayout.setAlpha(1.0f);
                mIsAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mControlsLayout.setAlpha(0.0f);
                mIsAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        mControlsLayout.startAnimation(animationFadeOut);
    }
}
