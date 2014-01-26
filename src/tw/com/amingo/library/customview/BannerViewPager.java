package tw.com.amingo.library.customview;

import com.viewpagerindicator.IconPageIndicator;

import tw.com.fuchuen.taxi.R;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;


/***
 * 
 * implement banner layout by viewpager
 * @author Amingo
 *
 */
public class BannerViewPager extends RelativeLayout {
	
	private Context mContext;
	
	private ViewPager mInnerViewPager;
	private IconPageIndicator mIconPageIndicator;
	
	private PagerAdapter mPagerAdapter;
	
	private int mCurrentBannerIndex;
	
	/**
	 * auto change image
	 */
	private Handler mHandler = new Handler();
	private Runnable mChangeBannerImageRunnable = new Runnable() {
		@Override
		public void run() {
			int index = mCurrentBannerIndex++;
			if(index > mPagerAdapter.getCount()-1)
				index = 0;
			mInnerViewPager.setCurrentItem(index, true);
			mHandler.postDelayed(mChangeBannerImageRunnable, 1500);
		}
	};
	
	public BannerViewPager(Context context) {
		super(context);
		mContext = context;
		inflate(mContext, R.layout.banner_layout, this);
		initElements();
	}
	
	public BannerViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		inflate(mContext, R.layout.banner_layout, this);
		initElements();
	}
	
	private void initElements() {
		mInnerViewPager = (ViewPager)findViewById(R.id.banner_pager);
		mIconPageIndicator = (IconPageIndicator)findViewById(R.id.banner_pager_indicator);
	}
	
//	@Override
//	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
//		this.setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
//		mWidth = MeasureSpec.getSize(widthMeasureSpec);
//		mHeight = MeasureSpec.getSize(heightMeasureSpec);
//	}
	
	/**
	 * set a adapter which expends from PagerAdapter
	 * @param adapter: a subclass of PagerAdapter
	 */
	public void setFragmentPagerAdapter(PagerAdapter adapter) {
		mPagerAdapter = adapter;
		mInnerViewPager.setAdapter(mPagerAdapter);
		mIconPageIndicator.setViewPager(mInnerViewPager, 0);
		setOnBannerChangeListener(null);
	}
	/**
	 * get PagerAdapter of BannerViewPager
	 * @return PagerAdapter: a subclass of FragmentPagerAdapter
	 */
	public PagerAdapter getPagerAdapter() {
		return mPagerAdapter;
	}
	
	/**
	 * set initial position for BannerViewPager
	 * @param position
	 */
	public void setInitialPosition(int position) {
		mCurrentBannerIndex = position;
		mIconPageIndicator.setCurrentItem(position);
	}
	
	/**
	 * set OnBannerChangeListener for BannerViewPager
	 * @param listener OnBannerChangeListener
	 */
	public void setOnBannerChangeListener(final OnBannerChangeListener listener) {
		mIconPageIndicator.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				mCurrentBannerIndex = position;
				if(listener != null) listener.onPageSelected(position);
			}
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				if(listener != null) listener.onPageScrolled(position,positionOffset,positionOffsetPixels);
			}
			@Override
			public void onPageScrollStateChanged(int state) {
				if(listener != null) listener.onPageScrollStateChanged(state);
			}
		});
	}

	/**
	 * set CirclePageIndicator Visible or Gone
	 * @param enable
	 */
	public void setCirclePageIndicatorEnable(boolean enable) {
		if(enable) mIconPageIndicator.setVisibility(View.VISIBLE);
		else mIconPageIndicator.setVisibility(View.GONE);
	}
	
	/**
	 * start to change image automatically
	 * @param delayMillis
	 */
	public void startAutoChangeImage(long delayMillis) {
		mHandler.postDelayed(mChangeBannerImageRunnable, delayMillis);
	}
	
	/**
	 * stop changing image
	 */
	public void stopAutoChangeImage() {
		mHandler.removeCallbacks(mChangeBannerImageRunnable);
	}
	
	/**
	 * get current index of viewpager
	 * @return index
	 */
	public int getCurrentBannerIndex() {
		return mCurrentBannerIndex;
	}
	
	public ViewPager getViewPager() {
		return mInnerViewPager;
	}
	
	public interface OnBannerChangeListener {
		public void onPageSelected(int position);
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
		public void onPageScrollStateChanged(int state);
	}

}
