package garrick.example.servicecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Toast;

public class WebViewActivity extends Activity {

	private WebView webView;
	private final String webURL = "http://www.google.com";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		
		initView();
	}
	
	private void initView() {
		webView = (WebView) findViewById(R.id.webView);
		WebSettings websettings = webView.getSettings();
        websettings.setUseWideViewPort(true);
        websettings.setJavaScriptEnabled(true);
        websettings.setLoadWithOverviewMode(true);
        websettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); //把所有內容放到WebView組件等寬的一列中
        webView.loadUrl(webURL);
        webView.getSettings().setAppCacheEnabled(true); //設置啟動緩存
        webView.setWebChromeClient(new WebChromeClient() {
       	public void onProgressChanged(WebView view, int progress)
            {
       		 WebViewActivity.this.setProgress(progress * 100);
            }
       	 
        });
        
        webView.setWebViewClient(new WebViewClient() {
        	
        	public void onPageFinished(WebView view, String url) {
            }
        	
        	@Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
        		System.out.println("url:"+url);
        		Toast.makeText(WebViewActivity.this, "測試取得連結:" + url, Toast.LENGTH_LONG).show();
        		
        		if (url.equals("https://www.youtube.com/?gl=TW")) {
        			return true; // interrupt
        		} else {
        			return super.shouldOverrideUrlLoading(view, url); // normal
        		}
        		
//        		return super.shouldOverrideUrlLoading(view, url);
//        		return true;
            }
        	
    	});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	Intent intent = new Intent(WebViewActivity.this, MainActivity.class);
	    	startActivity(intent);
	    	WebViewActivity.this.finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
