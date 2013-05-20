package kibow.games.casinohills.roulette.acitivty;

import java.util.HashMap;
import java.util.Map;

import kibow.games.casinohills.roulette.screen.GameEntity;
import kibow.networkmanagement.network.ConnectionHandler;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

;

public class WebviewHelpPage extends Activity implements OnClickListener {

    WebView mWebView;
    Bundle b;
    Button btnBack;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.webview_help);
        btnBack = (Button) findViewById(R.id.btn_back_webview);
        btnBack.setOnClickListener(this);
        b = getIntent().getExtras();
        if (b != null) {
            id = b.getInt("idButton");
        }
        mWebView = (WebView) findViewById(R.id.webbiew_help);
        loadWebView(id);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                if (id == 0) {
                    btnBack.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view,
                                                  HttpAuthHandler handler, String host, String realm) {
                handler.proceed(ConnectionHandler.SSL_USERNAME,
                        ConnectionHandler.SSL_PASSWORD);
            }

            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    void loadWebView(int id) {
        String up = ConnectionHandler.SSL_USERNAME + ":"
                + ConnectionHandler.SSL_PASSWORD;
        String authEncoded = new String(Base64.encode(up.getBytes(),
                Base64.NO_WRAP));
        String authHeader = "Basic " + authEncoded;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authHeader);

        switch (id) {
            case 0:
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mWebView.loadUrl("file:///android_asset/webview/tutorial/index.html");
                break;
            case 1:
                mWebView.loadUrl("file:///android_asset/webview/help/index.html");
                break;
            case 2:
                break;
            case 3:
                mWebView.loadUrl("file:///android_asset/webview/policy/index.html");
                break;
            case 4:
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mWebView.loadUrl("https://ch.moya.in/_api/roulette/"
                        + GameEntity.GAME_ID + "/history?user_id="
                        + GameEntity.getInstance().userComponent.id + "&token="
                        + GameEntity.getInstance().userComponent.token);
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        this.finish();
    }

}
