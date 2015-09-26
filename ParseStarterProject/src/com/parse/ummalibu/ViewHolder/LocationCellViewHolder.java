package com.parse.ummalibu.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ummalibu.Objects.UmLocation;
import com.parse.ummalibu.R;

/**
 * Created by rjaylward on 9/24/15.
 */
public class LocationCellViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageView;
    private TextView mName;
    private TextView mAddress;
    private Context mContext;
    private OnLocationClickedListener mListener;

    public LocationCellViewHolder(Context context, View itemView, OnLocationClickedListener listener) {
        super(itemView);

        mImageView = (ImageView) itemView.findViewById(R.id.cell_location_image);
        mName = (TextView) itemView.findViewById(R.id.cell_location_name);
        mAddress = (TextView) itemView.findViewById(R.id.cell_location_address);
        mListener = listener;
        mContext = context;
    }

    public void buildView(final UmLocation location) {
        Glide.with(mContext).load(location.getImageUrl()).into(mImageView);
        mName.setText(location.getName());
        mAddress.setText(location.getAddress());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLocationClicked(location);
            }
        });
    }

    public interface OnLocationClickedListener {
        void onLocationClicked(UmLocation location);
    }

    public void setOnLocationClickedListener(OnLocationClickedListener listener) {
        mListener = listener;
    }
}