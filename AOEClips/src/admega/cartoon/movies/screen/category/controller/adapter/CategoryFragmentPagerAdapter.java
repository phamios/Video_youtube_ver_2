package admega.cartoon.movies.screen.category.controller.adapter;
/**
 * @author ducna6417
 */

import java.util.ArrayList;

import vn.nad.aoeclips.common.model.entity.Category;
import admega.cartoon.movies.common.utils.Logger;
import admega.cartoon.movies.screen.category.controller.CategoryFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {
	public static final String ARGUMENT_CATEGORIES = "ARGUMENT_CATEGORIES";

	private ArrayList<Category> simpleCategories;

	public CategoryFragmentPagerAdapter(FragmentManager fm, Bundle bundle) {
		super(fm);
		simpleCategories = bundle.getParcelableArrayList(ARGUMENT_CATEGORIES);
	}

	@Override
	public Fragment getItem(int position) {
		Bundle args = new Bundle();
		Fragment ft = new CategoryFragment();
		args.putString(CategoryFragment.ARGUMENT_CATEGORY_ID, simpleCategories
				.get(position).getId());
		Logger.d("categoryId ===========> ", simpleCategories.get(position)
				.getId());
		args.putInt(CategoryFragment.ARGUMENT_FRAGMENT_ID, position);
		ft.setArguments(args);
		return ft;
	}

	@Override
	public int getCount() {
		return simpleCategories.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return simpleCategories.get(position).getCateName();
	}
}
