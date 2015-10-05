// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UmberMapFragment$$ViewBinder<T extends com.parse.ummalibu.fragments.UmberMapFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689774, "field 'mSearchPickUpLayout'");
    target.mSearchPickUpLayout = finder.castView(view, 2131689774, "field 'mSearchPickUpLayout'");
    view = finder.findRequiredView(source, 2131689775, "field 'mSearchDestLayout'");
    target.mSearchDestLayout = finder.castView(view, 2131689775, "field 'mSearchDestLayout'");
    view = finder.findRequiredView(source, 2131689778, "field 'mPickUpLocationName'");
    target.mPickUpLocationName = finder.castView(view, 2131689778, "field 'mPickUpLocationName'");
  }

  @Override public void unbind(T target) {
    target.mSearchPickUpLayout = null;
    target.mSearchDestLayout = null;
    target.mPickUpLocationName = null;
  }
}
