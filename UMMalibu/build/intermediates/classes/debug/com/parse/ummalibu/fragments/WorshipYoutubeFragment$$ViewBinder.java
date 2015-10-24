// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class WorshipYoutubeFragment$$ViewBinder<T extends com.parse.ummalibu.fragments.WorshipYoutubeFragment> extends com.parse.ummalibu.base.YoutubeToolbarFragment$$ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    super.bind(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131689676, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 2131689676, "field 'mRecyclerView'");
    view = finder.findRequiredView(source, 2131689675, "field 'mSwipeRefreshLayout'");
    target.mSwipeRefreshLayout = finder.castView(view, 2131689675, "field 'mSwipeRefreshLayout'");
    view = finder.findRequiredView(source, 2131689677, "field 'mEmptyListView'");
    target.mEmptyListView = finder.castView(view, 2131689677, "field 'mEmptyListView'");
    view = finder.findRequiredView(source, 2131689678, "field 'mProgressBar'");
    target.mProgressBar = finder.castView(view, 2131689678, "field 'mProgressBar'");
  }

  @Override public void unbind(T target) {
    super.unbind(target);

    target.mRecyclerView = null;
    target.mSwipeRefreshLayout = null;
    target.mEmptyListView = null;
    target.mProgressBar = null;
  }
}
