package com.parse.ummalibu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewFragment extends Fragment {

    private Bundle webviewBundle;
    private WebView webView1;

    public WebviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //  this.setRetainInstance(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_webview, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String url;
        if (getArguments() != null) {
        url = getArguments().getString("url");}
        else {url = "http://www.google.com";}

        webView1 = (WebView) getActivity().findViewById(R.id.webViewz);
        WebSettings settings = webView1.getSettings();
        settings.setJavaScriptEnabled(true);
        //settings.setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
        //webView1.loadUrl(url);

        if (webviewBundle == null) {
        webView1.loadUrl(url);
        webView1.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        }); }

        else {
            webView1.restoreState(webviewBundle);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        webviewBundle = new Bundle();
        webView1.saveState(webviewBundle);
    }
}
