package tw.com.fuchuen.taxi;

import java.util.ArrayList;
import java.util.List;

import tw.com.fuchuen.taxi.fragment.EmailFragment;
import tw.com.fuchuen.taxi.fragment.LineFragment;
import tw.com.fuchuen.taxi.fragment.NewsFragment;
import tw.com.fuchuen.taxi.fragment.PhoneFragment;
import tw.com.fuchuen.taxi.fragment.SMSFragment;
import tw.com.fuchuen.taxi.fragment.WebViewFragment;
import tw.com.fuchuen.taxi.fragment.WebViewWithControlFragment;
import tw.com.fuchuen.taxi.fragment.member.MemberLoginFragment;
import tw.com.fuchuen.taxi.model.BannerImage;
import tw.com.fuchuen.taxi.model.NewsItem;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.parse.Parse;
import com.parse.ParseAnalytics;

public class MainActivity extends SherlockFragmentActivity {
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	
	private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mFragmentTitles;
    
    private List<BannerImage> mBannerImageList;
    private List<NewsItem> mNewsList;
    
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_layout);
		
		setupActiobBar();
		setupDrawer();
		
		mBannerImageList = new ArrayList<BannerImage>();
		mNewsList = new ArrayList<NewsItem>();
		
		initFragment();
		
		Parse.initialize(this, "Sh07WHQA9rLHMk5LNuz5fKB9SmKdvmJZ0g5nZlWE", "SM6FUVvTnsszaVnuKiYU2mr8ClQTfv4qSIOGXHKD");
		ParseAnalytics.trackAppOpened(getIntent());
//		ParseObject parseObject = new ParseObject("GalleryImages");
//		ParseFile parseFile = (ParseFile)parseObject.get("image");
//		final String url = parseFile.getUrl();
//		parseFile.getDataInBackground(new GetDataCallback() {
//			@Override
//			public void done(byte[] arg0, ParseException arg1) {
//				Log.d(MainActivity.class.getSimpleName(), "URL:"+url);
//			}
//		});
		
//		ParseQuery<ParseObject> query = ParseQuery.getQuery("GalleryImages");
//		query.findInBackground(new FindCallback<ParseObject>() {
//			@Override
//			public void done(List<ParseObject> arg0, ParseException arg1) {
//				for(int i = 0;i < arg0.size();i++) {
//					Log.d(MainActivity.class.getSimpleName(), "URL:"+arg0.get(i).getString("imageLink"));
//				}
//			}
//		});
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        if (item.getItemId() == android.R.id.home) {
 
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
 
        return super.onOptionsItemSelected(item);
    }

	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
	@Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
	
	private void setupActiobBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}
	
	private void selectItem(int position) {
        mDrawerList.setItemChecked(position, true);
        setTitle(mFragmentTitles[position]);
        setMainContent(position);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
	
	private void setupDrawer() {
		mTitle = mDrawerTitle = getTitle();
        mFragmentTitles = getResources().getStringArray(R.array.main_menu_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_listitem_layout, mFragmentTitles));
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectItem(position);
			}
		});
        
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
            	getSupportActionBar().setTitle(mTitle);
            }

            public void onDrawerOpened(View drawerView) {
            	getSupportActionBar().setTitle(mDrawerTitle);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
	}
	
	private void initFragment() {
		selectItem(0);
	}
	
	private void setMainContent(int position) {
		switch (position) {
			case 0:
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, NewsFragment.newInstance(), NewsFragment.class.getSimpleName()).commit();
				break;
			case 1:
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, MemberLoginFragment.newInstance(), MemberLoginFragment.class.getSimpleName()).commit();
				break;
			case 2:
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, PhoneFragment.newInstance(), PhoneFragment.class.getSimpleName()).commit();
				break;
			case 3:
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, SMSFragment.newInstance(), SMSFragment.class.getSimpleName()).commit();
				break;
			case 4:
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, EmailFragment.newInstance(), EmailFragment.class.getSimpleName()).commit();
				break;
			case 5:
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, LineFragment.newInstance(), LineFragment.class.getSimpleName()).commit();
				break;
			case 6:
				WebViewWithControlFragment fbWebViewFragment = WebViewWithControlFragment.newInstance(WebViewFragment.URL_FACEBOOK);
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fbWebViewFragment, WebViewFragment.URL_FACEBOOK).commit();
				break;
			case 7:
				WebViewWithControlFragment xuiteWebViewFragment = WebViewWithControlFragment.newInstance(WebViewFragment.URL_XUITE);
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, xuiteWebViewFragment, WebViewFragment.URL_XUITE).commit();
				break;
			default:
				break;
		}
	}

	public List<BannerImage> getBannerImageList() {
		return mBannerImageList;
	}

	public void setBannerImageList(List<BannerImage> mBannerImageList) {
		this.mBannerImageList = mBannerImageList;
	}

	public List<NewsItem> getNewsList() {
		return mNewsList;
	}

	public void setNewsList(List<NewsItem> mNewsList) {
		this.mNewsList = mNewsList;
	}

}
