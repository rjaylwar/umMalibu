package com.parse.ummalibu;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ummalibu.base.ToolbarActivity;


public class WorshipActivity extends ToolbarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private SongsTableAdapter mainAdapter;
    Button submitButton;
    WebView webView;
    ImageView worshipImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worship);

        addListenerOnButton();

        mainAdapter = new SongsTableAdapter(this);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();

        webView = (WebView) findViewById(R.id.webView);
        worshipImageView = (ImageView) findViewById(R.id.worship_image);

        if (!haveNetworkConnection()){
            worshipImageView.setImageResource(R.drawable.band_screenshot);
            worshipImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            webView.setVisibility(WebView.GONE);
        }

        //final Activity activity = this;

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT < 19) {
                settings.setPluginState(WebSettings.PluginState.ON);
        }

        webView.loadUrl("http://www.youtube.com/embed/WhmdJeHsd6Y?list=PLi1lg8EgWuZREIU5YyX13mistBCRIhtZ2");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //error
            }
        });

        //String linkUrl = "http://www.youtube.com/embed/WhmdJeHsd6Y?list=PLi1lg8EgWuZREIU5YyX13mistBCRIhtZ2";
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_worship, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ParseObject queryObject = mainAdapter.getItem(position);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(queryObject.getString("youtubeUrl"))));
    }

    public void addListenerOnButton() {
        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText requestBox = (EditText) findViewById(R.id.request_box);
                Uri uri = Uri.parse("smsto:umworship@gmail.com");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", requestBox.getText());
                startActivity(it);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                webView.loadUrl("about:blank");
                super.onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        webView.loadUrl("about:blank");
        super.onBackPressed();
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
}
