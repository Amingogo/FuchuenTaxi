package tw.com.fuchuen.taxi.fragment.member;

import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.taxi.config.TaxiApiConfig;
import tw.com.fuchuen.utils.UtilsCommon;
import tw.com.fuchuen.utils.UtilsDialog;
import tw.com.fuchuen.utils.UtilsFragment;
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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MemberRegisterFragment extends Fragment {
	
	private Context mContext;
	
	private View mMainView;
	
	private EditText mUserNameEditText;
	private EditText mUserPasswordEditText;
	private EditText mUserPasswordConfirmEditText;
	private EditText mUserAccountEditText;
	private Button mRegisterButton;
	
	private ProgressDialog mProgressDialog;

	
	public static Fragment newInstance() {
		MemberRegisterFragment fragment = new MemberRegisterFragment();
		return fragment;
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = inflater.getContext();
		mMainView = inflater.inflate(R.layout.fragment_member_register_layout, null, false);
		return mMainView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setupRegisterLayout();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	private void setupRegisterLayout() {
		mUserNameEditText = (EditText)mMainView.findViewById(R.id.user_name);
		mUserPasswordEditText = (EditText)mMainView.findViewById(R.id.user_password);
		mUserPasswordConfirmEditText = (EditText)mMainView.findViewById(R.id.user_password_confirm);
		mUserAccountEditText = (EditText)mMainView.findViewById(R.id.user_account);
		mRegisterButton = (Button)mMainView.findViewById(R.id.send);
		mRegisterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!UtilsCommon.haveNetworkConnection(mContext)) {
					UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.register_no_network), true);
					return;
				}
				String userName = mUserNameEditText.getText().toString();
				String userPassword = mUserPasswordEditText.getText().toString();
				String userConfirmPassword = mUserPasswordConfirmEditText.getText().toString();
				String userAccount = mUserAccountEditText.getText().toString();
				if(userName.equals("") || userPassword.equals("") || userConfirmPassword.equals("") || userAccount.equals("")) {
					UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.member_user_register_leak_info), true);
					return;
				}
				if(!userPassword.equals(userConfirmPassword)) {
					UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.member_user_register_password_incorrect), true);
					return;
				}
				ParseUser user = new ParseUser();
				user.setUsername(mUserNameEditText.getText().toString());
				user.setPassword(mUserPasswordEditText.getText().toString());
				user.put(TaxiApiConfig.USER_ACCOUNT, userAccount);
				user.put(TaxiApiConfig.USER_TAKE_COUNT_CURRENT, "0");
				mProgressDialog = UtilsDialog.getProgressDialog(mContext, getString(R.string.member_user_registering));
				mProgressDialog.show();
				user.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {
						UtilsDialog.dismissProgressDialog(mProgressDialog);
					    if (e == null) {
					    	UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.member_user_register_success), true);
					    	UtilsFragment.popFragment(mContext);
					    } else {
					    	//已被註冊
					    	if(e.getCode() == 202) {
					    		UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.member_user_register_user_name_taken), true);
					    	} else {
					    		UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.member_user_register_fail), true);
					    	}
					    }
					}
				});
			}
		});
	}
	
}
