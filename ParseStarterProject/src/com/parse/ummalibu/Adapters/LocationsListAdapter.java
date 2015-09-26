package com.parse.ummalibu.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.parse.ummalibu.Objects.UmLocation;
import com.parse.ummalibu.R;
import com.parse.ummalibu.ViewHolder.LocationCellViewHolder;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/24/15.
 */
public class LocationsListAdapter extends RecyclerView.Adapter {

    private ArrayList<UmLocation> mItems = new ArrayList<>();
    private OnLocationSelectedListener mListener;
    private Context mContext;

    public LocationsListAdapter(Context context, OnLocationSelectedListener listener) {
        mContext = context;
        mListener = listener;
    }

    public void setItems(ArrayList<UmLocation> locations) {
        mItems.clear();
        mItems = locations;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocationCellViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.cell_location, parent, false),
                new LocationCellViewHolder.OnLocationClickedListener() {
                    @Override
                    public void onLocationClicked(UmLocation location) {
                        mListener.onLocationSelected(location);
                    }
                });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LocationCellViewHolder)
            ((LocationCellViewHolder) holder).buildView(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface OnLocationSelectedListener {
        void onLocationSelected(UmLocation location);
    }
}
