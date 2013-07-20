package tw.com.fuchuen.taxi;

import com.google.ads.*;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;

public class TakeTaxiNowActivity extends Activity {
	private static final String TAG = "TakeTaxiNowActivity";
	
	private static final int DISMISS_OPENING_DIALOG = 1;
	
	private static final int ABOUT_DRIVER = 1;
	
	private Context mContext;
	private TaxiHandler mHandler;
	private LayoutInflater mInflater;
	
	private Dialog openingDialog;
	
	private AdView adView;
	private LinearLayout adLayout;
	
	class TaxiHandler extends Handler {
		public void handleMessage(Message msg) {
			switch (msg.what) { 
			case DISMISS_OPENING_DIALOG:
				openingDialog.dismiss();
				Log.d(TAG, "Dismiss Opening View");
			}
			super.handleMessage(msg); 
		}
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_menu_layout);
        
        initBasicUtils();
//        showOpeningDialog();
        setupButtons();
        putAd();
        Gallery gallery = (Gallery) findViewById(R.id.photo_gallery);
        GalleryImageAdapter imageAdapter = new GalleryImageAdapter(this);
        gallery.setAdapter(imageAdapter);
        gallery.setSelection(1, false);
    }
    
    private void initBasicUtils() {
    	mContext = this;
    	mHandler = new TaxiHandler();
    	mInflater = LayoutInflater.from(mContext);
    }
    
    private void showOpeningDialog() {
    	openingDialog = new Dialog(this,android.R.style.Theme_NoTitleBar_Fullscreen);
    	openingDialog.setContentView(R.layout.opening_layout);
    	openingDialog.show();
    	Log.d(TAG, "Show Opening View");
    	
    	dismissOpeningDialog();
    }
    
    private void dismissOpeningDialog() {
    	Message msg = new Message();  
    	msg.what = DISMISS_OPENING_DIALOG;
    	mHandler.sendMessageDelayed(msg, 3000);
    }
    
    private void setupButtons() {
    	Button callBtn = (Button)findViewById(R.id.callCar_btn);
    	callBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(mContext,CallCarActivity.class);
				startActivity(intent);
				Log.d(TAG, "Go CallCar Activity");
			}
    	});
    	
    	Button lookUpBtn = (Button)findViewById(R.id.lookup_btn);
    	lookUpBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(mContext,LookUpActivity.class);
				startActivity(intent);
				Log.d(TAG, "Go LookUp Activity");
			}
    	});
    	
    	Button tripInfoBtn = (Button)findViewById(R.id.tripInfo_btn);
    	tripInfoBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(mContext,TripInfoActivity.class);
				startActivity(intent);
				Log.d(TAG, "Go TripInfo Activity");
			}
    	});
    	
    	Button aboutMeBtn = (Button)findViewById(R.id.aboutMe_btn);
    	aboutMeBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(mContext,AboutMeActivity.class);
				startActivity(intent);
				Log.d(TAG, "Go About Me Activity");
			}
    	});
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    menu.add(0, ABOUT_DRIVER, 0, getString(R.string.main_menu_about_menu));
	    Log.d(TAG, "Open Menu");
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    super.onOptionsItemSelected(item);
	    switch(item.getItemId()){
	    	case ABOUT_DRIVER:
	    		Intent intent = new Intent(mContext,AboutMeActivity.class);
				startActivity(intent);
				Log.d(TAG, "Go AboutMe Activity");
	    		break;
	    }
	    return true;
	}

	private void putAd() {
		adView = new AdView(this, AdSize.BANNER, getString(R.string.id));
		adLayout = (LinearLayout)findViewById(R.id.ad_layout);
	    adLayout.addView(adView);
	    adView.loadAd(new AdRequest());
	}
}