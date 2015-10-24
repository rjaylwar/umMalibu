package com.parse.ummalibu.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.parse.ummalibu.R;
import com.parse.ummalibu.adapters.YoutubeVideosAdapter;
import com.parse.ummalibu.api.ApiHelper;
import com.parse.ummalibu.base.YoutubeToolbarFragment;
import com.parse.ummalibu.objects.YoutubeItem;
import com.parse.ummalibu.responses.YoutubeItemsResponse;
import com.parse.ummalibu.views.EmptyListView;
import com.parse.ummalibu.views.LoadMoreRecyclerView;
import com.parse.ummalibu.volley.VolleyRequestListener;

import butterknife.Bind;

/**
 * Created by rjaylward on 10/22/15
 */
public class WorshipYoutubeFragment extends YoutubeToolbarFragment implements YouTubePlayer.OnInitializedListener {

//    @Bind(R.id.youtube_player_view)
//    YouTubePlayerView mPlayerView;
    @Bind(R.id.recycler_view)
    LoadMoreRecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.basic_empty_list_view)
    EmptyListView mEmptyListView;
    @Bind(R.id.progress)
    ProgressBar mProgressBar;

    YouTubePlayer mPlayer;
    YoutubeVideosAdapter mAdapter;

    public static WorshipYoutubeFragment createFragment() {
        return new WorshipYoutubeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_worship_youtube;
    }

    @Override
    protected void fragOnCreateView(Bundle savedInstanceState) {
//        initialize(ApiHelper.YOUTUBE_KEY, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new YoutubeVideosAdapter(mActivity, new YoutubeVideosAdapter.OnLinkClickedListener() {
            @Override
            public void onLinkClicked(YoutubeItem youtubeItem) {
                if(mPlayer != null)
                    mPlayer.cueVideo(youtubeItem.getVideoId());
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                makeApiRequest();
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        mPlayer = youTubePlayer;
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    private void makeApiRequest() {
        ApiHelper apiHelper = new ApiHelper(mActivity);
        apiHelper.getYoutubeVideos("PLi1lg8EgWuZTFkpHpc6rb0qu4Lt7pD_u1", new VolleyRequestListener<YoutubeItemsResponse>() {
            @Override
            public void onResponse(YoutubeItemsResponse response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if(!response.isNull() && !response.isEmpty()) {
                    mAdapter.loadItems(response.getYoutubeItems());
//                    mPlayer.cueVideo(response.getYoutubeItems().get(0).getVideoId());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
