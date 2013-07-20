package tw.com.fuchuen.taxi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutMeActivity extends Activity{
	private static final String TAG = "AboutMeActivity";
	String myURL = "http://www.wretch.cc/blog/taxitw/22430744";
	
	private ProgressDialog mProgressDialog;
	private WebView aboutMe;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about_me_layout);
        
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(R.string.loading_title);
        mProgressDialog.setMessage(getString(R.string.loading));
        
        aboutMe = (WebView)findViewById(R.id.aboutMeWebView);
        WebSettings websettings = aboutMe.getSettings();  
        websettings.setSupportZoom(true);  
        websettings.setBuiltInZoomControls(true);   
        websettings.setJavaScriptEnabled(true);  

        aboutMe.setWebViewClient(new myWebViewClient());  
  
        aboutMe.loadUrl(myURL); 
	}
	
	private class myWebViewClient extends WebViewClient {
		@Override  
        public void onPageStarted(WebView view, String url, Bitmap favicon) {  
			mProgressDialog.show();
            super.onPageStarted(view, url, favicon);  
        }  
		@Override  
        public boolean shouldOverrideUrlLoading(WebView view, String url) {  
            view.loadUrl(url);  
            return true;  
        } 
		@Override  
        public void onPageFinished(WebView view, String url) {  
			mProgressDialog.dismiss();
            super.onPageFinished(view, url);  
        } 
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
            case KeyEvent.KEYCODE_BACK:
                if(aboutMe.canGoBack() == true){
                	aboutMe.goBack();
                }else{
                	if(mProgressDialog != null && mProgressDialog.isShowing())
            			mProgressDialog.dismiss();
                    finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
	
	@Override
	public void onPause() {
		super.onPause();
		if(mProgressDialog != null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}
}