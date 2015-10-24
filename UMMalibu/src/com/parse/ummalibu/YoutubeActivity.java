package com.parse.ummalibu;

import com.google.android.youtube.player.YouTubeBaseActivity;

/**
 * Created by rjaylward on 10/23/15
 */
public class YoutubeActivity extends YouTubeBaseActivity {

//    private android.app.Fragment mFragment;
//    private static final String FRAGMENT_TAG = "frag_tag";
//    private OnActivityBackPressedListener mListener;
//
//    public static Intent createIntent(Context context) {
//        return new Intent(context, YoutubeActivity.class);
//    }
//
//    public int getLayoutId() {
//        return R.layout.activity_fragment;
//    }
//
//    public void initLayout() {
//        mFragment = getFragment();
//        mFragment.setArguments(getIntent().getExtras());
//
//        if(mFragment != null) {
//            getFragmentManager().beginTransaction()
//                    .replace(R.id.root, mFragment, FRAGMENT_TAG)
//                    .commit();
//        }
//    }
//
//    private android.app.Fragment getFragment() {
//        return WorshipYoutubeFragment.createFragment();
//    }
//
//    public void setOnActivityBackPressedListener(OnActivityBackPressedListener listener) {
//        mListener = listener;
//    }
//
//    public interface OnActivityBackPressedListener {
//        void onActivityBackPressed();
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//        if(mListener != null)
//            mListener.onActivityBackPressed();
//    }
//
//
//    private ProgressDialog mProgressDialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(getLayoutId());
//        initLayout();
//    }
//
//    public void hideKeyboard() {
//        View view = getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
////            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//    }
//
//    public Point getScreenSize() {
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//
//        return size;
//    }
//
//    public void showProgressDialog(int msgResId) {
//        showProgressDialog(getString(msgResId));
//    }
//
//    public void showProgressDialog(String msg) {
//        mProgressDialog = ProgressDialogHelper.buildDialog(this, msg);
//        mProgressDialog.show();
//    }
//
//    public void hideProgressDialog() {
//        if (mProgressDialog != null)
//            mProgressDialog.dismiss();
//
//    }

}
