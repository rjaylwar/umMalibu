package com.parse.ummalibu;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.parse.ummalibu.adapters.TumblrTalksAdapter;
import com.parse.ummalibu.api.ApiHelper;
import com.parse.ummalibu.base.ToolbarActivity;
import com.parse.ummalibu.objects.TumblrResponse;
import com.parse.ummalibu.objects.TumblrTalk;
import com.parse.ummalibu.views.AudioPlayerView;
import com.parse.ummalibu.views.LoadMoreRecyclerView;
import com.parse.ummalibu.volley.VolleyRequestListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rjaylward on 10/15/15
 **/
public class TumblrTalksActivity extends ToolbarActivity {

    @Bind(R.id.tumblr_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.tumblr_recycler_view)
    LoadMoreRecyclerView mRecyclerView;

    @Bind(R.id.tumblr_toolbar_webview)
    WebView mWebView;
    @Bind(R.id.tumblr_toolbar_image)
    ImageView mImageView;
    @Bind(R.id.tumblr_toolbar_audio_player)
    AudioPlayerView mPlayerView;
    @Bind(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private TumblrTalksAdapter mAdapter;

    public static Intent createIntent(Context context) {
        return new Intent(context, TumblrTalksActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tumblr_talks;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        ButterKnife.bind(this);

        mAdapter = new TumblrTalksAdapter(this);
        mAdapter.setOnTumblrTalkListener(new TumblrTalksAdapter.OnTumblrTalkListener() {
            @Override
            public void onTumblrTalkClick(TumblrTalk tumblrTalk) {
                loadTalkView(tumblrTalk);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                getRequests();
            }
        });

        setUpAudioPlayer();
        getRequests();
    }

    private void getRequests() {
//        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
//        dbHelper.getTumblrTalks();

        ApiHelper apiHelper = new ApiHelper(this);
        apiHelper.getTumblrTalks(new VolleyRequestListener<TumblrResponse>() {
            @Override
            public void onResponse(TumblrResponse response) {
                if(mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);

                Log.d("Talks Size", String.valueOf(response.getTumblrTalks().size()));

                if (!response.isEmpty()) {
                    mAdapter.loadTalks(response.getTumblrTalks());
                    loadTalkView(response.getTumblrTalks().get(0));
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if(mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);

                Log.d("Error", "Error getting tumblr talks - " + error.toString());
            }
        });

    }

    private void setUpAudioPlayer() {

    }

    private void loadTalkView(TumblrTalk talk) {
        mPlayerView.setUpAudioPlayer(talk);
        Glide.with(this).load(talk.getImageUrl()).into(mImageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
