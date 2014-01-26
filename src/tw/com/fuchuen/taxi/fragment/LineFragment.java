package tw.com.fuchuen.taxi.fragment;

import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.taxi.config.TaxiLocalConfig;
import tw.com.fuchuen.utils.BasicUtils;
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

public class LineFragment extends FormFragment {
	
	public static Fragment newInstance() {
		LineFragment fragment = new LineFragment();
		return fragment;
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = inflater.getContext();
		mMainView = inflater.inflate(R.layout.fragment_line_layout, null, false);
		return mMainView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setupUpTitle();
		setupLineButton();
	}
	
	@Override
	protected void setupSendButton() {
		((Button)mMainView.findViewById(R.id.send)).setText(R.string.line_button_title);
		((Button)mMainView.findViewById(R.id.send)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!BasicUtils.appInstalled(mContext, mContext.getString(R.string.line_package_name))) {
					BasicUtils.showLongToastMsg(mContext, mContext.getString(R.string.line_not_install));
					return;
				}
				final int[] dateArray = BasicUtils.getDateArray(mContext);
				final int[] timeArray = BasicUtils.getTimeArray(mContext);
				String passengerName = BasicUtils.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_PASSENGER_NAME, "");
				String passengerCount = (BasicUtils.getSharedPreferences(mContext).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_PASSENGER_COUNT_POSITION, 0)+1)+mContext.getString(R.string.people);
				String date = dateArray[0]+"/"+(dateArray[1]+1)+"/"+dateArray[2];
				String time = timeArray[0]+":"+timeArray[1];
				String start = BasicUtils.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_START_PLACE, "");
				String destination = BasicUtils.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_DESTINATION, "");
				
				if(passengerName.equals("") || start.equals("") || destination.equals("")) {
					BasicUtils.showLongToastMsg(mContext.getApplicationContext(), mContext.getString(R.string.leak_info));
					return;
				}
				
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("line://msg/text/"+
						BasicUtils.getMessageString(mContext,
								passengerName,
								passengerCount,
								date,
								time,
								start,
								destination)));
				startActivity(intent);
			}
		});
	}
	
	private void setupLineButton() {
		Button addLineBtn = (Button)mMainView.findViewById(R.id.line_add_friend_button);
		addLineBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!BasicUtils.appInstalled(mContext, mContext.getString(R.string.line_package_name))) {
					BasicUtils.showLongToastMsg(mContext, mContext.getString(R.string.line_not_install));
					return;
				}
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://line.naver.jp/ti/p/"+mContext.getString(R.string.driver_line_id)));
				startActivity(intent);
			}
		});
	}
	
	private void setupUpTitle() {
		mUpTitleTextView = (TextView)mMainView.findViewById(R.id.line_hint_title);
		String orignalStr = mContext.getString(R.string.line_hint_title);
		Spannable span = new SpannableString(orignalStr);
		span.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.large_word_size)),
				7, orignalStr.length()-5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mUpTitleTextView.setText(span);
	}
	
}
