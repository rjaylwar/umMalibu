package com.parse.ummalibu.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    Context mContext;
    ArrayList<UmberRequest> mRequests = new ArrayList<>();
    ArrayList<UmberRequest> mItems = new ArrayList<>();

    public RequestListAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<UmberRequest> requests) {
        mRequests = requests;
        for(UmberRequest request : requests) {
            if(!request.getEmail().equals(Preferences.getInstance().getEmail()))
                mItems.add(request);
        }

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RequestViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.card_request, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof RequestViewHolder)
            ((RequestViewHolder) viewHolder).loadView(mRequests.get(i));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
