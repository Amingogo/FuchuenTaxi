package tw.com.fuchuen.taxi.fragment;

import tw.com.fuchuen.taxi.R;
import android.content.Context;
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
import android.widget.TextView;

public class PhoneFragment extends Fragment {
	
	private Context mContext;
	
	private View mMainView;
	
	private TextView mUpTitleTextView;
	
	public static Fragment newInstance() {
		PhoneFragment fragment = new PhoneFragment();
		return fragment;
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = inflater.getContext();
		mMainView = inflater.inflate(R.layout.fragment_phone_layout, null, false);
		
		mMainView.findViewById(R.id.send).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callOut();
			}
		});
		setupUpTitle();
		
		return mMainView;
	}
	
	private void callOut() {
		Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+getString(R.string.driver_phone)));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	private void setupUpTitle() {
		mUpTitleTextView = (TextView)mMainView.findViewById(R.id.phone_hint_title);
		String orignalStr = mContext.getString(R.string.phone_hint_title);
		Spannable span = new SpannableString(orignalStr);
		span.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.large_word_size)),
				7, orignalStr.length()-5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mUpTitleTextView.setText(span);
	}
	
}
