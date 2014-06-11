package tw.com.fuchuen.taxi.fragment;

import java.util.Locale;

import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.taxi.config.TaxiLocalConfig;
import tw.com.fuchuen.utils.CustomUtils;
import tw.com.fuchuen.utils.UtilsStorage;
import tw.com.fuchuen.utils.UtilsToast;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EmailFragment extends FormFragment {
	
	public static Fragment newInstance() {
		EmailFragment fragment = new EmailFragment();
		return fragment;
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = inflater.getContext();
		mMainView = inflater.inflate(R.layout.fragment_email_layout, null, false);
		return mMainView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setupUpTitle();
	}
	
	@Override
	protected void setupSendButton() {
		((Button)mMainView.findViewById(R.id.send)).setText(R.string.email_send);
		((Button)mMainView.findViewById(R.id.send)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int[] dateArray = CustomUtils.getDateArray(mContext);
				final int[] timeArray = CustomUtils.getTimeArray(mContext);
				String passengerName = UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_PASSENGER_NAME, "");
				String passengerCount = (UtilsStorage.getSharedPreferences(mContext).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_PASSENGER_COUNT_POSITION, 0)+1)+mContext.getString(R.string.people);
				String date = dateArray[0]+"/"+(dateArray[1]+1)+"/"+dateArray[2];
				String time = timeArray[0]+":"+timeArray[1];
				String start = UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_START_PLACE, "");
				String destination = UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_DESTINATION, "");
				
				if(passengerName.equals("") || start.equals("") || destination.equals("")) {
					UtilsToast.showLongToastMsg(mContext.getApplicationContext(), mContext.getString(R.string.leak_info), true);
					return;
				}
				
				String title = String.format(mContext.getString(R.string.driver_email_title), passengerName, date, time);
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+getString(R.string.driver_email)));
				intent.putExtra(Intent.EXTRA_SUBJECT, title);
				intent.putExtra(Intent.EXTRA_TEXT,
						CustomUtils.getMessageString(mContext,
						passengerName,
						passengerCount,
						date,
						time,
						start,
						destination));
				startActivity(intent);
			}
		});
	}
	
	private void setupUpTitle() {
		mUpTitleTextView = (TextView)mMainView.findViewById(R.id.email_hint_title);
		String orignalStr = mContext.getString(R.string.email_hint_title);
		Spannable span = new SpannableString(orignalStr);
		if(Locale.getDefault().equals(Locale.CHINESE) || Locale.getDefault().equals(Locale.TAIWAN) || Locale.getDefault().equals(Locale.CHINA)) {
			span.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.large_word_size)),
					7, orignalStr.length()-5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		} else {
			span.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.large_word_size)),
					19, orignalStr.length()-13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		mUpTitleTextView.setText(span);
	}
	
}
