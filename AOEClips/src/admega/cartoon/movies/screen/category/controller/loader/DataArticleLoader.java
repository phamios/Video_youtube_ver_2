package admega.cartoon.movies.screen.category.controller.loader;

/**
 * @author ducna6417
 */
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.nad.aoeclips.common.model.entity.Article;
import admega.cartoon.movies.common.model.business.JSONParser;
import admega.cartoon.movies.common.utils.Constants;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class DataArticleLoader extends AsyncTaskLoader<ArrayList<Article>>
		implements Constants {
	private ArrayList<Article> mResult;
	private String categoryId;

	public DataArticleLoader(Context context, String categoryId) {
		super(context);
		this.categoryId = categoryId;
	}

	@Override
	public ArrayList<Article> loadInBackground() {
		ArrayList<Article> simpleArticles = new ArrayList<Article>();

		String url = String.format(URL_ARTICLE_LIST, categoryId);
		// get tabTitle & tab id
		JSONObject articleJSON = JSONParser.getJSONFromUrl(url);
		if (articleJSON != null) {
			try {
				JSONArray arrServices = articleJSON.getJSONArray(KEY_ROOT);
				for (int i = 0; i < arrServices.length(); i++) {
					JSONObject objArticle = arrServices.getJSONObject(i);
					String id = objArticle.getString(KEY_ID);
					String cateid = objArticle.getString(KEY_CATEID);
					String duration = objArticle.getString(KEY_VIDEO_DURATION);
					String image = objArticle.getString(KEY_VIDEO_IMAGE);
					String link = objArticle.getString(KEY_VIDEO_LINK);
					String name = objArticle.getString(KEY_VIDEO_NAME);
					simpleArticles.add(new Article(id, cateid, image, name,
							link, duration));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return simpleArticles;
	}

	/**
	 * Handles a request to start the Loader.
	 */
	@Override
	protected void onStartLoading() {
		if (mResult != null) {
			deliverResult(mResult);
		}

		// Start watching for changes in the app data.

		if (takeContentChanged() || mResult == null) {
			// If the data has changed since the last time it was loaded
			// or is not currently available, start a load.
			forceLoad();
		}
	}

	/**
	 * Called when there is new data to deliver to the client. The super class
	 * will take care of delivering it; the implementation here just adds a
	 * little more logic.
	 */
	@Override
	public void deliverResult(ArrayList<Article> data) {
		if (isReset()) {
			// An async query came in while the loader is stopped. We
			// don't need the result.
			if (data != null) {
				onReleaseResources(data);
			}
		}
		ArrayList<Article> oldData = data;
		mResult = data;
		if (isStarted()) {
			// If the Loader is currently started, we can immediately
			// deliver its results.
			super.deliverResult(data);
		}
		// At this point we can release the resources associated with
		// 'oldApps' if needed; now that the new result is delivered we
		// know that it is no longer in use.
		if (oldData != null) {
			onReleaseResources(oldData);
		}
	}

	/**
	 * Handles a request to stop the Loader.
	 */
	@Override
	protected void onStopLoading() {
		// Attempt to cancel the current load task if possible.
		cancelLoad();
	}

	/**
	 * Handles a request to cancel a load.
	 */
	@Override
	public void onCanceled(ArrayList<Article> data) {
		super.onCanceled(data);
		// At this point we can release the resources associated with 'apps'
		// if needed.
		onReleaseResources(data);
	}

	/**
	 * Handles a request to completely reset the Loader.
	 */
	@Override
	protected void onReset() {
		super.onReset();
		// Ensure the loader is stopped
		onStopLoading();
		// At this point we can release the resources associated with 'apps'
		// if needed.
		if (mResult != null) {
			onReleaseResources(mResult);
			mResult = null;
		}
		// Stop monitoring for changes.
	}

	protected void onReleaseResources(ArrayList<Article> data) {
		// For a simple List<> there is nothing to do. For something
		// like a Cursor, we would close it here.
	}

}
