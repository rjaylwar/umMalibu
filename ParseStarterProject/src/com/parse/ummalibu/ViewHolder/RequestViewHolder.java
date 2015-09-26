package com.parse.ummalibu.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ummalibu.Objects.UmberRequest;
import com.parse.ummalibu.R;
import com.parse.ummalibu.Values.Preferences;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rjaylward on 9/22/15.
 */
public class RequestViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.request_card_pick_up) TextView mPickUp;
    @Bind(R.id.request_card_destination) TextView mDestination;
    @Bind(R.id.request_card_name) TextView mName;
    @Bind(R.id.request_card_time) TextView mTime;

    @Bind(R.id.request_card_background_layout) RelativeLayout mBackgroundLayout;

    @Bind(R.id.request_card_person_image) de.hdodenhof.circleimageview.CircleImageView mProfile;
    @Bind(R.id.request_card_main_image) ImageView mMap;

    private OnRequestClickedListener mListener;
    private Context mContext;

    public RequestViewHolder(Context context, View itemView, OnRequestClickedListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        mContext = context;
        mListener = listener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRequestClicked(getAdapterPosition());
            }
        });
    }

    public void loadView(UmberRequest request) {
        Glide.with(mContext).load(request.getRiderImageUrl()).into(mProfile);
        Glide.with(mContext).load(request.getMapUrl(mMap.getWidth(), mMap.getHeight())).into(mMap);

        if(!request.isClaimed())
            mBackgroundLayout.setBackgroundColor(mContext.getResources().getColor(R.color.light_grey));
        else
            mBackgroundLayout.setBackgroundColor(mContext.getResources().getColor(R.color.black));
        if(Preferences.getInstance().getEmail().equals(request.getEmail()))
            mBackgroundLayout.setBackgroundColor(mContext.getResources().getColor(R.color.grey_blue));
        if(request.isCanceled())
            mBackgroundLayout.setBackgroundColor(mContext.getResources().getColor(R.color.red));

        mPickUp.setText(request.getPickUpLocation());
        mDestination.setText(request.getDestination());

        SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy H:mm", Locale.US);
        mTime.setText(dateFormat.format(request.getEta()));
        mName.setText(request.getName());
    }

    public interface OnRequestClickedListener {
        void onRequestClicked(int position);
    }
}
