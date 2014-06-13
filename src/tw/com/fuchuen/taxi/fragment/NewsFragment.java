package tw.com.fuchuen.taxi.fragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import tw.com.amingo.library.customview.BannerAdapter;
import tw.com.amingo.library.customview.BannerViewPager;
import tw.com.fuchuen.taxi.MainActivity;
import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.taxi.config.TaxiLocalConfig;
import tw.com.fuchuen.taxi.model.BannerImage;
import tw.com.fuchuen.taxi.model.BannerImageList;
import tw.com.fuchuen.taxi.model.NewsItem;
import tw.com.fuchuen.taxi.model.NewsItemList;
import tw.com.fuchuen.utils.UtilsCommon;
import tw.com.fuchuen.utils.UtilsLog;
import tw.com.fuchuen.utils.UtilsStorage;
import tw.com.fuchuen.utils.UtilsToast;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class NewsFragment extends Fragment {
	
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	
	private View mMainView;
	
	private MainActivity mMainActivity;
	
	private BannerViewPager mBannerViewPager;
	private BannerAdapter mBannerAdapter;
	
	private LinearLayout mNewsLinearLayout;
	
	
	public static Fragment newInstance() {
		NewsFragment fragment = new NewsFragment();
		return fragment;
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mLayoutInflater = inflater;
		mContext = inflater.getContext();
		mMainActivity = (MainActivity) getActivity();
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
		if(UtilsCommon.haveNetworkConnection(mContext)) {
			if(mMainActivity.getBannerImageList().dataList.size() == 0) {
				ParseQuery<ParseObject> query = ParseQuery.getQuery("GalleryImages");
				query.findInBackground(new FindCallback<ParseObject>() {
					@Override
					public void done(List<ParseObject> parseObjectList, ParseException parseException) {
						if(parseException == null) {
							for(int i = 0;i < parseObjectList.size();i++) {
								UtilsLog.logDebug(NewsFragment.class, "URL:"+parseObjectList.get(i).getString("imageLink"));
								BannerImage bannerImage = new BannerImage();
								bannerImage.BannerURL = parseObjectList.get(i).getString("imageLink");
								mMainActivity.getBannerImageList().dataList.add(bannerImage);
							}
							UtilsStorage.putSharedPreferencesValue(mContext, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_CACHE_IMAGES, new Gson().toJson(mMainActivity.getBannerImageList()));
							setupBanner();
						} else {
							String imageListString = UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_CACHE_IMAGES, null);
							if(imageListString == null || imageListString.equals("")) UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.fetch_data_fail), true);
							BannerImageList bannerImageList = new Gson().fromJson(imageListString, BannerImageList.class);
							mMainActivity.setBannerImageList(bannerImageList.dataList);
							setupBanner();
						}
					}
				});
				return;
			}
			setupBanner();
		} else {
			String imageListString = UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_CACHE_IMAGES, null);
			if(imageListString == null || imageListString.equals("")) UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.fetch_data_no_network), true);
			BannerImageList bannerImageList = new Gson().fromJson(imageListString, BannerImageList.class);
			mMainActivity.setBannerImageList(bannerImageList.dataList);
			setupBanner();
		}
	}
	
	private void setupBanner() {
		mBannerAdapter = new BannerAdapter(mContext, mMainActivity.getBannerImageList().dataList);
		mBannerViewPager = (BannerViewPager)mMainView.findViewById(R.id.banner_viewpager_layout);
		mBannerViewPager.setFragmentPagerAdapter(mBannerAdapter);
		mBannerViewPager.startAutoChangeImage(5000);
	}
	
	private void getNewsData() {
		mNewsLinearLayout = (LinearLayout)mMainView.findViewById(R.id.news_layout);
		if(UtilsCommon.haveNetworkConnection(mContext)) {
			if(mMainActivity.getNewsList().dataList.size() == 0) {
				ParseQuery<ParseObject> query = null;
				Locale currentLocale = getResources().getConfiguration().locale;
				if(currentLocale.equals(Locale.CHINESE) || currentLocale.equals(Locale.TAIWAN) || currentLocale.equals(Locale.TRADITIONAL_CHINESE)) {
					query = ParseQuery.getQuery("News");
				} else if(currentLocale.equals(Locale.CHINA) || currentLocale.equals(Locale.SIMPLIFIED_CHINESE)) {
					query = ParseQuery.getQuery("NewsCn");
				} else {
					query = ParseQuery.getQuery("NewsEn");
				}
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
								mMainActivity.getNewsList().dataList.add(newsItem);
							}
							UtilsStorage.putSharedPreferencesValue(mContext, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_CACHE_NEWS, new Gson().toJson(mMainActivity.getNewsList()));
							setupNewsItem();
						} else {
							String newsListString = UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_CACHE_NEWS, null);
							if(newsListString == null || newsListString.equals("")) UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.fetch_data_no_network), true);
							NewsItemList newsItemList = new Gson().fromJson(newsListString, NewsItemList.class);
							mMainActivity.setNewsList(newsItemList.dataList);
							setupNewsItem();
						}
					}
				});
				return;
			}
			setupNewsItem();
		} else {
			String newsListString = UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_CACHE_NEWS, null);
			if(newsListString == null || newsListString.equals("")) UtilsToast.showLongToastMsg(mContext, mContext.getString(R.string.fetch_data_fail), true);
			NewsItemList newsItemList = new Gson().fromJson(newsListString, NewsItemList.class);
			mMainActivity.setNewsList(newsItemList.dataList);
			setupNewsItem();
		}
	}
	
	private void setupNewsItem() {
		Collections.sort(mMainActivity.getNewsList().dataList, new NewsItemComparator());
		for(NewsItem newsItem : mMainActivity.getNewsList().dataList) {
			RelativeLayout newsItemLayout = (RelativeLayout)mLayoutInflater.inflate(R.layout.news_item_layout, null);
//			TextView updateDateTextView = (TextView)newsItemLayout.findViewById(R.id.news_item_update_date);
			TextView indexTextView = (TextView)newsItemLayout.findViewById(R.id.news_item_index);
			TextView contentTextView = (TextView)newsItemLayout.findViewById(R.id.news_item_content);
			
//			updateDateTextView.setText(newsItem.updateDate);
			indexTextView.setText(newsItem.index+".");
			contentTextView.setText(newsItem.content);

			LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(5, 7, 5, 7);
			mNewsLinearLayout.addView(newsItemLayout,layoutParams);
		}
	}
	
	public class NewsItemComparator implements Comparator<NewsItem> {
	    @Override
	    public int compare(NewsItem o1, NewsItem o2) {
	        return o1.index.compareTo(o2.index);
	    }
	}
	
}
