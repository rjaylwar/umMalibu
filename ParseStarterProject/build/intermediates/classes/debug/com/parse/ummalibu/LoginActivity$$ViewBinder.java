// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends com.parse.ummalibu.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689683, "field 'mCircleImageView'");
    target.mCircleImageView = finder.castView(view, 2131689683, "field 'mCircleImageView'");
    view = finder.findRequiredView(source, 2131689686, "field 'mNameLayout'");
    target.mNameLayout = finder.castView(view, 2131689686, "field 'mNameLayout'");
    view = finder.findRequiredView(source, 2131689684, "field 'mEmailLayout'");
    target.mEmailLayout = finder.castView(view, 2131689684, "field 'mEmailLayout'");
    view = finder.findRequiredView(source, 2131689688, "field 'mPhoneLayout'");
    target.mPhoneLayout = finder.castView(view, 2131689688, "field 'mPhoneLayout'");
    view = finder.findRequiredView(source, 2131689690, "field 'mImageUrlLayout'");
    target.mImageUrlLayout = finder.castView(view, 2131689690, "field 'mImageUrlLayout'");
    view = finder.findRequiredView(source, 2131689692, "field 'mCarLayout'");
    target.mCarLayout = finder.castView(view, 2131689692, "field 'mCarLayout'");
    view = finder.findRequiredView(source, 2131689694, "field 'mMpgLayout'");
    target.mMpgLayout = finder.castView(view, 2131689694, "field 'mMpgLayout'");
    view = finder.findRequiredView(source, 2131689617, "field 'mDrawerLayout'");
    target.mDrawerLayout = finder.castView(view, 2131689617, "field 'mDrawerLayout'");
    view = finder.findRequiredView(source, 2131689625, "field 'mNavigationView'");
    target.mNavigationView = finder.castView(view, 2131689625, "field 'mNavigationView'");
  }

  @Override public void unbind(T target) {
    target.mCircleImageView = null;
    target.mNameLayout = null;
    target.mEmailLayout = null;
    target.mPhoneLayout = null;
    target.mImageUrlLayout = null;
    target.mCarLayout = null;
    target.mMpgLayout = null;
    target.mDrawerLayout = null;
    target.mNavigationView = null;
  }
}
