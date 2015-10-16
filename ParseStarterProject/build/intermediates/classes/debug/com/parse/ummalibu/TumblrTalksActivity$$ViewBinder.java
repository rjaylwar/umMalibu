// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TumblrTalksActivity$$ViewBinder<T extends com.parse.ummalibu.TumblrTalksActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689636, "field 'mSwipeRefreshLayout'");
    target.mSwipeRefreshLayout = finder.castView(view, 2131689636, "field 'mSwipeRefreshLayout'");
    view = finder.findRequiredView(source, 2131689637, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 2131689637, "field 'mRecyclerView'");
    view = finder.findRequiredView(source, 2131689807, "field 'mWebView'");
    target.mWebView = finder.castView(view, 2131689807, "field 'mWebView'");
    view = finder.findRequiredView(source, 2131689805, "field 'mImageView'");
    target.mImageView = finder.castView(view, 2131689805, "field 'mImageView'");
    view = finder.findRequiredView(source, 2131689806, "field 'mPlayerView'");
    target.mPlayerView = finder.castView(view, 2131689806, "field 'mPlayerView'");
    view = finder.findRequiredView(source, 2131689801, "field 'mAppBarLayout'");
    target.mAppBarLayout = finder.castView(view, 2131689801, "field 'mAppBarLayout'");
    view = finder.findRequiredView(source, 2131689803, "field 'mCollapsingToolbarLayout'");
    target.mCollapsingToolbarLayout = finder.castView(view, 2131689803, "field 'mCollapsingToolbarLayout'");
  }

  @Override public void unbind(T target) {
    target.mSwipeRefreshLayout = null;
    target.mRecyclerView = null;
    target.mWebView = null;
    target.mImageView = null;
    target.mPlayerView = null;
    target.mAppBarLayout = null;
    target.mCollapsingToolbarLayout = null;
  }
}
