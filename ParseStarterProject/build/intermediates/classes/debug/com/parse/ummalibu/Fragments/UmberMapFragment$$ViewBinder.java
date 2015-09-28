// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UmberMapFragment$$ViewBinder<T extends com.parse.ummalibu.fragments.UmberMapFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624212, "field 'mPickUpLocationName'");
    target.mPickUpLocationName = finder.castView(view, 2131624212, "field 'mPickUpLocationName'");
  }

  @Override public void unbind(T target) {
    target.mPickUpLocationName = null;
  }
}
