// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.holder;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TumblrTalksViewHolder$$ViewBinder<T extends com.parse.ummalibu.holder.TumblrTalksViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689681, "field 'mImageView'");
    target.mImageView = finder.castView(view, 2131689681, "field 'mImageView'");
    view = finder.findRequiredView(source, 2131689682, "field 'mTitle'");
    target.mTitle = finder.castView(view, 2131689682, "field 'mTitle'");
    view = finder.findRequiredView(source, 2131689683, "field 'mSubtitle'");
    target.mSubtitle = finder.castView(view, 2131689683, "field 'mSubtitle'");
    view = finder.findRequiredView(source, 2131689684, "field 'mDate'");
    target.mDate = finder.castView(view, 2131689684, "field 'mDate'");
  }

  @Override public void unbind(T target) {
    target.mImageView = null;
    target.mTitle = null;
    target.mSubtitle = null;
    target.mDate = null;
  }
}
