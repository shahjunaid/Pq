package art.kashmir.com.pq;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class web extends AppCompatActivity {
    WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              web.super.onBackPressed();
              //Toast.makeText(web.this, "back", Toast.LENGTH_SHORT).show();
          }
      });

        myWebView=findViewById(R.id.webView);
        myWebView.loadUrl(getIntent().getStringExtra("url"));

    }

}
