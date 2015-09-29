// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.parse.ummalibu.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689620, "field 'mDrawerLayout'");
    target.mDrawerLayout = finder.castView(view, 2131689620, "field 'mDrawerLayout'");
    view = finder.findRequiredView(source, 2131689629, "field 'mNavigationView'");
    target.mNavigationView = finder.castView(view, 2131689629, "field 'mNavigationView'");
  }

  @Override public void unbind(T target) {
    target.mDrawerLayout = null;
    target.mNavigationView = null;
  }
}
