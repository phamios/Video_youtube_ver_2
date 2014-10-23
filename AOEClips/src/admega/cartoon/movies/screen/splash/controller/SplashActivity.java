package admega.cartoon.movies.screen.splash.controller;

import admega.cartoon.movies.R;

import admega.cartoon.movies.common.utils.NetworkHelper;
import admega.cartoon.movies.common.view.AlertDialogManager;
import admega.cartoon.movies.screen.category.controller.MainActivity;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author ducna6417
 */
public class SplashActivity extends Activity {
	private int TIME_SPLASH = 3000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if (NetworkHelper.hasNetworkConnection(SplashActivity.this)) {
					Intent intent = new Intent(SplashActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}else {
					AlertDialogManager.showAlert(SplashActivity.this, getString(R.string.no_network), new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
				}
				
			}
		}, TIME_SPLASH);
	}
}
