package tw.com.fuchuen.taxi;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryImageAdapter extends BaseAdapter
{
	private int[] photoResIds = new int[]
		    { R.drawable.photo1, R.drawable.photot_main, R.drawable.photo2, R.drawable.photo3};
    int mGalleryItemBackground;
    private Context mContext;

    public GalleryImageAdapter(Context context)
    {
        mContext = context;
        TypedArray typedArray = mContext.obtainStyledAttributes(R.styleable.Gallery);
        mGalleryItemBackground = typedArray.getResourceId(
                R.styleable.Gallery_android_galleryItemBackground, 0);                        
    }
    public int getCount()
    {
        return photoResIds.length;
    }
    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(photoResIds[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setLayoutParams(new Gallery.LayoutParams(163, 106));
        imageView.setBackgroundResource(mGalleryItemBackground);
        return imageView;
    }
}