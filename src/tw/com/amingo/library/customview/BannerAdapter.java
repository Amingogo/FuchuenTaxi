package tw.com.amingo.library.customview;

import java.util.List;

import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.taxi.model.BannerImage;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.IconPagerAdapter;

public class BannerAdapter extends PagerAdapter implements IconPagerAdapter {

	private LayoutInflater mLayoutInflater;
	private List<BannerImage> mData;
	
	
	public BannerAdapter(Context context) {
		mLayoutInflater = LayoutInflater.from(context);
	}
	
	public BannerAdapter(Context context, List<BannerImage> data) {
		mLayoutInflater = LayoutInflater.from(context);
		mData = data;
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((LinearLayout) object);
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		LinearLayout imageLayout = (LinearLayout)mLayoutInflater.inflate(R.layout.fragment_banner_image, null);
		ImageLoader.getInstance().displayImage(mData.get(position).BannerURL, (ImageView)imageLayout.findViewById(R.id.banner_image));
		imageLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		((ViewPager) container).addView(imageLayout,0);
		return imageLayout;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((LinearLayout) object);
	}

	@Override
	public int getIconResId(int index) {
		return R.drawable.btn_banner_dot;
	}

}
