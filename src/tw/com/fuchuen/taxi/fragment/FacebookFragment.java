package tw.com.fuchuen.taxi.fragment;

import tw.com.fuchuen.taxi.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FacebookFragment extends Fragment {
	
	private View mMainView;
	
	public static Fragment newInstance() {
		FacebookFragment fragment = new FacebookFragment();
		return fragment;
	}
	
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mMainView = inflater.inflate(R.layout.fragment_facebook_layout, null, false);
		return mMainView;
	}
	
}
