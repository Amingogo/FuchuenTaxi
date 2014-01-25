package tw.com.fuchuen.taxi.fragment;

import tw.com.fuchuen.taxi.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class WebViewWithControlFragment extends SherlockFragment implements OnClickListener {
	
	public static String BUNDLE_EXTRA_URL = "com.evaair.homedelivery.extra.url";

	protected String mUrl;
	
	private WebView mWebView;
	
	private Button mBackBtn;
	private TextView mTitle;

	private ProgressBar mProgressBar;
	
	private ImageButton mBackButton;
	private ImageButton mForwardButton;
	private ImageButton mRefreshButton;


	public static WebViewWithControlFragment newInstance(String url) {
		return newInstance(url, true);
	}
	
	public static final WebViewWithControlFragment newInstance(String url, boolean isLoadHomeBtn) {
		WebViewWithControlFragment f = new WebViewWithControlFragment();
		
		// Supply url input as an argument.
		Bundle args = new Bundle();
		args.putString(BUNDLE_EXTRA_URL, url);
		f.setArguments(args);

		return f;
	}	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			if (getArguments().containsKey(BUNDLE_EXTRA_URL)) {
				mUrl = getArguments().getString(BUNDLE_EXTRA_URL);
			}
		}
	}

	@SuppressLint({ "SetJavaScriptEnabled", "HandlerLeak" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.webview_with_control, container, false);
		
		mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

		mWebView = (WebView) view.findViewById(R.id.webview_wv);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setJavaScriptEnabled(true);

		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.setWebChromeClient(new MyWebChromeClient());

		mBackButton = (ImageButton) view.findViewById(R.id.webview_back_btn);
		mBackButton.setOnClickListener(this);

		mForwardButton = (ImageButton) view.findViewById(R.id.webview_forward_btn);
		mForwardButton.setOnClickListener(this);

		mRefreshButton = (ImageButton) view.findViewById(R.id.webview_refresh_btn);
		mRefreshButton.setOnClickListener(this);

		return view;	 
	}

	@Override
	public void onResume() {
		mWebView.onResume();
		super.onResume();

		mWebView.loadUrl(mUrl);
	}

	/**
	 * Called when the fragment is no longer resumed. Pauses the WebView.
	 */
	@TargetApi(11)
	@Override
	public void onPause() {
		super.onPause();
		mWebView.onPause();
	}

	/**
	 * Called when the WebView has been detached from the fragment. The WebView
	 * is no longer available after this time.
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	/**
	 * Called when the fragment is no longer in use. Destroys the internal state
	 * of the WebView.
	 */
	@Override
	public void onDestroy() {
		if (mWebView != null) {
			mWebView.removeAllViews();
			mWebView.destroy();
			mWebView = null;
		}

		super.onDestroy();
	}

	protected void setActionBarTitle(int id) {
		mTitle.setText(id);
	}

	protected void setActionBarTitle(String title) {
		mTitle.setText(title);
	}
	
	protected void setActionBarBackBtn(int id) {
		mBackBtn.setText(id);
	}

	protected void setActionBarBackBtn(String title) {
		mBackBtn.setText(title);
	}
	
	private class MyWebViewClient extends WebViewClient {

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			mProgressBar.setVisibility(View.VISIBLE);
			mRefreshButton.setVisibility(View.GONE);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			return false;
		}

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed(); // Why ?
		}
	}

	
	private class MyWebChromeClient extends WebChromeClient {

		@Override
		public void onProgressChanged(WebView view, int progress) {
			if (progress == 100) {
				mProgressBar.setVisibility(View.INVISIBLE);
				mRefreshButton.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {}

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == mBackButton.getId() && mWebView.canGoBack()) { // 上一頁
			mWebView.goBack();
		} else if (v.getId() == mForwardButton.getId() && mWebView.canGoForward()) { // 下一頁
			mWebView.goForward();
		} else if (v.getId() == mRefreshButton.getId()) { // 重新整理
			mWebView.reload();
		}
	}

}
