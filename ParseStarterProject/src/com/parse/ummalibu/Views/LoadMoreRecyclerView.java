package com.parse.ummalibu.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by rjaylward on 9/22/15.
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private OnLoadMoreTriggeredListener mOnLoadMoreTriggeredListener;
    private int mBeginRefreshAt;

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public void initialize() {
        //Default
        mBeginRefreshAt = 5;

        addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                try {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();

                    int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                    if(lastVisiblePosition >= getAdapter().getItemCount() - mBeginRefreshAt) {
                        if(mOnLoadMoreTriggeredListener != null) {
                            mOnLoadMoreTriggeredListener.onLoadMoreTriggered();
                            mOnLoadMoreTriggeredListener = null;
                        }
                    }
                } catch(ClassCastException e) {
                    Log.e("Load more recycler view", e.getMessage());
                }
            }

        });
    }

    public void setBeginRefreshAt(int refreshAt) {
        mBeginRefreshAt = refreshAt;
    }

    public void setOnLoadMoreTriggeredListener(OnLoadMoreTriggeredListener onLoadMoreTriggeredListener) {
        mOnLoadMoreTriggeredListener = onLoadMoreTriggeredListener;
    }

    public interface OnLoadMoreTriggeredListener {

        void onLoadMoreTriggered();

    }

}

