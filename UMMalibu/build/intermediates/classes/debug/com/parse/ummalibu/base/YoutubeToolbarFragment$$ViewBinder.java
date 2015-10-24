// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.base;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class YoutubeToolbarFragment$$ViewBinder<T extends com.parse.ummalibu.base.YoutubeToolbarFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findOptionalView(source, 2131689597, null);
    target.mToolbar = finder.castView(view, 2131689597, "field 'mToolbar'");
  }

  @Override public void unbind(T target) {
    target.mToolbar = null;
  }
}
