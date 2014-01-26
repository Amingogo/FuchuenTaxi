package tw.com.fuchuen.taxi.fragment;

import java.util.ArrayList;
import java.util.List;

import tw.com.amingo.library.customview.BannerAdapter;
import tw.com.amingo.library.customview.BannerViewPager;
import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.taxi.model.BannerImage;
import tw.com.fuchuen.taxi.model.NewsItem;
import tw.com.fuchuen.utils.BasicUtils;
import tw.com.fuchuen.utils.UtilsLog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class NewsFragment extends Fragment {
	
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	
	private View mMainView;
	
	private BannerViewPager mBannerViewPager;
	private BannerAdapter mBannerAdapter;
	private List<BannerImage> mBannerImageList;
	
	private LinearLayout mNewsLinearLayout;
	private List<NewsItem> mNewsList;
	
	
	public static Fragment newInstance() {
		NewsFragment fragment = new NewsFragment();
		return fragment;
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBannerImageList = new ArrayList<BannerImage>();
		mNewsList = new ArrayList<NewsItem>();
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mLayoutInflater = inflater;
		mContext = inflater.getContext();
		mMainView = inflater.inflate(R.layout.fragment_news_layout, null, false);
		getImageData();
		getNewsData();
		return mMainView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(mBannerViewPager != null)
			mBannerViewPager.stopAutoChangeImage();
	}
	
	private void getImageData() {
		if(mBannerImageList.size() == 0) {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("GalleryImages");
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> parseObjectList, ParseException parseException) {
					if(parseException == null) {
						for(int i = 0;i < parseObjectList.size();i++) {
							UtilsLog.logDebug(NewsFragment.class, "URL:"+parseObjectList.get(i).getString("imageLink"));
							BannerImage bannerImage = new BannerImage();
							bannerImage.BannerURL = parseObjectList.get(i).getString("imageLink");
							mBannerImageList.add(bannerImage);
						}
						setupBanner();
					} else {
						BasicUtils.showLongToastMsg(mContext, mContext.getString(R.string.fetch_data_fail));
					}
				}
			});
			return;
		}
		setupBanner();
	}
	
	private void setupBanner() {
		mBannerAdapter = new BannerAdapter(mContext, mBannerImageList);
		mBannerViewPager = (BannerViewPager)mMainView.findViewById(R.id.banner_viewpager_layout);
		mBannerViewPager.setFragmentPagerAdapter(mBannerAdapter);
		mBannerViewPager.startAutoChangeImage(5000);
	}
	
	private void getNewsData() {
		mNewsLinearLayout = (LinearLayout)mMainView.findViewById(R.id.news_layout);
		if(mNewsList.size() == 0) {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("News");
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> parseObjectList, ParseException parseException) {
					if(parseException == null) {
						for(int i = 0;i < parseObjectList.size();i++) {
							UtilsLog.logDebug(NewsFragment.class, "index:"+parseObjectList.get(i).getString("index")+" updateDate:"+parseObjectList.get(i).getString("updateDate")+" content:"+parseObjectList.get(i).getString("content"));
							NewsItem newsItem = new NewsItem();
							newsItem.updateDate = parseObjectList.get(i).getString("updateDate");
							newsItem.index = parseObjectList.get(i).getString("index");
							newsItem.content = parseObjectList.get(i).getString("content");
							mNewsList.add(newsItem);
						}
						setupNewsItem();
					} else {
						BasicUtils.showLongToastMsg(mContext, mContext.getString(R.string.fetch_data_fail));
					}
				}
			});
			return;
		}
		setupNewsItem();
	}
	
	private void setupNewsItem() {
		for(NewsItem newsItem : mNewsList) {
			RelativeLayout newsItemLayout = (RelativeLayout)mLayoutInflater.inflate(R.layout.news_item_layout, null);
//			TextView updateDateTextView = (TextView)newsItemLayout.findViewById(R.id.news_item_update_date);
			TextView indexTextView = (TextView)newsItemLayout.findViewById(R.id.news_item_index);
			TextView contentTextView = (TextView)newsItemLayout.findViewById(R.id.news_item_content);
			
//			updateDateTextView.setText(newsItem.updateDate);
			indexTextView.setText(newsItem.index+".");
			contentTextView.setText(newsItem.content);

			mNewsLinearLayout.addView(newsItemLayout);
		}
	}
	
}
