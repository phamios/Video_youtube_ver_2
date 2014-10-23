package admega.cartoon.movies.screen.category.controller.adapter;

/**
 * @author ducna6417
 */
import java.util.ArrayList;

import com.bumptech.glide.Glide;
import admega.cartoon.movies.R;

import vn.nad.aoeclips.common.model.entity.Category;
import admega.cartoon.movies.common.utils.Constants;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryArrayAdapter extends ArrayAdapter<Category> implements Constants{

	private ArrayList<Category> listCategories = new ArrayList<Category>();
	private LayoutInflater mLayoutInflater;

	public CategoryArrayAdapter(Context context,
			ArrayList<Category> listCategories) {
		super(context, R.layout.item_list_category);
		this.mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listCategories = listCategories;
	}

	@Override
	public int getCount() {
		return listCategories.size();
	}

	@Override
	public Category getItem(int position) {
		return listCategories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_list_category, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.imageViewIconCategory = (ImageView) convertView.findViewById(R.id.imageViewIconCategory);
			viewHolder.textViewTitleCategory = (TextView) convertView.findViewById(R.id.textViewTitleCategory);
			viewHolder.textViewCountItem = (TextView) convertView.findViewById(R.id.textViewCount);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Category category = (Category) getItem(position);
		viewHolder.textViewTitleCategory.setText(category.getCateName());
		viewHolder.textViewCountItem.setText("   +" + category.getCountItem() + " movies");
		Glide.load("http://admega.vn/src/15/"+category.getCateIcon()).centerCrop().placeholder(R.drawable.icon_app).into(viewHolder.imageViewIconCategory);
		//Glide.load(URL_IMAGE_DEFAULT).centerCrop().placeholder(R.drawable.icon_app).into(viewHolder.imageViewIconCategory);
		return convertView;
	}

	class ViewHolder {
		private ImageView imageViewIconCategory;
		private TextView textViewTitleCategory;
		private TextView textViewCountItem;
	}

}
