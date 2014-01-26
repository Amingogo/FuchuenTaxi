package tw.com.fuchuen.taxi;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class FuchuenTaxiApplication extends Application {

	private static FuchuenTaxiApplication sInstance;
	
	private static int sWidth;
	private static int sHeight;
	

	@Override
	public void onCreate() {
		super.onCreate();
		
		sInstance = this;
		
		DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        sWidth = dm.widthPixels;
        sHeight = dm.heightPixels;
		
		initImageLoader(sInstance);

	}

	/**
	 * @return the main context of the Application
	 */
	public static Context getAppContext() {
		return sInstance;
	}

	/**
	 * @return the main resources from the Application
	 */
	public static Resources getAppResources() {
		return sInstance.getResources();
	}
	
	public static void initImageLoader(Context context) {
		int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 8);

		MemoryCacheAware<String, Bitmap> memoryCache;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			memoryCache = new LruMemoryCache(memoryCacheSize);
		} else {
			memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
		}

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//				.showStubImage(R.drawable.ic_launcher)
//				.showImageForEmptyUri(R.drawable.ic_launcher)
//				.showImageOnFail(android.R.drawable.ic_delete)
				.cacheInMemory()
				.cacheOnDisc()
				.resetViewBeforeLoading()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY)
				.displayer(new FadeInBitmapDisplayer(250))
				.build();

		// This configuration tuning is custom. You can tune every option, you may tune some of them, 
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(memoryCache)
				.denyCacheImageMultipleSizesInMemory()
				.defaultDisplayImageOptions(defaultOptions)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.FIFO)
//				.enableLogging() // Not necessary in common
				.build();

//		ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(context);

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	
	public static int getsWidth() {
		return sWidth;
	}
	public static void setsWidth(int sWidth) {
		FuchuenTaxiApplication.sWidth = sWidth;
	}

	public static int getsHeight() {
		return sHeight;
	}
	public static void setsHeight(int sHeight) {
		FuchuenTaxiApplication.sHeight = sHeight;
	}

}
