package com.shane.me.shanedemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.util.FileDownloader;

/**
 * Created by luckyshane on 2018/3/15.
 */

public class WebViewDemoActivity extends BaseActivity {
    WebView webView;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_webview_demo);

        String imageUrl = "https://upload-images.jianshu.io/upload_images/944365-207a738cb165a2da.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700";

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);

        TextView urlTv = findViewById(R.id.url_tv);
        urlTv.setText(imageUrl);
        loadUrl(imageUrl);
    }

    private void loadUrl(final String imageUrl) {
        final String savePath = FileDownloader.getFileSavePath(this, imageUrl);
        String path = "file://" + savePath;
        final String html = "<html><head></head><body> <p>Hello </p><img src=\"" + path + "\" width=\"200\" height=\"200\"></body></html>";

       // webView.loadData(html, "text/html; charset=utf-8", "UTF-8");
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success = FileDownloader.donwload(imageUrl, savePath);
                if (success) {
                    Log.d(TAG, "download success: " + savePath);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
                            //webView.loadData(html, "text/html; charset=utf-8", "UTF-8");
                        }
                    });
                }
            }
        }).start();
    }


}
