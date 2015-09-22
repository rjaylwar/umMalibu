package com.parse.ummalibu.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ummalibu.Objects.UmberRequest;
import com.parse.ummalibu.R;
import com.parse.ummalibu.Values.Preferences;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by rjaylward on 9/22/15.
 */
public class RequestViewHolder extends RecyclerView.ViewHolder {

    private TextView mPickUp;
    private TextView mDestination;
    private TextView mName;
    private TextView mTime;
    private RelativeLayout mBackgroundLayout;

    private ImageView mProfile;
    private ImageView mMap;

    private Context mContext;

    public RequestViewHolder(Context context, View itemView) {
        super(itemView);
        mBackgroundLayout = (RelativeLayout) itemView.findViewById(R.id.request_card_background_layout);

        mPickUp = (TextView) itemView.findViewById(R.id.request_card_pick_up);
        mDestination = (TextView) itemView.findViewById(R.id.request_card_destination);
        mName = (TextView) itemView.findViewById(R.id.request_card_name);
        mTime = (TextView) itemView.findViewById(R.id.request_card_time);

        mProfile = (ImageView) itemView.findViewById(R.id.request_card_person_image);
        mMap = (ImageView) itemView.findViewById(R.id.request_card_main_image);

        mContext = context;
    }

    public void loadView(UmberRequest request) {
        Picasso.with(mContext).load(request.getRiderImageUrl()).into(mProfile);
        Picasso.with(mContext).load(request.getMapUrl(mMap.getWidth(), mMap.getHeight())).into(mMap);

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d H:mm", Locale.US);
        mTime.setText(dateFormat.format(request.getEta()));
        mName.setText(request.getName());
    }

}
