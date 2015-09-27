// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.Fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RequestListFragment$$ViewBinder<T extends com.parse.ummalibu.Fragments.RequestListFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624130, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 2131624130, "field 'mRecyclerView'");
    view = finder.findRequiredView(source, 2131624129, "field 'mSwipeRefreshLayout'");
    target.mSwipeRefreshLayout = finder.castView(view, 2131624129, "field 'mSwipeRefreshLayout'");
    view = finder.findRequiredView(source, 2131624131, "field 'mEmptyListView'");
    target.mEmptyListView = finder.castView(view, 2131624131, "field 'mEmptyListView'");
    view = finder.findRequiredView(source, 2131624132, "field 'mProgressBar'");
    target.mProgressBar = finder.castView(view, 2131624132, "field 'mProgressBar'");
  }

  @Override public void unbind(T target) {
    target.mRecyclerView = null;
    target.mSwipeRefreshLayout = null;
    target.mEmptyListView = null;
    target.mProgressBar = null;
  }
}
