package tw.com.fuchuen.utils;

import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.taxi.config.TaxiLocalConfig;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class UtilsFragment {

	public static FragmentTransaction switchToFragment(Context mContext, Fragment fragment, Bundle args, int fromResource, boolean isAddStack, boolean needAnimation, int[] animationResources) {
		FragmentManager fragmentManager=((FragmentActivity) mContext).getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
	
		if (needAnimation) {
			ft.setCustomAnimations(animationResources[0], animationResources[1], animationResources[2], animationResources[3]);
		}
	
		if (isAddStack) {
			ft.addToBackStack(TaxiLocalConfig.FRAGMENT_STACK_NAME);
		}
	
		ft.replace(fromResource, fragment, TaxiLocalConfig.FRAGMENT_STACK_NAME_MAIN);
		
		return ft;
	}

	public static FragmentTransaction switchToFragment(Context mContext, Fragment fragment, Bundle args, int fromResource, boolean isAddStack, boolean needAnimation) {
		int[] defaultAnimArr = new int[] { R.anim.enter_right, R.anim.leave_left, R.anim.enter_left, R.anim.leave_right };
		return switchToFragment(mContext, fragment, args, fromResource, isAddStack, needAnimation,defaultAnimArr);
	}

	public static FragmentTransaction switchToFragmentImmediately(Context mContext, Fragment fragment, Bundle args, int fromResource, boolean isAddStack, boolean needAnimation, int[] animationResources) {
		FragmentManager fragmentManager=((FragmentActivity) mContext).getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
	
		if (needAnimation) {
			ft.setCustomAnimations(animationResources[0], animationResources[1], animationResources[2], animationResources[3]);
		}
	
		if (isAddStack) {
			ft.addToBackStack(TaxiLocalConfig.FRAGMENT_STACK_NAME);
		}
	
		ft.replace(fromResource, fragment, TaxiLocalConfig.FRAGMENT_STACK_NAME_MAIN);
		
		ft.commit();
		fragmentManager.executePendingTransactions();
	
		return ft;
	}

	public static FragmentTransaction switchToFragmentImmediately(Context mContext, Fragment fragment, Bundle args, int fromResource, boolean isAddStack, boolean needAnimation) {
		int[] defaultAnimArr = new int[] { R.anim.enter_right, R.anim.leave_left, R.anim.enter_left, R.anim.leave_right };
		return switchToFragmentImmediately(mContext, fragment, args, fromResource, isAddStack, needAnimation,defaultAnimArr);
	}

	public static void popFragment(Context mContext) {
		FragmentManager fragmentManager=((FragmentActivity) mContext).getSupportFragmentManager();
		fragmentManager.popBackStack();
	}

}
