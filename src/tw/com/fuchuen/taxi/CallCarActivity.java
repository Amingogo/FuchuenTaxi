package tw.com.fuchuen.taxi;

import tw.com.fuchuen.taxi.email.MainEmailActivity;
import tw.com.fuchuen.taxi.phone.MainPhoneCallActivity;
import tw.com.fuchuen.taxi.sms.MainSmsActivity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class CallCarActivity extends TabActivity{
	private static final String TAG = "CallCarActivity";
	private static final int EMAIL = 1;
	private static final int MMS = 2;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.call_car_main_layout);
        
        setupTabView();
	}
	
	private void setupTabView() {
		Log.d(TAG, "Setup TabView!");
		//phone tab
		View tab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
		ImageView image = (ImageView) tab.findViewById(R.id.icon);
		TextView text = (TextView) tab.findViewById(R.id.text);

		image.setImageResource(R.drawable.icon_phone_32);
		text.setText(R.string.phone_btn);
		
		Intent intent = new Intent().setClass(this, MainPhoneCallActivity.class);
		
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec = tabHost.newTabSpec(getString(R.string.phone_btn)).setIndicator(tab).setContent(intent);
		tabHost.addTab(spec);
		
		//email tab
		tab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
		image = (ImageView) tab.findViewById(R.id.icon);
		text = (TextView) tab.findViewById(R.id.text);

		image.setImageResource(R.drawable.icon_email_32);
		text.setText(R.string.email_btn);
		
		intent = new Intent().setClass(this, MainEmailActivity.class);
		intent.putExtra("choice", EMAIL);
		
		tabHost = getTabHost();
		spec = tabHost.newTabSpec(getString(R.string.email_btn)).setIndicator(tab).setContent(intent);
		tabHost.addTab(spec);

		//sms tab
		tab = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
		image = (ImageView) tab.findViewById(R.id.icon);
		text = (TextView) tab.findViewById(R.id.text);

		image.setImageResource(R.drawable.icon_message_32);
		text.setText(R.string.mms_btn);
		
		intent = new Intent().setClass(this, MainSmsActivity.class);
		intent.putExtra("choice", MMS);
		
		tabHost = getTabHost();
		spec = tabHost.newTabSpec(getString(R.string.mms_btn)).setIndicator(tab).setContent(intent);
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(0);
	}
}
