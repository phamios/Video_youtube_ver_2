package admega.cartoon.movies.screen.category.controller;

import java.util.ArrayList;
import java.util.Calendar;

import admega.cartoon.movies.common.utils.Constants;
import admega.cartoon.movies.common.utils.GaTracking;
import admega.cartoon.movies.common.utils.Logger;
import admega.cartoon.movies.common.utils.NetworkHelper;
import admega.cartoon.movies.common.view.AlertDialogManager;
import admega.cartoon.movies.screen.category.controller.adapter.CategoryArrayAdapter;
import admega.cartoon.movies.screen.category.controller.adapter.CategoryFragmentPagerAdapter;
import admega.cartoon.movies.screen.category.controller.loader.DataCategoriesLoader;
import admega.cartoon.movies.screen.setting.controller.SettingActivity;
import admega.cartoon.movies.screen.splash.controller.SplashActivity;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.app.Activity;
import android.net.Uri;
import android.os.SystemClock;
import vn.admega.notification.AlarmReceiver;
import vn.admega.notification.NotificationPublisher;

import admega.cartoon.movies.R;

import vn.nad.aoeclips.common.model.entity.Category;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements Constants,
		LoaderCallbacks<ArrayList<Category>>, OnItemClickListener,
		OnPageChangeListener {
	private String TAG = MainActivity.class.getSimpleName();

	private DrawerLayout mDrawerLayout;
	private ListView mListCategory;
	private ActionBarDrawerToggle mDrawerToggle;
	private ArrayList<Category> simpleCategories = new ArrayList<Category>();

	private ProgressDialog mProgressDialog;

	// Navigation drawer title
	private CharSequence mTitleCategory;
	private CharSequence mTitle;

	// adapter Category
	private CategoryArrayAdapter mAdapterCategory;

	private ViewPager mViewPager;

	private CategoryFragmentPagerAdapter mAdapter;

	private InterstitialAd interstitial;

	final String appPackageName = "";
	private Context context;
	private PendingIntent mAlarmSender;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTitle = mTitleCategory = getTitle();

		if (NetworkHelper.hasNetworkConnection(MainActivity.this)) {
			initData();
			startInterstitial();
			scheduleNotification(
					getNotification("News Clips has been updated, Click to watch it now !"),
					1000000);
			mAlarmSender = PendingIntent.getBroadcast(this, 0, new Intent(this,
					AlarmReceiver.class), 0);

			// Locate the Banner Ad in activity_main.xml
			AdView adView = (AdView) this.findViewById(R.id.adView);

			// Request for Ads
			AdRequest adRequest = new AdRequest.Builder().build();
			// Load ads into Banner Ads
			adView.loadAd(adRequest);

			GaTracking.track_view(this, "Home", "Ads popup");

		} else {
			AlertDialogManager.showAlert(MainActivity.this,
					getString(R.string.no_network), new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
		}
	}

	public void startAlarm() {
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
				new Intent(this, MainActivity.class), 0);

		Calendar calendar = Calendar.getInstance();

		// TODO: This is the real code
		calendar.set(Calendar.HOUR_OF_DAY, 11);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
	}

	private void scheduleNotification(Notification notification, int delay) {

		Intent notificationIntent = new Intent(this, MainActivity.class);
		notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
		notificationIntent.putExtra(NotificationPublisher.NOTIFICATION,
				notification);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		long futureInMillis = SystemClock.elapsedRealtime() + delay;
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis,
				pendingIntent);
	}

	private Notification getNotification(String content) {
		Notification.Builder builder = new Notification.Builder(this);
		builder.setContentTitle("New Movies - HOT");
		builder.setContentText(content);
		builder.setSmallIcon(R.drawable.ic_launcher);
		return builder.build();
	}

	public void startInterstitial() {
		interstitial = new InterstitialAd(MainActivity.this);
		interstitial.setAdUnitId("ca-app-pub-1214276490829950/6387014627");

		// AdView adView = (AdView) this.findViewById(R.id.adView);

		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("")
				.build();

		// adView.loadAd(adRequest);
		interstitial.loadAd(adRequest);
		interstitial.setAdListener(new AdListener() {
			public void onAdLoaded() {
				// Call displayInterstitial() function
				displayInterstitial();
			}
		});
	}

	public void displayInterstitial() {
		// If Ads are loaded, show Interstitial else show nothing.
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}

	// findViewById
	public void findViewById() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		mListCategory = (ListView) findViewById(R.id.list_slidermenu);
		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		mAdapterCategory = new CategoryArrayAdapter(MainActivity.this,
				simpleCategories);
		mListCategory.setAdapter(mAdapterCategory);
	}

	private void initView() {
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(
				CategoryFragmentPagerAdapter.ARGUMENT_CATEGORIES,
				simpleCategories);
		mAdapter = new CategoryFragmentPagerAdapter(
				getSupportFragmentManager(), bundle);

		mViewPager.setAdapter(mAdapter);
		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setOnPageChangeListener(this);
	}

	// listener
	public void listener() {
		mListCategory.setOnItemClickListener(this);
	}

	public void initData() {
		initmProgressDialog();
		if (simpleCategories.size() == 0) {
			LoaderManager lm = getSupportLoaderManager();
			lm.destroyLoader(LOADER_ID_CATEGORIES);
			lm.initLoader(LOADER_ID_CATEGORIES, null, this);
		} else {
			findViewById();
			initView();
			listener();
			setActionBar();

		}
	}

	public void setActionBar() {
		// Enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));

		mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
				mDrawerLayout, R.drawable.ic_drawer, R.string.select_category,
				R.string.select_category) {
			public void onDrawerClosed(android.view.View drawerView) {
				getActionBar().setTitle(mTitle);
				startInterstitial(); 
			};

			public void onDrawerOpened(android.view.View drawerView) {
				getActionBar().setTitle(mTitleCategory);
				invalidateOptionsMenu();
				startInterstitial();
			};
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	private void displayView(int position) {
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	private void initmProgressDialog() {
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle(R.string.app_name);
		mProgressDialog.setMessage("Please wait...");
		mProgressDialog.setCancelable(false);
		mProgressDialog.setIndeterminate(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * On menu item selected
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			// Intent intent = new Intent(MainActivity.this,
			// SettingActivity.class);
			// startActivity(intent);
			startInterstitial();
			try {
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("http://play.google.com/store/apps/developer?id=RadioVL")));
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("market://details?id=com.sonpx.vnclip")));
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		// boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLayout);
		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * loader
	 */
	@Override
	public Loader<ArrayList<Category>> onCreateLoader(int arg0, Bundle bundle) {
		mProgressDialog.show();
		return new DataCategoriesLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Category>> loader,
			ArrayList<Category> data) {
		mProgressDialog.dismiss();
		simpleCategories.clear();
		simpleCategories.addAll(data);
		Logger.out(TAG, "simpleCategories ; " + simpleCategories.size());
		// mAdapterCategory = new CategoryArrayAdapter(MainActivity.this,
		// simpleCategories);

		findViewById();
		initView();
		listener();
		setActionBar();

	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Category>> arg0) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mViewPager.setCurrentItem(position);
		mDrawerLayout.closeDrawers();
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		// mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		// mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		setTitle(simpleCategories.get(position).getCateName());
	}

	private long backPressedTime = 0;

	@Override
	public void onBackPressed() { // to prevent irritating accidental logouts
		long t = System.currentTimeMillis();
		if (t - backPressedTime > 2000) { // 2 secs
			backPressedTime = t;
			Toast.makeText(this, "Press back again to logout",
					Toast.LENGTH_SHORT).show();
		} else { // this guy is serious
			// clean up
			startInterstitial();
			super.onBackPressed(); // bye
		}
	}
}
