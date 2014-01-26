package tw.com.fuchuen.taxi.fragment.member;

import tw.com.fuchuen.taxi.R;
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

import com.parse.ParseUser;

public class MemberPageFragment extends Fragment {
	
	private Context mContext;
	
	private View mMainView;

	private Button mLogoutButton;

	
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
		setupLogoutLayout();
	}
	
	@Override
	public void onPause() {
		super.onPause();
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
	
}
