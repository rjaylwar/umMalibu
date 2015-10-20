// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SummaryFragment$$ViewBinder<T extends com.parse.ummalibu.fragments.SummaryFragment> extends com.parse.ummalibu.fragments.ToolbarFragment$$ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    super.bind(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131689720, "field 'mMapImage'");
    target.mMapImage = finder.castView(view, 2131689720, "field 'mMapImage'");
    view = finder.findRequiredView(source, 2131689721, "field 'mTopLocationsLayout'");
    target.mTopLocationsLayout = finder.castView(view, 2131689721, "field 'mTopLocationsLayout'");
    view = finder.findRequiredView(source, 2131689722, "field 'mPickUpAddress'");
    target.mPickUpAddress = finder.castView(view, 2131689722, "field 'mPickUpAddress'");
    view = finder.findRequiredView(source, 2131689723, "field 'mDestAddress'");
    target.mDestAddress = finder.castView(view, 2131689723, "field 'mDestAddress'");
    view = finder.findRequiredView(source, 2131689724, "field 'mSlidingLayout'");
    target.mSlidingLayout = finder.castView(view, 2131689724, "field 'mSlidingLayout'");
    view = finder.findRequiredView(source, 2131689743, "field 'mCircleImageView'");
    target.mCircleImageView = finder.castView(view, 2131689743, "field 'mCircleImageView'");
    view = finder.findRequiredView(source, 2131689726, "field 'mContactButton' and method 'sendText'");
    target.mContactButton = finder.castView(view, 2131689726, "field 'mContactButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.sendText();
        }
      });
    view = finder.findRequiredView(source, 2131689727, "field 'mStatusButton' and method 'onStatusButtonClick'");
    target.mStatusButton = finder.castView(view, 2131689727, "field 'mStatusButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onStatusButtonClick();
        }
      });
    view = finder.findRequiredView(source, 2131689728, "field 'mName'");
    target.mName = finder.castView(view, 2131689728, "field 'mName'");
    view = finder.findRequiredView(source, 2131689731, "field 'mPickUpTime'");
    target.mPickUpTime = finder.castView(view, 2131689731, "field 'mPickUpTime'");
    view = finder.findRequiredView(source, 2131689733, "field 'mDistance'");
    target.mDistance = finder.castView(view, 2131689733, "field 'mDistance'");
    view = finder.findRequiredView(source, 2131689735, "field 'mTripTime'");
    target.mTripTime = finder.castView(view, 2131689735, "field 'mTripTime'");
    view = finder.findRequiredView(source, 2131689737, "field 'mStatus'");
    target.mStatus = finder.castView(view, 2131689737, "field 'mStatus'");
    view = finder.findRequiredView(source, 2131689738, "field 'mCost'");
    target.mCost = finder.castView(view, 2131689738, "field 'mCost'");
    view = finder.findRequiredView(source, 2131689740, "field 'mMinusButton' and method 'divideCost'");
    target.mMinusButton = finder.castView(view, 2131689740, "field 'mMinusButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.divideCost();
        }
      });
    view = finder.findRequiredView(source, 2131689742, "field 'mPlusButton' and method 'splitCost'");
    target.mPlusButton = finder.castView(view, 2131689742, "field 'mPlusButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.splitCost();
        }
      });
    view = finder.findRequiredView(source, 2131689741, "field 'mSplitGas' and method 'launchVenmo'");
    target.mSplitGas = finder.castView(view, 2131689741, "field 'mSplitGas'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.launchVenmo();
        }
      });
  }

  @Override public void unbind(T target) {
    super.unbind(target);

    target.mMapImage = null;
    target.mTopLocationsLayout = null;
    target.mPickUpAddress = null;
    target.mDestAddress = null;
    target.mSlidingLayout = null;
    target.mCircleImageView = null;
    target.mContactButton = null;
    target.mStatusButton = null;
    target.mName = null;
    target.mPickUpTime = null;
    target.mDistance = null;
    target.mTripTime = null;
    target.mStatus = null;
    target.mCost = null;
    target.mMinusButton = null;
    target.mPlusButton = null;
    target.mSplitGas = null;
  }
}
