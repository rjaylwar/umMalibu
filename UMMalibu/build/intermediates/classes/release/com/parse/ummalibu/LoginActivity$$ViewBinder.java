// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends com.parse.ummalibu.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689700, "field 'mCircleImageView'");
    target.mCircleImageView = finder.castView(view, 2131689700, "field 'mCircleImageView'");
    view = finder.findRequiredView(source, 2131689703, "field 'mNameLayout'");
    target.mNameLayout = finder.castView(view, 2131689703, "field 'mNameLayout'");
    view = finder.findRequiredView(source, 2131689701, "field 'mEmailLayout'");
    target.mEmailLayout = finder.castView(view, 2131689701, "field 'mEmailLayout'");
    view = finder.findRequiredView(source, 2131689705, "field 'mPhoneLayout'");
    target.mPhoneLayout = finder.castView(view, 2131689705, "field 'mPhoneLayout'");
    view = finder.findRequiredView(source, 2131689707, "field 'mImageUrlLayout'");
    target.mImageUrlLayout = finder.castView(view, 2131689707, "field 'mImageUrlLayout'");
    view = finder.findRequiredView(source, 2131689709, "field 'mCarLayout'");
    target.mCarLayout = finder.castView(view, 2131689709, "field 'mCarLayout'");
    view = finder.findRequiredView(source, 2131689711, "field 'mMpgLayout'");
    target.mMpgLayout = finder.castView(view, 2131689711, "field 'mMpgLayout'");
    view = finder.findRequiredView(source, 2131689612, "field 'mDrawerLayout'");
    target.mDrawerLayout = finder.castView(view, 2131689612, "field 'mDrawerLayout'");
    view = finder.findRequiredView(source, 2131689620, "field 'mNavigationView'");
    target.mNavigationView = finder.castView(view, 2131689620, "field 'mNavigationView'");
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
