// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.fragments;

import android.content.res.Resources;
import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UmberMapFragment$$ViewBinder<T extends com.parse.ummalibu.fragments.UmberMapFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689828, "field 'mSearchPickUpLayout'");
    target.mSearchPickUpLayout = finder.castView(view, 2131689828, "field 'mSearchPickUpLayout'");
    view = finder.findRequiredView(source, 2131689829, "field 'mSearchDestLayout'");
    target.mSearchDestLayout = finder.castView(view, 2131689829, "field 'mSearchDestLayout'");
    view = finder.findRequiredView(source, 2131689827, "field 'mSearchLayout'");
    target.mSearchLayout = finder.castView(view, 2131689827, "field 'mSearchLayout'");
    view = finder.findRequiredView(source, 2131689647, "field 'mRequestButton'");
    target.mRequestButton = finder.castView(view, 2131689647, "field 'mRequestButton'");
    view = finder.findRequiredView(source, 2131689640, "field 'mDriverLayout'");
    target.mDriverLayout = finder.castView(view, 2131689640, "field 'mDriverLayout'");
    view = finder.findRequiredView(source, 2131689641, "field 'mDriverImage'");
    target.mDriverImage = finder.castView(view, 2131689641, "field 'mDriverImage'");
    view = finder.findRequiredView(source, 2131689644, "field 'mDriverName'");
    target.mDriverName = finder.castView(view, 2131689644, "field 'mDriverName'");
    view = finder.findRequiredView(source, 2131689643, "field 'mDriverContactButton' and method 'contactDriver'");
    target.mDriverContactButton = finder.castView(view, 2131689643, "field 'mDriverContactButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.contactDriver();
        }
      });
    view = finder.findRequiredView(source, 2131689646, "field 'mDriverCancelButton' and method 'promptToCancel'");
    target.mDriverCancelButton = finder.castView(view, 2131689646, "field 'mDriverCancelButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.promptToCancel();
        }
      });
    view = finder.findRequiredView(source, 2131689645, "field 'mDriverCarDescription'");
    target.mDriverCarDescription = finder.castView(view, 2131689645, "field 'mDriverCarDescription'");
    view = finder.findRequiredView(source, 2131689648, "field 'mRidersLayout'");
    target.mRidersLayout = finder.castView(view, 2131689648, "field 'mRidersLayout'");
    view = finder.findRequiredView(source, 2131689650, "field 'mFirstRiderView'");
    target.mFirstRiderView = finder.castView(view, 2131689650, "field 'mFirstRiderView'");
    view = finder.findRequiredView(source, 2131689651, "field 'mSecondRiderView'");
    target.mSecondRiderView = finder.castView(view, 2131689651, "field 'mSecondRiderView'");
    view = finder.findRequiredView(source, 2131689652, "field 'mThirdRiderView'");
    target.mThirdRiderView = finder.castView(view, 2131689652, "field 'mThirdRiderView'");
    Resources res = finder.getContext(source).getResources();
    target.mSearchLayoutHeight = res.getDimensionPixelSize(2131427452);
  }

  @Override public void unbind(T target) {
    target.mSearchPickUpLayout = null;
    target.mSearchDestLayout = null;
    target.mSearchLayout = null;
    target.mRequestButton = null;
    target.mDriverLayout = null;
    target.mDriverImage = null;
    target.mDriverName = null;
    target.mDriverContactButton = null;
    target.mDriverCancelButton = null;
    target.mDriverCarDescription = null;
    target.mRidersLayout = null;
    target.mFirstRiderView = null;
    target.mSecondRiderView = null;
    target.mThirdRiderView = null;
  }
}
