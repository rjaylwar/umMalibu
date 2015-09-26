package com.parse.ummalibu.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.parse.ummalibu.Objects.UmberRequest;
import com.parse.ummalibu.R;
import com.parse.ummalibu.Values.Preferences;
import com.parse.ummalibu.ViewHolder.RequestViewHolder;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/22/15.
 */
public class RequestListAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private OnRequestClickedListener mListener;
    private ArrayList<UmberRequest> mRequests = new ArrayList<>();
    private ArrayList<UmberRequest> mItems = new ArrayList<>();

    public RequestListAdapter(Context context, OnRequestClickedListener listener) {
        mContext = context;
        mListener = listener;
    }

    public void setData(ArrayList<UmberRequest> requests) {
        mRequests.clear();
        mItems.clear();

        mRequests = requests;
        for(UmberRequest request : requests) {
            if(!request.getEmail().equals(Preferences.getInstance().getEmail()) && !request.isClaimed() && !request.isCanceled()) {
                mItems.add(request);
                Log.d("Request -->",request.toContentValues().toString());
            }
        }

        Log.d("Number requests loaded", String.valueOf(mItems.size()));

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RequestViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.card_request, viewGroup, false),
                new RequestViewHolder.OnRequestClickedListener() {
                    @Override
                    public void onRequestClicked(int position) {
                        mListener.onRequestClicked(mItems.get(position));
                    }
                });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof RequestViewHolder)
            ((RequestViewHolder) viewHolder).loadView(mItems.get(i));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface OnRequestClickedListener {
        void onRequestClicked(UmberRequest request);
    }
}
