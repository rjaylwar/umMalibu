package com.parse.ummalibu.fragments;

import android.annotation.SuppressLint;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.parse.ummalibu.R;
import com.parse.ummalibu.adapters.TumblrTalksAdapter;
import com.parse.ummalibu.api.ApiHelper;
import com.parse.ummalibu.database.DatabaseHelper;
import com.parse.ummalibu.database.Table;
import com.parse.ummalibu.objects.BaseTumblrResponse;
import com.parse.ummalibu.objects.TumblrTalk;
import com.parse.ummalibu.values.Constants;
import com.parse.ummalibu.values.Preferences;
import com.parse.ummalibu.views.AudioPlayerView;
import com.parse.ummalibu.views.LoadMoreRecyclerView;
import com.parse.ummalibu.volley.VolleyRequestListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.BindBool;

/**
 * Created by rjaylward on 10/17/15
 **/
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

    @BindBool(R.bool.isLandscape)
    boolean mLandscape;

    private TumblrTalksAdapter mAdapter;
    private boolean mHasLoaded;

    public static TumblrTalksFragment createFragment() {
        return new TumblrTalksFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tumblr_talks;
    }

    @Override
    protected void fragOnCreateView(Bundle savedInstanceState) {
        if(!mLandscape) {
            mActivity.getScreenSize();
            //noinspection SuspiciousNameCombination
            mAppBarLayout.getLayoutParams().height = mActivity.getScreenSize().x;
            //noinspection SuspiciousNameCombination
            mPlayerView.getLayoutParams().height = mActivity.getScreenSize().x;
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new TumblrTalksAdapter(mActivity);

        mAdapter.setOnTumblrTalkListener(new TumblrTalksAdapter.OnTumblrTalkListener() {
            @Override
            public void onTumblrTalkClick(TumblrTalk tumblrTalk) {
                changeTalkView(tumblrTalk);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                makeApiRequest();
            }
        });

//        mSwipeRefreshLayout.setEnabled(false);

        mActivity.getContentResolver().registerContentObserver(Table.TumblrTalks.CONTENT_URI, true, mContentObserver);

        getRequests();
    }

    ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            loadTalksFromDatabase();
        }
    };

    private void getRequests() {
        if(Preferences.getInstance().getTumblrTalksExpiration() < System.currentTimeMillis())
            makeApiRequest();

        loadTalksFromDatabase();
    }

    private void makeApiRequest() {
        ApiHelper apiHelper = new ApiHelper(mActivity);
        apiHelper.getTumblrTalks(new VolleyRequestListener<BaseTumblrResponse>() {
            @Override
            public void onResponse(BaseTumblrResponse response) {
                response.saveResponse(mActivity);
                Preferences.getInstance().setTumblrTalksExpiration(System.currentTimeMillis() + Constants.ONE_DAY_MILLIS);

                mSwipeRefreshLayout.setRefreshing(false);

                try {
                    Log.d("Talks Size", String.valueOf(response.getResponse().getTumblrTalks().size()));
                } catch (NullPointerException e) {
                    Log.d("Null", "Something is null " + e.getMessage());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                mSwipeRefreshLayout.setRefreshing(false);

                Log.d("Error", "Error getting tumblr talks - " + error.toString());
            }
        });
    }

    private void loadTalksFromDatabase() {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(mActivity);
        ArrayList<TumblrTalk> talks = dbHelper.getTumblrTalks();

        if(!talks.isEmpty()) {
            mAdapter.loadTalks(talks);
            loadTalkView(talks.get(0));
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadTalkView(TumblrTalk talk) {
        if(!mHasLoaded) {
            if(mActivity.getActionBar() != null)
                mActivity.getActionBar().setTitle(talk.getTitle());

            mPlayerView.setUpAudioPlayer(talk);
            mWebView.setWebViewClient(new WebViewClient());
            mWebView.setWebChromeClient(new WebChromeClient());
            mWebView.getSettings().setJavaScriptEnabled(true);

            if(talk.getType().equals("video")) {
                if(mWebView.getVisibility() != View.VISIBLE)
                    mWebView.setVisibility(View.VISIBLE);
                if(mPlayerView.getVisibility() != View.GONE)
                    mPlayerView.setVisibility(View.GONE);

                Glide.with(this).load(talk.getThumbnailUrl()).into(mImageView);
                //noinspection SuspiciousNameCombination
                mWebView.loadUrl(talk.getVideoUrl());
            } else {
                if(mWebView.getVisibility() != View.GONE)
                    mWebView.setVisibility(View.GONE);
                if(mPlayerView.getVisibility() != View.VISIBLE)
                    mPlayerView.setVisibility(View.VISIBLE);

                Glide.with(this).load(talk.getImageUrl()).into(mImageView);
            }
            mHasLoaded = true;
        } else
            changeTalkView(talk);
    }

    private void changeTalkView(TumblrTalk talk) {
        Log.d("ID", String.valueOf(talk.getId()));

        if(mActivity.getActionBar() != null)
            mActivity.getActionBar().setTitle(talk.getTitle());

        if(talk.getType().equals("video")) {
            if(mWebView.getVisibility() != View.VISIBLE)
                mWebView.setVisibility(View.VISIBLE);
            if(mPlayerView.getVisibility() != View.GONE)
                mPlayerView.setVisibility(View.GONE);

            Glide.with(this).load(talk.getThumbnailUrl()).into(mImageView);
            mPlayerView.pause();
            //noinspection SuspiciousNameCombination
            mWebView.loadUrl(talk.getVideoUrl());
        }
        else {
            mWebView.loadUrl(null);
            if(mWebView.getVisibility() != View.GONE)
                mWebView.setVisibility(View.GONE);
            if(mPlayerView.getVisibility() != View.VISIBLE)
                mPlayerView.setVisibility(View.VISIBLE);

            mPlayerView.changeTalk(talk);
            Glide.with(this).load(talk.getImageUrl()).into(mImageView);
        }
    }

    @Override
    public void onActivityBackPressed() {
        super.onActivityBackPressed();
        if(mPlayerView != null)
            mPlayerView.release();

        mWebView = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mActivity.getContentResolver().unregisterContentObserver(mContentObserver);
    }
}