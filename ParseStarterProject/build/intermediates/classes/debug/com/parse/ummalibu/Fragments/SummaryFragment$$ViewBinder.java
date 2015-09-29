// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SummaryFragment$$ViewBinder<T extends com.parse.ummalibu.fragments.SummaryFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689684, "field 'mMapImage'");
    target.mMapImage = finder.castView(view, 2131689684, "field 'mMapImage'");
    view = finder.findRequiredView(source, 2131689685, "field 'mTopLocationsLayout'");
    target.mTopLocationsLayout = finder.castView(view, 2131689685, "field 'mTopLocationsLayout'");
    view = finder.findRequiredView(source, 2131689686, "field 'mPickUpAddress'");
    target.mPickUpAddress = finder.castView(view, 2131689686, "field 'mPickUpAddress'");
    view = finder.findRequiredView(source, 2131689687, "field 'mDestAddress'");
    target.mDestAddress = finder.castView(view, 2131689687, "field 'mDestAddress'");
    view = finder.findRequiredView(source, 2131689688, "field 'mSlidingLayout'");
    target.mSlidingLayout = finder.castView(view, 2131689688, "field 'mSlidingLayout'");
    view = finder.findRequiredView(source, 2131689690, "field 'mCircleImageView'");
    target.mCircleImageView = finder.castView(view, 2131689690, "field 'mCircleImageView'");
    view = finder.findRequiredView(source, 2131689691, "field 'mContactButton' and method 'sendText'");
    target.mContactButton = finder.castView(view, 2131689691, "field 'mContactButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.sendText();
        }
      });
    view = finder.findRequiredView(source, 2131689692, "field 'mStatusButton'");
    target.mStatusButton = finder.castView(view, 2131689692, "field 'mStatusButton'");
    view = finder.findRequiredView(source, 2131689693, "field 'mName'");
    target.mName = finder.castView(view, 2131689693, "field 'mName'");
    view = finder.findRequiredView(source, 2131689696, "field 'mPickUpTime'");
    target.mPickUpTime = finder.castView(view, 2131689696, "field 'mPickUpTime'");
    view = finder.findRequiredView(source, 2131689698, "field 'mDistance'");
    target.mDistance = finder.castView(view, 2131689698, "field 'mDistance'");
    view = finder.findRequiredView(source, 2131689700, "field 'mTripTime'");
    target.mTripTime = finder.castView(view, 2131689700, "field 'mTripTime'");
    view = finder.findRequiredView(source, 2131689702, "field 'mStatus'");
    target.mStatus = finder.castView(view, 2131689702, "field 'mStatus'");
    view = finder.findRequiredView(source, 2131689703, "field 'mCost'");
    target.mCost = finder.castView(view, 2131689703, "field 'mCost'");
    view = finder.findRequiredView(source, 2131689705, "field 'mMinusButton' and method 'divideCost'");
    target.mMinusButton = finder.castView(view, 2131689705, "field 'mMinusButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.divideCost();
        }
      });
    view = finder.findRequiredView(source, 2131689707, "field 'mPlusButton' and method 'splitCost'");
    target.mPlusButton = finder.castView(view, 2131689707, "field 'mPlusButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.splitCost();
        }
      });
    view = finder.findRequiredView(source, 2131689706, "field 'mSplitGas'");
    target.mSplitGas = finder.castView(view, 2131689706, "field 'mSplitGas'");
  }

  @Override public void unbind(T target) {
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
