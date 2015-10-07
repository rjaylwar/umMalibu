// Generated code from Butter Knife. Do not modify!
package com.parse.ummalibu;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends com.parse.ummalibu.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689676, "field 'mCircleImageView'");
    target.mCircleImageView = finder.castView(view, 2131689676, "field 'mCircleImageView'");
    view = finder.findRequiredView(source, 2131689679, "field 'mNameLayout'");
    target.mNameLayout = finder.castView(view, 2131689679, "field 'mNameLayout'");
    view = finder.findRequiredView(source, 2131689677, "field 'mEmailLayout'");
    target.mEmailLayout = finder.castView(view, 2131689677, "field 'mEmailLayout'");
    view = finder.findRequiredView(source, 2131689681, "field 'mPhoneLayout'");
    target.mPhoneLayout = finder.castView(view, 2131689681, "field 'mPhoneLayout'");
    view = finder.findRequiredView(source, 2131689683, "field 'mImageUrlLayout'");
    target.mImageUrlLayout = finder.castView(view, 2131689683, "field 'mImageUrlLayout'");
    view = finder.findRequiredView(source, 2131689685, "field 'mCarLayout'");
    target.mCarLayout = finder.castView(view, 2131689685, "field 'mCarLayout'");
    view = finder.findRequiredView(source, 2131689687, "field 'mMpgLayout'");
    target.mMpgLayout = finder.castView(view, 2131689687, "field 'mMpgLayout'");
  }

  @Override public void unbind(T target) {
    target.mCircleImageView = null;
    target.mNameLayout = null;
    target.mEmailLayout = null;
    target.mPhoneLayout = null;
    target.mImageUrlLayout = null;
    target.mCarLayout = null;
    target.mMpgLayout = null;
  }
}
