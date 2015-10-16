package com.parse.ummalibu.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ummalibu.R;
import com.parse.ummalibu.objects.TumblrTalk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rjaylward on 10/16/15
 */
public class TumblrTalksViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tumblr_talk_image)
    ImageView mImageView;
    @Bind(R.id.tumblr_talk_title)
    TextView mTitle;
    @Bind(R.id.tumblr_talk_subtitle)
    TextView mSubtitle;
    @Bind(R.id.tumblr_talk_date)
    TextView mDate;

    private Context mContext;

    public TumblrTalksViewHolder(Context context, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    public void loadView(TumblrTalk tumblrTalk, View.OnClickListener onClickListener) {
        Glide.with(mContext).load(tumblrTalk.getImageUrl()).into(mImageView);
        mTitle.setText(tumblrTalk.getTitle());
        mSubtitle.setText(tumblrTalk.getSubtitle());

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        String dateString = dateFormat.format(new Date(tumblrTalk.getTimestamp()));
        mDate.setText(dateString);

        if(onClickListener != null)
            itemView.setOnClickListener(onClickListener);
    }
}
