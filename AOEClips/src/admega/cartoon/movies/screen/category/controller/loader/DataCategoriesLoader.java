package admega.cartoon.movies.screen.category.controller.loader;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.nad.aoeclips.common.model.entity.Category;
import admega.cartoon.movies.common.model.business.JSONParser;
import admega.cartoon.movies.common.utils.Constants;
import admega.cartoon.movies.common.utils.Logger;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * @author ducna6417
 */
public class DataCategoriesLoader extends AsyncTaskLoader<ArrayList<Category>> {

	private ArrayList<Category> mResult;

	/**
	 * @param context
	 */
	public DataCategoriesLoader(Context context) {
		super(context);
	}

	@Override
	public ArrayList<Category> loadInBackground() {
		ArrayList<Category> simpleCategories = new ArrayList<Category>();
		/**
		 * { id: "22", catename: "learn android", cateroot: "22", cateicon:
		 * null, active: "1" },
		 */
		// get tabTitle & tab id
		JSONObject categoriesJSON = JSONParser
				.getJSONFromUrl(Constants.URL_CATEGORY_LIST);
		Logger.out("categoriesJSON : ", categoriesJSON.toString());
		if (categoriesJSON != null) {
			try {
				JSONArray arrServices = categoriesJSON.getJSONArray("store8x");
				for (int i = 0; i < arrServices.length(); i++) {
					JSONObject objCategory = arrServices.getJSONObject(i);
					String id = objCategory.getString("id");
					String catename = objCategory.getString("catename");
					String cateimages = objCategory.getString("cateicon");
					String countitem = objCategory.getString("count_item");
					simpleCategories.add(new Category(id, catename.toUpperCase(), cateimages,countitem));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return simpleCategories;
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
	public void deliverResult(ArrayList<Category> data) {
		if (isReset()) {
			// An async query came in while the loader is stopped. We
			// don't need the result.
			if (data != null) {
				onReleaseResources(data);
			}
		}
		ArrayList<Category> oldData = data;
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
	public void onCanceled(ArrayList<Category> data) {
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

	protected void onReleaseResources(ArrayList<Category> data) {
		// For a simple List<> there is nothing to do. For something
		// like a Cursor, we would close it here.
	}
}
