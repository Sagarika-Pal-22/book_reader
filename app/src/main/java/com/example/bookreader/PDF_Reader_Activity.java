package com.example.bookreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.Locale;

public class PDF_Reader_Activity extends AppCompatActivity {

    WebView webview;
    String type,book_url,preview_url;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f__reader_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("book_name"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);
        webview = findViewById(R.id.webview);

        progressBar.setVisibility(View.VISIBLE);

        book_url=getIntent().getExtras().getString("pdf_url");
        preview_url=getIntent().getExtras().getString("preview_url");

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setSupportZoom(true);
        webview.setHorizontalScrollBarEnabled(true);
        webview.setHorizontalFadingEdgeEnabled(true);
        webview.setVerticalFadingEdgeEnabled(false);
        webview.setVerticalScrollBarEnabled(false);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webview.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
//                pDialog.show();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.getSettings().setLoadsImagesAutomatically(true);
                webview .loadUrl("javascript:(function() {document.querySelector('[class=\"ndfHFb-c4YZDc-Wrql6b\"]').remove();})()");
//                pDialog.dismiss();
                if(progressBar!=null && progressBar.isShown()){
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

        type=getIntent().getExtras().getString("type");
        if(type.equals("book_pdf")){
            webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + book_url);
        }else {
            webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + preview_url);
        }


//        webview.setOnTouchListener(new OnSwipeTouchScreen() {
//            public void onSwipeRight() {
//
//                //functioning on right swipe
//            }
//
//            public void onSwipeLeft() {
//                //functioning on left swipe
//            }
//
//        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}