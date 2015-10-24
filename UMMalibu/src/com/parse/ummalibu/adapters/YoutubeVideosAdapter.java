package com.parse.ummalibu.adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ummalibu.R;
import com.parse.ummalibu.holder.YoutubeViewHolder;
import com.parse.ummalibu.objects.YoutubeItem;

import java.util.ArrayList;

/**
 * Created by rjaylward on 10/22/15
 */
public class YoutubeVideosAdapter extends RecyclerView.Adapter {

    ArrayList<YoutubeItem> mItems = new ArrayList<>();
    AppCompatActivity mActivity;
    OnLinkClickedListener mListener;

    public YoutubeVideosAdapter(AppCompatActivity activity, OnLinkClickedListener listener) {
        mActivity = activity;
        mListener = listener;
    }

    public void loadItems(ArrayList<YoutubeItem> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new YoutubeViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.cell_youtube, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof YoutubeViewHolder)
            ((YoutubeViewHolder) holder).buildView(mActivity, mItems.get(position), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null)
                        mListener.onLinkClicked(mItems.get(position));
                }
            });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface OnLinkClickedListener {
        void onLinkClicked(YoutubeItem youtubeItem);
    }
}
