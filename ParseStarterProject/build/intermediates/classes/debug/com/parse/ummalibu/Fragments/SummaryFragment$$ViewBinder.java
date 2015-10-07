// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SummaryFragment$$ViewBinder<T extends com.parse.ummalibu.fragments.SummaryFragment> extends com.parse.ummalibu.fragments.ToolbarFragment$$ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    super.bind(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131689689, "field 'mMapImage'");
    target.mMapImage = finder.castView(view, 2131689689, "field 'mMapImage'");
    view = finder.findRequiredView(source, 2131689690, "field 'mTopLocationsLayout'");
    target.mTopLocationsLayout = finder.castView(view, 2131689690, "field 'mTopLocationsLayout'");
    view = finder.findRequiredView(source, 2131689691, "field 'mPickUpAddress'");
    target.mPickUpAddress = finder.castView(view, 2131689691, "field 'mPickUpAddress'");
    view = finder.findRequiredView(source, 2131689692, "field 'mDestAddress'");
    target.mDestAddress = finder.castView(view, 2131689692, "field 'mDestAddress'");
    view = finder.findRequiredView(source, 2131689693, "field 'mSlidingLayout'");
    target.mSlidingLayout = finder.castView(view, 2131689693, "field 'mSlidingLayout'");
    view = finder.findRequiredView(source, 2131689695, "field 'mCircleImageView'");
    target.mCircleImageView = finder.castView(view, 2131689695, "field 'mCircleImageView'");
    view = finder.findRequiredView(source, 2131689696, "field 'mContactButton' and method 'sendText'");
    target.mContactButton = finder.castView(view, 2131689696, "field 'mContactButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.sendText();
        }
      });
    view = finder.findRequiredView(source, 2131689697, "field 'mStatusButton' and method 'onStatusButtonClick'");
    target.mStatusButton = finder.castView(view, 2131689697, "field 'mStatusButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onStatusButtonClick();
        }
      });
    view = finder.findRequiredView(source, 2131689698, "field 'mName'");
    target.mName = finder.castView(view, 2131689698, "field 'mName'");
    view = finder.findRequiredView(source, 2131689701, "field 'mPickUpTime'");
    target.mPickUpTime = finder.castView(view, 2131689701, "field 'mPickUpTime'");
    view = finder.findRequiredView(source, 2131689703, "field 'mDistance'");
    target.mDistance = finder.castView(view, 2131689703, "field 'mDistance'");
    view = finder.findRequiredView(source, 2131689705, "field 'mTripTime'");
    target.mTripTime = finder.castView(view, 2131689705, "field 'mTripTime'");
    view = finder.findRequiredView(source, 2131689707, "field 'mStatus'");
    target.mStatus = finder.castView(view, 2131689707, "field 'mStatus'");
    view = finder.findRequiredView(source, 2131689708, "field 'mCost'");
    target.mCost = finder.castView(view, 2131689708, "field 'mCost'");
    view = finder.findRequiredView(source, 2131689710, "field 'mMinusButton' and method 'divideCost'");
    target.mMinusButton = finder.castView(view, 2131689710, "field 'mMinusButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.divideCost();
        }
      });
    view = finder.findRequiredView(source, 2131689712, "field 'mPlusButton' and method 'splitCost'");
    target.mPlusButton = finder.castView(view, 2131689712, "field 'mPlusButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.splitCost();
        }
      });
    view = finder.findRequiredView(source, 2131689711, "field 'mSplitGas'");
    target.mSplitGas = finder.castView(view, 2131689711, "field 'mSplitGas'");
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
