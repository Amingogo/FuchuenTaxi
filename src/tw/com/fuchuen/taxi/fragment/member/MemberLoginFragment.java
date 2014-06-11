package tw.com.fuchuen.taxi.fragment.member;

import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.taxi.config.TaxiLocalConfig;
import tw.com.fuchuen.utils.UtilsCommon;
import tw.com.fuchuen.utils.UtilsDialog;
import tw.com.fuchuen.utils.UtilsFragment;
import tw.com.fuchuen.utils.UtilsLog;
import tw.com.fuchuen.utils.UtilsStorage;
import tw.com.fuchuen.utils.UtilsToast;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MemberLoginFragment extends Fragment {
	
	private Context mContext;
	
	private View mMainView;
	
	private EditText mUserNameEditText;
	private EditText mUserPasswordEditText;
	private Button mLoginButton;
	private Button mRegisterButton;
	
	private View mProgressLayout;
	
	private ProgressDialog mProgressDialog;

	
	public static Fragment newInstance() {
		MemberLoginFragment fragment = new MemberLoginFragment();
		return fragment;
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = inflater.getContext();
		mMainView = inflater.inflate(R.layout.fragment_member_login_layout, null, false);
		mProgressLayout = mMainView.findViewById(R.id.progress_bar_layout);
		return mMainView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setupLoginLayout();
		setupRegisterButton();
		autoSignIn();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	private void setupLoginLayout() {
		mUserNameEditText = (EditText)mMainView.findViewById(R.id.user_name);
		mUserPasswordEditText = (EditText)mMainView.findViewById(R.id.user_password);
		mLoginButton = (Button)mMainView.findViewById(R.id.send);
		mLoginButton.setText(R.string.member_user_login);
		mLoginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!UtilsCommon.haveNetworkConnection(mContext)) {
					UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.login_no_network), true);
					return;
				}
				String userName = mUserNameEditText.getText().toString();
				String userPassword = mUserPasswordEditText.getText().toString();
				if(userName.equals("") || userPassword.equals("")) {
					UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.member_user_login_leak_info), true);
					return;
				}
				mProgressDialog = UtilsDialog.getProgressDialog(mContext, getString(R.string.member_user_logining));
				mProgressDialog.show();
				ParseUser.logInInBackground(userName, userPassword, new LogInCallback() {
					@Override
					public void done(ParseUser user, ParseException e) {
						UtilsDialog.dismissProgressDialog(mProgressDialog);
						if(e == null && user != null) {
							//success
							UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.member_user_login_success), true);
							UtilsStorage.putSharedPreferencesValue(mContext, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_SESSION_TOKEN, user.getSessionToken());
							UtilsFragment.switchToFragmentImmediately(mContext, MemberPageFragment.newInstance(), null, R.id.content_frame, false, false);
						} else if(user == null) {
							//usernameOrPasswordIsInvalid
							UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.member_user_login_username_or_password_error), true);
						} else {
							UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.member_user_register_fail), true);
						}
					}
				});
			}
		});
	}
	
	private void setupRegisterButton() {
		mRegisterButton = (Button)mMainView.findViewById(R.id.register);
		mRegisterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UtilsFragment.switchToFragmentImmediately(mContext, MemberRegisterFragment.newInstance(), null, R.id.content_frame, true, true);
			}
		});
	}
	
	private void autoSignIn() {
		String sessionToken = UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_SESSION_TOKEN, "");
		UtilsLog.logDebug(MemberLoginFragment.class, "sessionToken: "+sessionToken);
		if(sessionToken.equals("")) {
			mProgressLayout.setVisibility(View.GONE);
		} else {
			if(!UtilsCommon.haveNetworkConnection(mContext)) {
				UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.login_no_network), true);
				mProgressLayout.setVisibility(View.GONE);
				return;
			}
			ParseUser.becomeInBackground(sessionToken, new LogInCallback() {
				@Override
				public void done(ParseUser user, ParseException e) {
					if(e == null && user != null) {
						//success
						UtilsStorage.putSharedPreferencesValue(mContext, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_SESSION_TOKEN, user.getSessionToken());
						UtilsFragment.switchToFragmentImmediately(mContext, MemberPageFragment.newInstance(), null, R.id.content_frame, false, false);
					} else if(user == null) {
						//usernameOrPasswordIsInvalid
						UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.member_user_login_username_or_password_error), true);
						mProgressLayout.setVisibility(View.GONE);
					} else {
						UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.member_user_register_fail), true);
						mProgressLayout.setVisibility(View.GONE);
					}
				}
			});
		}
	}
	
}
