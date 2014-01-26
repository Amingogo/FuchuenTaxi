package tw.com.fuchuen.taxi.fragment.member;

import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.utils.BasicUtils;
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
	private Button mRegisterButton;
	
	private View mProgressLayout;

	
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
		mMainView = inflater.inflate(R.layout.fragment_member_layout, null, false);
		mProgressLayout = mMainView.findViewById(R.id.progress_bar_layout);
		mProgressLayout.setVisibility(View.GONE);
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
		mMainView.findViewById(R.id.register).setVisibility(View.GONE);
		mUserNameEditText = (EditText)mMainView.findViewById(R.id.user_name);
		mUserPasswordEditText = (EditText)mMainView.findViewById(R.id.user_password);
		mRegisterButton = (Button)mMainView.findViewById(R.id.send);
		mRegisterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String userName = mUserNameEditText.getText().toString();
				String userPassword = mUserPasswordEditText.getText().toString();
				if(userName.equals("") || userPassword.equals("")) {
					BasicUtils.showLongToastMsg(mContext, mContext.getString(R.string.member_user_register_leak_info));
					return;
				}
				ParseUser user = new ParseUser();
				user.setUsername(mUserNameEditText.getText().toString());
				user.setPassword(mUserPasswordEditText.getText().toString());
				user.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {
					    if (e == null) {
					    	BasicUtils.showLongToastMsg(mContext, mContext.getString(R.string.member_user_register_success));
					    	BasicUtils.popFragment(mContext);
					    } else {
					    	//已被註冊
					    	if(e.getCode() == 202) {
					    		BasicUtils.showLongToastMsg(mContext, mContext.getString(R.string.member_user_register_user_name_taken));
					    	} else {
					    		BasicUtils.showLongToastMsg(mContext, mContext.getString(R.string.member_user_register_fail));
					    	}
					    }
					}
				});
			}
		});
	}
	
}
