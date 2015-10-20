// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.fragments;

import android.content.res.Resources;
import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UmberMapFragment$$ViewBinder<T extends com.parse.ummalibu.fragments.UmberMapFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689817, "field 'mSearchPickUpLayout'");
    target.mSearchPickUpLayout = finder.castView(view, 2131689817, "field 'mSearchPickUpLayout'");
    view = finder.findRequiredView(source, 2131689818, "field 'mSearchDestLayout'");
    target.mSearchDestLayout = finder.castView(view, 2131689818, "field 'mSearchDestLayout'");
    view = finder.findRequiredView(source, 2131689816, "field 'mSearchLayout'");
    target.mSearchLayout = finder.castView(view, 2131689816, "field 'mSearchLayout'");
    view = finder.findRequiredView(source, 2131689640, "field 'mRequestButton'");
    target.mRequestButton = finder.castView(view, 2131689640, "field 'mRequestButton'");
    view = finder.findRequiredView(source, 2131689633, "field 'mDriverLayout'");
    target.mDriverLayout = finder.castView(view, 2131689633, "field 'mDriverLayout'");
    view = finder.findRequiredView(source, 2131689634, "field 'mDriverImage'");
    target.mDriverImage = finder.castView(view, 2131689634, "field 'mDriverImage'");
    view = finder.findRequiredView(source, 2131689637, "field 'mDriverName'");
    target.mDriverName = finder.castView(view, 2131689637, "field 'mDriverName'");
    view = finder.findRequiredView(source, 2131689636, "field 'mDriverContactButton' and method 'contactDriver'");
    target.mDriverContactButton = finder.castView(view, 2131689636, "field 'mDriverContactButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.contactDriver();
        }
      });
    view = finder.findRequiredView(source, 2131689639, "field 'mDriverCancelButton' and method 'promptToCancel'");
    target.mDriverCancelButton = finder.castView(view, 2131689639, "field 'mDriverCancelButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.promptToCancel();
        }
      });
    view = finder.findRequiredView(source, 2131689638, "field 'mDriverCarDescription'");
    target.mDriverCarDescription = finder.castView(view, 2131689638, "field 'mDriverCarDescription'");
    view = finder.findRequiredView(source, 2131689641, "field 'mRidersLayout'");
    target.mRidersLayout = finder.castView(view, 2131689641, "field 'mRidersLayout'");
    view = finder.findRequiredView(source, 2131689643, "field 'mFirstRiderView'");
    target.mFirstRiderView = finder.castView(view, 2131689643, "field 'mFirstRiderView'");
    view = finder.findRequiredView(source, 2131689644, "field 'mSecondRiderView'");
    target.mSecondRiderView = finder.castView(view, 2131689644, "field 'mSecondRiderView'");
    view = finder.findRequiredView(source, 2131689645, "field 'mThirdRiderView'");
    target.mThirdRiderView = finder.castView(view, 2131689645, "field 'mThirdRiderView'");
    Resources res = finder.getContext(source).getResources();
    target.mSearchLayoutHeight = res.getDimensionPixelSize(2131427445);
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
