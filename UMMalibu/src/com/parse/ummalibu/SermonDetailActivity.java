package com.parse.ummalibu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rjaylward on 12/15/14
 */
public class SermonDetailActivity extends FragmentActivity implements View.OnTouchListener {

    private Button btn;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private boolean intialStage = true;

    WebView webView1;
    EditText editText;
    String title;
    ProgressBar Pbar;
    TextView loadingTextView;
    Boolean mediaPlayerEnabled;
    SeekBar seek_bar;
    Handler seekHandler;
    String sourceLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(android.R.style.Theme_Holo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sermon_detail_layout);

        Intent i = getIntent();

        mediaPlayerEnabled = false;

        //getSupportFragmentManager().findFragmentById(R.id.webview_fragment).setRetainInstance(true);

        String linkUrl = i.getStringExtra("linkUrl");
        //String linkUrl = "http://ummalibu.com/post/104964480153/audio_player_iframe/ummalibu/tumblr_ngg12g8dAo1tx13jp?audio_file=http%3A%2F%2Fs3.amazonaws.com%2Fchurchplantmedia-cms%2Fmalibu_presbyterian_church_malibu_ca%2F12-2-14_home-for-the-holidays_your-family-system.mp3&color=white";
        title = i.getStringExtra("title");
        String datePreached = i.getStringExtra("datePreached");
        String series = i.getStringExtra("series");
        sourceLink = i.getStringExtra("sourceLink");

