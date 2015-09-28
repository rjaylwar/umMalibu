// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.holder;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RequestViewHolder$$ViewBinder<T extends com.parse.ummalibu.holder.RequestViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624130, "field 'mCardView'");
    target.mCardView = finder.castView(view, 2131624130, "field 'mCardView'");
    view = finder.findRequiredView(source, 2131624131, "field 'mBackgroundLayout'");
    target.mBackgroundLayout = finder.castView(view, 2131624131, "field 'mBackgroundLayout'");
    view = finder.findRequiredView(source, 2131624138, "field 'mProfile'");
    target.mProfile = finder.castView(view, 2131624138, "field 'mProfile'");
    view = finder.findRequiredView(source, 2131624135, "field 'mMap'");
    target.mMap = finder.castView(view, 2131624135, "field 'mMap'");
    view = finder.findRequiredView(source, 2131624133, "field 'mPickUp'");
    target.mPickUp = finder.castView(view, 2131624133, "field 'mPickUp'");
    view = finder.findRequiredView(source, 2131624134, "field 'mDestination'");
    target.mDestination = finder.castView(view, 2131624134, "field 'mDestination'");
    view = finder.findRequiredView(source, 2131624137, "field 'mName'");
    target.mName = finder.castView(view, 2131624137, "field 'mName'");
    view = finder.findRequiredView(source, 2131624136, "field 'mTime'");
    target.mTime = finder.castView(view, 2131624136, "field 'mTime'");
  }

  @Override public void unbind(T target) {
    target.mCardView = null;
    target.mBackgroundLayout = null;
    target.mProfile = null;
    target.mMap = null;
    target.mPickUp = null;
    target.mDestination = null;
    target.mName = null;
    target.mTime = null;
  }
}
