package tw.com.fuchuen.taxi;

import tw.com.fuchuen.taxi.config.TaxiLocalConfig;
import tw.com.fuchuen.utils.UtilsLog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class OpeningActivity extends Activity{

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_opening_layout);

        goToMainMenu();
        
	}
	
	private void goToMainMenu() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(getApplicationContext(),MainActivity.class));
				finish();
				UtilsLog.logDebug(OpeningActivity.class, "Welcome to FuchuenTaxi");
			}
		}, TaxiLocalConfig.OPENING_ACTIVITY_GO_TO_MENU_DELAY);
	}
	
}
