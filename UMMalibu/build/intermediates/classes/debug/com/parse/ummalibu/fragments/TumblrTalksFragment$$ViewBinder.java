// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.fragments;

import android.content.res.Resources;
import android.view.View;
import butterknife.ButterKnife.Finder;

public class TumblrTalksFragment$$ViewBinder<T extends com.parse.ummalibu.fragments.TumblrTalksFragment> extends com.parse.ummalibu.fragments.ToolbarFragment$$ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    super.bind(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131689638, "field 'mSwipeRefreshLayout'");
    target.mSwipeRefreshLayout = finder.castView(view, 2131689638, "field 'mSwipeRefreshLayout'");
    view = finder.findRequiredView(source, 2131689639, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 2131689639, "field 'mRecyclerView'");
    view = finder.findRequiredView(source, 2131689816, "field 'mWebView'");
    target.mWebView = finder.castView(view, 2131689816, "field 'mWebView'");
    view = finder.findRequiredView(source, 2131689815, "field 'mImageView'");
    target.mImageView = finder.castView(view, 2131689815, "field 'mImageView'");
    view = finder.findRequiredView(source, 2131689817, "field 'mPlayerView'");
    target.mPlayerView = finder.castView(view, 2131689817, "field 'mPlayerView'");
    view = finder.findRequiredView(source, 2131689811, "field 'mAppBarLayout'");
    target.mAppBarLayout = finder.castView(view, 2131689811, "field 'mAppBarLayout'");
    view = finder.findRequiredView(source, 2131689813, "field 'mCollapsingToolbarLayout'");
    target.mCollapsingToolbarLayout = finder.castView(view, 2131689813, "field 'mCollapsingToolbarLayout'");
    Resources res = finder.getContext(source).getResources();
    target.mLandscape = res.getBoolean(2131361795);
  }

  @Override public void unbind(T target) {
    super.unbind(target);

    target.mSwipeRefreshLayout = null;
    target.mRecyclerView = null;
    target.mWebView = null;
    target.mImageView = null;
    target.mPlayerView = null;
    target.mAppBarLayout = null;
    target.mCollapsingToolbarLayout = null;
  }
}
