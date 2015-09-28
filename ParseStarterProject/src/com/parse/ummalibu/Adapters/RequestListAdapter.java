package com.parse.ummalibu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.parse.ummalibu.objects.UmberRequest;
import com.parse.ummalibu.R;
import com.parse.ummalibu.values.Preferences;
import com.parse.ummalibu.holder.RequestViewHolder;

import java.util.ArrayList;

/**
 * Created by rjaylward on 9/22/15.
 */
public class RequestListAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private OnRequestClickedListener mListener;
    private ArrayList<UmberRequest> mRequests = new ArrayList<>();
    private ArrayList<UmberRequest> mItems = new ArrayList<>();

    private boolean mUseColors;

    public static final int ALL_REQUESTS = 0;
    public static final int MY_REQUESTS = 1;
    public static final int OPEN_REQUESTS = 2;

    public RequestListAdapter(Context context, OnRequestClickedListener listener) {
        mContext = context;
        mListener = listener;
    }

    public void colorRequests(boolean useColors) {
        mUseColors = useColors;
    }

    public void setData(ArrayList<UmberRequest> requests, int mode) {
        mRequests.clear();
        mItems.clear();

        mRequests = requests;
        switch (mode) {
            case OPEN_REQUESTS :
                for(UmberRequest request : requests) {
                    if (!request.getEmail().equals(Preferences.getInstance().getEmail()) && !request.isClaimed() && !request.isCanceled())
                        mItems.add(request);
                }
                break;
            case MY_REQUESTS :
                for(UmberRequest request : requests) {
                    if (request.getEmail().equals(Preferences.getInstance().getEmail()) || request.getDriverEmail().equals(Preferences.getInstance().getEmail()))
                        mItems.add(request);
                }
                break;
            default :
                mItems.addAll(requests);
                break;
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
            ((RequestViewHolder) viewHolder).loadView(mItems.get(i), mUseColors);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface OnRequestClickedListener {
        void onRequestClicked(UmberRequest request);
    }
}
