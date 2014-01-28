package tw.com.fuchuen.taxi.fragment.member;

import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.taxi.adapter.TakeCountAdapter;
import tw.com.fuchuen.taxi.config.TaxiApiConfig;
import tw.com.fuchuen.taxi.config.TaxiLocalConfig;
import tw.com.fuchuen.utils.BasicUtils;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.parse.ParseUser;

public class MemberPageFragment extends Fragment {
	
	private Context mContext;
	
	private View mMainView;

	private GridView mTakeCountGridView;
	private TextView mZeroTextView;
	private Button mLogoutButton;
	
	private TakeCountAdapter mTakeCountAdapter;
	
	private ParseUser mParseUser;

	
	public static Fragment newInstance() {
		MemberPageFragment fragment = new MemberPageFragment();
		return fragment;
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = inflater.getContext();
		mMainView = inflater.inflate(R.layout.fragment_member_page_layout, null, false);
		return mMainView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mParseUser = ParseUser.getCurrentUser();
		setupWelcomeTitle();
		setupLogoutLayout();
		setupTakeCounts();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	private void setupWelcomeTitle() {
		if(mParseUser != null) {
			((TextView)mMainView.findViewById(R.id.welcome_title)).setText(String.format(mContext.getString(R.string.member_user_page_welcome), mParseUser.getString(TaxiApiConfig.USER_ACCOUNT)));
		}
	}
	
	private void setupLogoutLayout() {
		mLogoutButton = (Button)mMainView.findViewById(R.id.logout);
		mLogoutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ParseUser.logOut();
				BasicUtils.putSharedPreferencesValue(mContext, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_SESSION_TOKEN, null);
				BasicUtils.switchToFragmentImmediately(mContext, MemberLoginFragment.newInstance(), null, R.id.content_frame, false, false);
			}
		});
	}
	
	private void setupTakeCounts() {
		mTakeCountGridView = (GridView)mMainView.findViewById(R.id.user_take_count_gridview);
		mZeroTextView = (TextView)mMainView.findViewById(R.id.user_take_count_zero_title);
		if(mParseUser != null) {
			if(Integer.parseInt(mParseUser.getString(TaxiApiConfig.USER_TAKE_COUNT)) == 0) {
				mTakeCountGridView.setVisibility(View.GONE);
			} else {
				mTakeCountAdapter = new TakeCountAdapter(mContext, Integer.parseInt(mParseUser.getString(TaxiApiConfig.USER_TAKE_COUNT)));
				mTakeCountGridView.setAdapter(mTakeCountAdapter);
				mZeroTextView.setVisibility(View.GONE);
				mTakeCountGridView.setVisibility(View.VISIBLE);
			}
		}
	}
	
}
