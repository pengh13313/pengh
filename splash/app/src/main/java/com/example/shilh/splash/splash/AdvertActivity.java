package com.example.shilh.splash.splash;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.shilh.splash.MainActivity;
import com.example.shilh.splash.R;

public class AdvertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);

        //隐藏标题栏
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.hide();
        }

        Button btnExit = (Button)findViewById(R.id.bt_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(AdvertActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        WebView webAd = (WebView)findViewById(R.id.web_ad);
        WebSettings webSettings = webAd.getSettings();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(true);
        }
        webAd.setWebViewClient(new WebViewClient());
        webAd.loadUrl("https://www.baidu.com");
    }
}
