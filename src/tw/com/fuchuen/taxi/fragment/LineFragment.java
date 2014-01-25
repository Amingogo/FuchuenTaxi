package tw.com.fuchuen.taxi.fragment;

import tw.com.fuchuen.taxi.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class LineFragment extends Fragment {
	
	private View mMainView;
	
	public static Fragment newInstance() {
		LineFragment fragment = new LineFragment();
		return fragment;
	}
	
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mMainView = inflater.inflate(R.layout.fragment_line_layout, null, false);
		setupLineButton();
		return mMainView;
	}
	
	private void setupLineButton() {
		Button addLineBtn = (Button)mMainView.findViewById(R.id.addLineBtn);
		addLineBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://line.naver.jp/ti/p/J1wRJ7uDhZ"));
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("line://msg/text/test"));
				startActivity(intent);
			}
		});
	}
	
}
