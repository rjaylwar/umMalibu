// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LocationsFragment$$ViewBinder<T extends com.parse.ummalibu.fragments.LocationsFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689663, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 2131689663, "field 'mRecyclerView'");
    view = finder.findRequiredView(source, 2131689662, "field 'mSwipeRefreshLayout'");
    target.mSwipeRefreshLayout = finder.castView(view, 2131689662, "field 'mSwipeRefreshLayout'");
    view = finder.findRequiredView(source, 2131689664, "field 'mEmptyListView'");
    target.mEmptyListView = finder.castView(view, 2131689664, "field 'mEmptyListView'");
    view = finder.findRequiredView(source, 2131689665, "field 'mProgressBar'");
    target.mProgressBar = finder.castView(view, 2131689665, "field 'mProgressBar'");
    view = finder.findRequiredView(source, 2131689803, "field 'mSearchLayout'");
    target.mSearchLayout = finder.castView(view, 2131689803, "field 'mSearchLayout'");
  }

  @Override public void unbind(T target) {
    target.mRecyclerView = null;
    target.mSwipeRefreshLayout = null;
    target.mEmptyListView = null;
    target.mProgressBar = null;
    target.mSearchLayout = null;
  }
}
