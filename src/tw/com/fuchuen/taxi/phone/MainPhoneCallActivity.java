package tw.com.fuchuen.taxi.phone;

import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.utils.BasicUtils;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MainPhoneCallActivity extends Activity{
	private static final String TAG = "MainPhoneCallActivity";
	
	private Context mContext;
	private LayoutInflater mInflater;
	
	private AdView adView;
	private LinearLayout adLayout;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_phone_call);
        
        initBasicUtils();
        setupButtons();
        putAd();
	}
	
	private void initBasicUtils() {
    	mContext = this;
    	mInflater = LayoutInflater.from(mContext);
    }

	private void setupButtons() {
    	Button phoneBtn = (Button)findViewById(R.id.phoneCallBtn);
    	phoneBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RelativeLayout dialogView = (RelativeLayout)mInflater.inflate(R.layout.two_btn_dialog_layout, null);
				Dialog callDialog = BasicUtils.getDialog(mContext, dialogView,getString(R.string.phone_btn));
				
				Button cancelBtn = (Button)dialogView.findViewById(R.id.cancel_btn);
				Button dialBtn = (Button)dialogView.findViewById(R.id.ok_btn);
				setupCancelButton(callDialog,cancelBtn);
				setupDialButton(callDialog,dialBtn);
				
				callDialog.show();
				Log.d(TAG, "Show Phone Call Dialog");
			}
    	});
	}
	
	private void setupCancelButton(final Dialog mDialog, Button cancel) {
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mDialog.dismiss();
			}
		});
	}
	
	private void setupDialButton(final Dialog mDialog, Button dial) {
		dial.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+getString(R.string.phone_number)));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				mDialog.dismiss();
				Log.d(TAG, "Call Out");
			}
		});
	}
	
	private void putAd() {
		adView = new AdView(this, AdSize.BANNER, getString(R.string.id));
		adLayout = (LinearLayout)findViewById(R.id.ad_layout);
	    adLayout.addView(adView);
	    adView.loadAd(new AdRequest());
	}
}
