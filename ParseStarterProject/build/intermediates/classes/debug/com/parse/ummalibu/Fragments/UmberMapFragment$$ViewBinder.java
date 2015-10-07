// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.fragments;

import android.content.res.Resources;
import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UmberMapFragment$$ViewBinder<T extends com.parse.ummalibu.fragments.UmberMapFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689790, "field 'mSearchPickUpLayout'");
    target.mSearchPickUpLayout = finder.castView(view, 2131689790, "field 'mSearchPickUpLayout'");
    view = finder.findRequiredView(source, 2131689791, "field 'mSearchDestLayout'");
    target.mSearchDestLayout = finder.castView(view, 2131689791, "field 'mSearchDestLayout'");
    view = finder.findRequiredView(source, 2131689789, "field 'mSearchLayout'");
    target.mSearchLayout = finder.castView(view, 2131689789, "field 'mSearchLayout'");
    view = finder.findRequiredView(source, 2131689636, "field 'mRequestButton'");
    target.mRequestButton = finder.castView(view, 2131689636, "field 'mRequestButton'");
    Resources res = finder.getContext(source).getResources();
    target.mSearchLayoutHeight = res.getDimensionPixelSize(2131361895);
  }

  @Override public void unbind(T target) {
    target.mSearchPickUpLayout = null;
    target.mSearchDestLayout = null;
    target.mSearchLayout = null;
    target.mRequestButton = null;
  }
}
