package admega.cartoon.movies.screen.category.controller;

import java.util.ArrayList;

import admega.cartoon.movies.R;

import vn.nad.aoeclips.common.model.entity.Article;
import admega.cartoon.movies.common.utils.Constants;
import admega.cartoon.movies.common.utils.Logger;
import admega.cartoon.movies.screen.category.controller.adapter.ArticleListAdapter;
import admega.cartoon.movies.screen.category.controller.loader.DataArticleLoader;
import admega.cartoon.movies.screen.detail.controller.DetailActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CategoryFragment extends Fragment implements OnItemClickListener,
		LoaderCallbacks<ArrayList<Article>>, Constants {

	public static final String TAG = CategoryFragment.class.getSimpleName();

	private ListView listView;
	private int fragmentId = 0;

	private ArrayList<Article> listArticle = new ArrayList<Article>();
	private String categoryId;
	private ArticleListAdapter adapter;

	private boolean isForceReset = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_selected_category,
				container, false);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		listView = (ListView) view.findViewById(R.id.pull_to_refresh_listview);
		adapter = new ArticleListAdapter(getActivity(), listArticle);
		Logger.out(ARGUMENT_FRAGMENT_ID, listArticle.size() + "");
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		initData();
	}

	public void initData() {
		Bundle bundle = getArguments();
		categoryId = bundle.getString(ARGUMENT_CATEGORY_ID);
		fragmentId = bundle.getInt(ARGUMENT_FRAGMENT_ID, 0);

		if (listArticle.size() == 0 || isForceReset) {
			LoaderManager lm = getActivity().getSupportLoaderManager();
			lm.destroyLoader(fragmentId);
			lm.initLoader(fragmentId, null, this);
		}
	}

	/**
	 * loader
	 */
	@Override
	public Loader<ArrayList<Article>> onCreateLoader(int arg0, Bundle arg1) {
		return new DataArticleLoader(getActivity(), categoryId);
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Article>> loader,
			ArrayList<Article> data) {
		listArticle.clear();
		listArticle.addAll(data);
		Logger.out(TAG, listArticle.size() + "");
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Article>> loader) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), DetailActivity.class);
		intent.putParcelableArrayListExtra(ARGUMENT_ARTICLES, listArticle);
		 intent.putExtra(ARGUMENT_POSITION, position);
//		 intent.putExtra(ARGUMENT_ARTICLES, listArticle.get(position).getLink());
		startActivity(intent);
	}

}
