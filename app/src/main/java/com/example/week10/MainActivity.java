package com.example.week10;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    EditText searchBar;
    Button search, refresh, previous, next, jsFin, jsEng;
    String urlString;
    ArrayList<String> urlPrevious = new ArrayList<>();
    ArrayList<String> urlNext = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        searchBar = findViewById(R.id.searchBar);
        search = findViewById(R.id.search);
        refresh = findViewById(R.id.refresh);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        jsFin = findViewById(R.id.jsFin);
        jsEng = findViewById(R.id.jsEng);

        webView.setWebViewClient(new WebViewClient());
        urlString = "http://www.google.fi";
        searchBar.setText(urlString);
        webView.loadUrl(urlString);
        webView.getSettings().setJavaScriptEnabled(true);

        /* Button's OnClick */
        View.OnClickListener listener = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (v == findViewById(R.id.search)){
                    urlPrevious.add(urlString);
                    urlString = searchBar.getText().toString();

                    if (urlString.equals("index.html")) {
                        urlString = "file:///android_asset/index.html";
                        searchBar.setText(urlString);
                        webView.loadUrl(urlString);

                    } else if (!urlString.contains("http://")){
                        urlString = "http://" + urlString;
                        searchBar.setText(urlString);
                        webView.loadUrl(urlString);
                    }

                } else if (v == findViewById(R.id.refresh)){
                    webView.loadUrl(urlString);

                } else if (v == findViewById(R.id.previous)){
                    if (urlPrevious.size() == 0){
                        Toast.makeText(MainActivity.this, "Edellistä sivua ei ole.", Toast.LENGTH_LONG).show();
                    } else {
                        urlNext.add(urlString);
                        for (int i = urlPrevious.size() - 1 ; i >= 0 ; i--) {
                            searchBar.setText(urlPrevious.get(i));
                            webView.loadUrl(urlPrevious.get(i));
                            urlPrevious.remove(i);
                        }
                    }
                } else if (v == findViewById(R.id.next)){
                    if (urlNext.size() == 0){
                        Toast.makeText(MainActivity.this, "Seuraavaa sivua ei ole.", Toast.LENGTH_LONG).show();
                    } else {
                        urlPrevious.add(urlString);
                        for (int i = urlNext.size() - 1 ; i >= 0 ; i--) {
                            searchBar.setText(urlNext.get(i));
                            webView.loadUrl(urlNext.get(i));
                            urlNext.remove(i);
                        }
                    }
                }
            }
        };
        search.setOnClickListener(listener);
        refresh.setOnClickListener(listener);
        previous.setOnClickListener(listener);
        next.setOnClickListener(listener);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void jsOnShout(View v){
        webView.evaluateJavascript("javascript:shoutOut()", null);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void jsInitialize(View v){
        webView.evaluateJavascript("javascript:initialize()", null);
    }
}
