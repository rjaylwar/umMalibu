package com.parse.ummalibu.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ummalibu.R;
import com.parse.ummalibu.objects.YoutubeItem;

/**
 * Created by rjaylward on 10/22/15
 */
public class YoutubeViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;
    private ImageView mImageView;
    private View mView;

    public YoutubeViewHolder(View itemView) {
        super(itemView);

        mTitle = (TextView) itemView.findViewById(R.id.youtube_cell_title);
        mImageView = (ImageView) itemView.findViewById(R.id.youtube_cell_image_view);
        mView = itemView;
    }

    public void buildView(Context context, YoutubeItem item, View.OnClickListener onClickListener) {
        mTitle.setText(item.getTitle());
        Glide.with(context).load(item.getThumbnailUrl()).into(mImageView);
        mView.setOnClickListener(onClickListener);
    }
}