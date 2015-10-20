package com.parse.ummalibu.fragments;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.parse.ummalibu.R;
import com.parse.ummalibu.adapters.RequestListAdapter;
import com.parse.ummalibu.base.BaseFragment;
import com.parse.ummalibu.database.Table;
import com.parse.ummalibu.objects.UmberRequest;
import com.parse.ummalibu.views.EmptyListView;
import com.parse.ummalibu.views.LoadMoreRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rjaylward on 9/26/15
 */
public abstract class AbsRequestListFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    protected LoadMoreRecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.basic_empty_list_view)
    protected EmptyListView mEmptyListView;
    @Bind(R.id.progress)
    protected ProgressBar mProgressBar;

    protected RequestListAdapter mAdapter;
    protected AppCompatActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.basic_swipe_refresh_recyclerview, container, false);
        ButterKnife.bind(this, root);

        mActivity = (AppCompatActivity) getActivity();
        mActivity.getContentResolver().registerContentObserver(Table.Requests.CONTENT_URI, true, mRequestsObserver);

        initLayout();
        loadList();
        return root;
    }

    private void initLayout() {
        mEmptyListView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new RequestListAdapter(getActivity(), new RequestListAdapter.OnRequestClickedListener() {
            @Override
            public void onRequestClicked(UmberRequest request) {
                onRequestSelected(request);
            }
        });
        mAdapter.colorRequests(useColors());
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeColors(R.color.um_green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiRequest();
            }
        });
    }

    protected abstract void onRequestSelected(UmberRequest request);

    protected boolean useColors() {
        return false;
    }

    protected int getMode() {
        return RequestListAdapter.OPEN_REQUESTS;
    }

    private ContentObserver mRequestsObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            loadList();
        }
    };

    private void loadList() {
        if(getExpiration() < System.currentTimeMillis())
            apiRequest();

        databaseRequest();
    }

    protected abstract void databaseRequest();

    protected abstract void apiRequest();

    protected abstract long getExpiration();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        getActivity().getContentResolver().unregisterContentObserver(mRequestsObserver);
    }

}
