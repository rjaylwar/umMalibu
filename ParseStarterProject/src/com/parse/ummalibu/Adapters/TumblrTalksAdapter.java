package com.parse.ummalibu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ummalibu.R;
import com.parse.ummalibu.holder.TumblrTalksViewHolder;
import com.parse.ummalibu.objects.TumblrTalk;

import java.util.ArrayList;

/**
 * Created by rjaylward on 10/16/15
 */
public class TumblrTalksAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private OnTumblrTalkListener mListener;
    private ArrayList<TumblrTalk> mItems = new ArrayList<>();

    public TumblrTalksAdapter(Context context) {
        mContext = context;
    }

    public void loadTalks(ArrayList<TumblrTalk> talks) {
        mItems = talks;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TumblrTalksViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.card_tumblr_talk, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof TumblrTalksViewHolder)
            ((TumblrTalksViewHolder) holder).loadView(mItems.get(position), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null)
                        mListener.onTumblrTalkClick(mItems.get(position));
                }
            });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnTumblrTalkListener(OnTumblrTalkListener tumblrTalkListener) {
        mListener = tumblrTalkListener;
    }

    public interface OnTumblrTalkListener {
        void onTumblrTalkClick(TumblrTalk tumblrTalk);
    }

}