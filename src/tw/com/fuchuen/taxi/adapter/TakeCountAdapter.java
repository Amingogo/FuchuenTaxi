package tw.com.fuchuen.taxi.adapter;

import tw.com.fuchuen.taxi.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class TakeCountAdapter extends BaseAdapter {

	private Context mContext;
	private int mCount = 0;
	
	public TakeCountAdapter(Context context, int count) {
		mContext = context;
		mCount = count;
	}
	
	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        if(position < mCount) imageView.setImageResource(R.drawable.icon);
        else imageView.setImageResource(0);
        return imageView;
	}

}