        loadingTextView = (TextView) findViewById(R.id.loading_text);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);

        webView1 = (WebView) findViewById(R.id.webView);
        webView1.setVisibility(WebView.GONE);
        WebSettings settings = webView1.getSettings();
        settings.setJavaScriptEnabled(true);
        //settings.setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");

        Pbar = (ProgressBar) findViewById(R.id.progressB);
        Pbar.setMax(100);

        if ("direct".equals(i.getStringExtra("source"))) {mediaPlayerEnabled = true;}

        if (mediaPlayerEnabled) {
            TextView mediaPlayerTextView = (TextView) findViewById(R.id.textView);
            ImageView sermonImage = (ImageView) findViewById(R.id.imageView);

            if (i.getStringExtra("imageUrl")!= null){
            new DownloadImageTask(sermonImage)
                    .execute(i.getStringExtra("imageUrl"));} else {
            sermonImage.setVisibility(ImageView.GONE);}

            mediaPlayerTextView.setText(Html.fromHtml(i.getStringExtra("mediaPlayerText")));

            btn = (Button) findViewById(R.id.play_pause_button);
            seek_bar = (SeekBar) findViewById(R.id.seekBar);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            btn.setOnClickListener(pausePlay);
            seekHandler = new Handler();
            Pbar.setVisibility(ProgressBar.GONE);
            seek_bar.setMax(mediaPlayer.getDuration());
            loadingTextView.setVisibility(TextView.GONE);

            seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(mediaPlayer != null && fromUser){
                        mediaPlayer.seekTo(progress);
                    }
                }
            });

        } else {

            webView1.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {

                    Pbar.setProgress(progress);
                    if (progress == 100) {
                        Pbar.setVisibility(ProgressBar.GONE);
                        loadingTextView.setVisibility(TextView.GONE);
                        webView1.setVisibility(WebView.VISIBLE);
                    }
                }
            });

            webView1.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    //view.scrollTo(0, 475);
                }
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    //error
                }
            });

            RelativeLayout mediaPlayerLayout = (RelativeLayout) findViewById(R.id.media_player_layout);
            mediaPlayerLayout.setVisibility(RelativeLayout.GONE);
            if (haveNetworkConnection()) {
                webView1.loadUrl(linkUrl);
            } else {
                loadingTextView.setText("  - SERMON UNAVAILABLE - \nNO NETWORK CONNECTION");
                Pbar.setVisibility(ProgressBar.GONE);
            }
        }
            // Add the title view
            TextView titleTextView = (TextView) findViewById(R.id.titleLabel);
            titleTextView.setText(title);

            TextView datePreachedTextView = (TextView) findViewById(R.id.datelabel);
            datePreachedTextView.setText(datePreached);

            //webView1.loadUrl("http://ummalibu.com/post/104964480153/wow-if-you-missed-this-talk-because-it-was-dead");

            editText = (EditText) findViewById(R.id.edit_text);
            ImageView ImageView = (ImageView) findViewById(R.id.graphicView);

            if (getSizeName(this).equals("small") || getSizeName(this).equals("normal")) {
                ImageView.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
            }

            if ("youtube".equals(i.getStringExtra("source"))) {

                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                float logicalDensity = metrics.density;

                int px = (int) Math.ceil(230 * logicalDensity);

                ImageView.setVisibility(android.widget.ImageView.GONE);
                webView1.getLayoutParams().height = px;
            }

            if (series.equalsIgnoreCase("artofdis")) {
                ImageView.setImageResource(R.drawable.artofdis);
            }

            if (series.equalsIgnoreCase("beloved")) {
                ImageView.setImageResource(R.drawable.beloved);
            }

            //not sure where this goes...
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        hideKeyboard(v);
                    }
                }
            });
            loadSavedPreferences();
        }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String notes = sharedPreferences.getString(title, "");
        if (notes.length() > 1) {
            editText.setText(notes);
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (haveNetworkConnection()){
                webView1.loadUrl("about:blank");}
                savePreferences(title, editText.getText().toString());
                super.onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        webView1.loadUrl("about:blank");
        savePreferences(title, editText.getText().toString());
        if (mediaPlayer != null) {
            seekHandler.removeCallbacks(run);
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onBackPressed();
    }

    private static String getSizeName(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return "large";
            case 4: // Configuration.SCREENLAYOUT_SIZE_XLARGE is API >= 9
                return "xlarge";
            default:
                return "undefined";
        }
    }

    //////////////////////////////////////////////// media player methods ////////////////////////////////////////////////

    Runnable run = new Runnable() { @Override public void run() { seekUpdation(); } };

    public void seekUpdation() {
        seek_bar.setProgress(mediaPlayer.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
        }

    private View.OnClickListener pausePlay = new View.OnClickListener() {
        //  "http://www.virginmegastore.me/Library/Music/CD_001214/Tracks/Track1.mp3"
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // TODO Auto-generated method stub

            if (!playPause) {
                btn.setBackgroundResource(R.drawable.button_pause);
                if (intialStage)
                    new Player()
                            .execute(sourceLink);
                else {
                    if (!mediaPlayer.isPlaying())
                        mediaPlayer.start();
                        seekUpdation();
                }
                playPause = true;
            } else {
                btn.setBackgroundResource(R.drawable.button_play);
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                    seekHandler.removeCallbacks(run);
                playPause = false;
            }
        }
    };

    // * preparing mediaplayer will take sometime to buffer the content so prepare it inside the background thread and starting it on UI thread.
    // * @author piyush
    // *


    class Player extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progress;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Boolean prepared;
            try {

                mediaPlayer.setDataSource(params[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        intialStage = true;
                        playPause=false;
                        btn.setBackgroundResource(R.drawable.button_play);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.prepare();
                prepared = true;
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                Log.d("IllegarArgument", e.getMessage());
                prepared = false;
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (progress.isShowing()) {
                progress.cancel();
            }
            Log.d("Prepared", "//" + result);
            seekUpdation();
            mediaPlayer.start();
            seek_bar.setMax(mediaPlayer.getDuration());
            intialStage = false;
        }

        public Player() {
            progress = new ProgressDialog(SermonDetailActivity.this);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.progress.setMessage("Buffering...");
            this.progress.show();
        }
    }

//    @Override
//    protected void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//        if (mediaPlayer != null) {
//            seekHandler.removeCallbacks(run);
//            mediaPlayer.reset();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
            bmImage.setVisibility(ImageView.GONE);
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            bmImage.setVisibility(ImageView.VISIBLE);
        }
    }
}
