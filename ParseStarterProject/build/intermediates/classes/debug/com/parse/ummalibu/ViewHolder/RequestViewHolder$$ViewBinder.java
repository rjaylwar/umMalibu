// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.ViewHolder;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RequestViewHolder$$ViewBinder<T extends com.parse.ummalibu.ViewHolder.RequestViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624135, "field 'mPickUp'");
    target.mPickUp = finder.castView(view, 2131624135, "field 'mPickUp'");
    view = finder.findRequiredView(source, 2131624136, "field 'mDestination'");
    target.mDestination = finder.castView(view, 2131624136, "field 'mDestination'");
    view = finder.findRequiredView(source, 2131624139, "field 'mName'");
    target.mName = finder.castView(view, 2131624139, "field 'mName'");
    view = finder.findRequiredView(source, 2131624138, "field 'mTime'");
    target.mTime = finder.castView(view, 2131624138, "field 'mTime'");
    view = finder.findRequiredView(source, 2131624133, "field 'mBackgroundLayout'");
    target.mBackgroundLayout = finder.castView(view, 2131624133, "field 'mBackgroundLayout'");
    view = finder.findRequiredView(source, 2131624140, "field 'mProfile'");
    target.mProfile = finder.castView(view, 2131624140, "field 'mProfile'");
    view = finder.findRequiredView(source, 2131624137, "field 'mMap'");
    target.mMap = finder.castView(view, 2131624137, "field 'mMap'");
  }

  @Override public void unbind(T target) {
    target.mPickUp = null;
    target.mDestination = null;
    target.mName = null;
    target.mTime = null;
    target.mBackgroundLayout = null;
    target.mProfile = null;
    target.mMap = null;
  }
}
