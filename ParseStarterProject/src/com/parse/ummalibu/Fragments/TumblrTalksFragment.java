package com.parse.ummalibu.fragments;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.parse.ummalibu.R;
import com.parse.ummalibu.adapters.RequestListAdapter;
import com.parse.ummalibu.adapters.TumblrTalksAdapter;
import com.parse.ummalibu.api.ApiHelper;
import com.parse.ummalibu.objects.BaseTumblrResponse;
import com.parse.ummalibu.objects.TumblrTalk;
import com.parse.ummalibu.views.AudioPlayerView;
import com.parse.ummalibu.views.LoadMoreRecyclerView;
import com.parse.ummalibu.volley.VolleyRequestListener;

import butterknife.Bind;

/**
 * Created by rjaylward on 10/17/15
 */
public class TumblrTalksFragment extends ToolbarFragment {

    @Bind(R.id.tumblr_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.tumblr_load_more_recycler_view)
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
    private RequestListAdapter requestListAdapter;

    public static TumblrTalksFragment createFragment() {
        return new TumblrTalksFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tumblr_talks;
    }

    @Override
    protected void fragOnCreateView(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new TumblrTalksAdapter(mActivity);

        mAdapter.setOnTumblrTalkListener(new TumblrTalksAdapter.OnTumblrTalkListener() {
            @Override
            public void onTumblrTalkClick(TumblrTalk tumblrTalk) {
                changeTalkView(tumblrTalk);
            }
        });

//        requestListAdapter = new RequestListAdapter(mActivity, new RequestListAdapter.OnRequestClickedListener() {
//            @Override
//            public void onRequestClicked(UmberRequest request) {
//                Log.d("Request Clicked", "test works");
//            }
//        });

        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                getRequests();
            }
        });

        mSwipeRefreshLayout.setEnabled(false);

        setUpAudioPlayer();
        getRequests();
    }

    private void getRequests() {
//        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
//        dbHelper.getTumblrTalks();

        ApiHelper apiHelper = new ApiHelper(mActivity);
        apiHelper.getTumblrTalks(new VolleyRequestListener<BaseTumblrResponse>() {
            @Override
            public void onResponse(BaseTumblrResponse response) {
                if (mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);

                try {
                    Log.d("Talks Size", String.valueOf(response.getResponse().getTumblrTalks().size()));

                    if (!response.getResponse().isEmpty()) {
                        mAdapter.loadTalks(response.getResponse().getTumblrTalks());
                        loadTalkView(response.getResponse().getTumblrTalks().get(0));
                    }
                } catch (NullPointerException e) {
                    Log.d("Null", "Something is null " + e.getMessage());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if (mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);

                Log.d("Error", "Error getting tumblr talks - " + error.toString());
            }
        });

    }

    private void setUpAudioPlayer() { }

    private void loadTalkView(TumblrTalk talk) {
        mPlayerView.setUpAudioPlayer(talk);
        Glide.with(this).load(talk.getImageUrl()).into(mImageView);
    }

    private void changeTalkView(TumblrTalk talk) {
        mPlayerView.changeTalk(talk);
        Glide.with(this).load(talk.getImageUrl()).into(mImageView);
    }

    @Override
    public void onActivityBackPressed() {
        super.onActivityBackPressed();
        if(mPlayerView != null)
            mPlayerView.release();
    }
}