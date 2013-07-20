package tw.com.fuchuen.taxi;

import tw.com.fuchuen.taxi.TakeTaxiNowActivity.TaxiHandler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;

public class OpeningActivity extends Activity{
	private static final String TAG = "OpeningActivity";
	
	private static final int GOTO_MAIN_MENU = 1;
	
	private static final int ABOUT_DRIVER = 1;
	
	private Context mContext;
	private TransHandler mHandler;
	private LayoutInflater mInflater;
	private ProgressDialog mProgressDialog;
	
	public class TransHandler extends Handler {
		public void handleMessage(Message msg) {
			switch (msg.what) { 
			case GOTO_MAIN_MENU:
//				mProgressDialog.dismiss();
				Intent intent = new Intent(mContext,TakeTaxiNowActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
				Log.d(TAG, "Go to main menu");
			}
			super.handleMessage(msg); 
		}
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.opening_layout);

        initBasicUtils();
//        showProgressDialog();
        goToMainMenu();
        
	}
	
	private void initBasicUtils() {
    	mContext = this;
    	mHandler = new TransHandler();
    	mInflater = LayoutInflater.from(mContext);
    }
	
	private void goToMainMenu() {
		Message msg = new Message();  
    	msg.what = GOTO_MAIN_MENU;
    	mHandler.sendMessageDelayed(msg, 1000);
	}
	
	private void showProgressDialog() {
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setCanceledOnTouchOutside(false);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.show();
	}
	
}
