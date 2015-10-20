// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RideShareActivity$$ViewBinder<T extends com.parse.ummalibu.RideShareActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689619, "field 'mDrawerLayout'");
    target.mDrawerLayout = finder.castView(view, 2131689619, "field 'mDrawerLayout'");
    view = finder.findRequiredView(source, 2131689627, "field 'mNavigationView'");
    target.mNavigationView = finder.castView(view, 2131689627, "field 'mNavigationView'");
    view = finder.findRequiredView(source, 2131689810, "field 'mTabLayout'");
    target.mTabLayout = finder.castView(view, 2131689810, "field 'mTabLayout'");
    view = finder.findRequiredView(source, 2131689637, "field 'mViewPager'");
    target.mViewPager = finder.castView(view, 2131689637, "field 'mViewPager'");
  }

  @Override public void unbind(T target) {
    target.mDrawerLayout = null;
    target.mNavigationView = null;
    target.mTabLayout = null;
    target.mViewPager = null;
  }
}
