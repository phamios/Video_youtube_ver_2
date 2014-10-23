package admega.cartoon.movies.screen.detail.controller;

import java.net.MalformedURLException;
import java.util.ArrayList;

import admega.cartoon.movies.R;

import vn.nad.aoeclips.common.model.entity.Article;
import admega.cartoon.movies.common.utils.Constants;
import admega.cartoon.movies.common.utils.GaTracking;
import admega.cartoon.movies.common.utils.Logger;
import admega.cartoon.movies.common.utils.YoutubeUtils;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
 
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView; 

/**
 * @author ducna6417
 */
public class DetailActivity extends YouTubeBaseActivity implements Constants,
		OnInitializedListener {

	public static final String TAG = "DetailActivity";
	private ArrayList<Article> simpleArticles = new ArrayList<Article>();
	private int position;
	private String linkVideo;
	// list id video youtube
	private ArrayList<String> arrayVideoId = new ArrayList<String>();
	private YouTubePlayerView mYouTubePlayerView;

	@Override
	protected void onCreate(Bundle arg0) {
		Logger.d(TAG, "onCreate");
		super.onCreate(arg0);
		setContentView(R.layout.activity_detail);

		findViewById();
		initData();

		// Locate the Banner Ad in activity_main.xml
		AdView adView = (AdView) this.findViewById(R.id.adView);

		// Request for Ads
		AdRequest adRequest = new AdRequest.Builder()

				// Add a test device to show Test Ads
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("")
				.build();

		// Load ads into Banner Ads
		adView.loadAd(adRequest);
		
		

	}

	public void findViewById() {
		mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
	}

	public void initData() {

		Intent intent = getIntent();
		simpleArticles = intent.getParcelableArrayListExtra(ARGUMENT_ARTICLES);
		// linkVideo = intent.getStringExtra(ARGUMENT_ARTICLES);
		position = intent.getIntExtra(ARGUMENT_POSITION, 0);

		// get video id
		for (int i = 0; i < simpleArticles.size(); i++) {
			try {
				Logger.out(TAG, "id : " + simpleArticles.get(i).getLink());
				
 
				GaTracking.track_view(this, "Details", simpleArticles.get(i).getLink());
				
				arrayVideoId.add(YoutubeUtils.extractYoutubeId(simpleArticles
						.get(i).getLink()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		mYouTubePlayerView.initialize(YOUTUBE_API_KEY, this);
	}

	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {

	}

	@Override
	public void onInitializationSuccess(Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		if (!wasRestored)
			// player.loadVideos(arrayVideoId);
			// player.loadPlaylist(arrayVideoId, position, 0);
			player.loadVideos(arrayVideoId, position, 0);

		// try {
		// player.loadVideo(YoutubeUtils.extractYoutubeId(linkVideo));
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
