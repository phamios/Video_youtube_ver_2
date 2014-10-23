package admega.cartoon.movies.screen.category.controller.adapter;
/**
 * @author ducna6417
 */
import java.util.ArrayList;

import admega.cartoon.movies.R;
import vn.nad.aoeclips.common.model.entity.Article;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.presenter.target.ImageViewTarget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ArticleListAdapter extends ArrayAdapter<Article> {
	private ArrayList<Article> listArticle = new ArrayList<Article>();
	private LayoutInflater mLayoutInflater;

	public ArticleListAdapter(Context context, ArrayList<Article> listArticle) {
		super(context, R.layout.item_list_video);
		this.mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listArticle = listArticle;
	}

	@Override
	public int getCount() {
		return listArticle.size();
	}

	@Override
	public Article getItem(int position) {
		return listArticle.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_list_video, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.imageViewThumbVideo = (ImageView) convertView.findViewById(R.id.imageViewVideo);
			viewHolder.textViewTitleVideo = (TextView) convertView.findViewById(R.id.textViewTitleVideo);
			viewHolder.textDuration = (TextView) convertView.findViewById(R.id.txt_duration);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Article article = listArticle.get(position);
		viewHolder.textViewTitleVideo.setText(article.getName());
		viewHolder.textDuration.setText("Duration: " + article.getDuration());
		Glide.load(article.getImage()).centerCrop().placeholder(R.drawable.icon_app).into(viewHolder.imageViewThumbVideo);

		// Locate the Banner Ad in activity_main.xml
//		AdView adView = (AdView) convertView.findViewById(R.id.adView);		
//		// Request for Ads
//		AdRequest adRequest = new AdRequest.Builder().build();
//		// Load ads into Banner Ads
//		adView.loadAd(adRequest);

		return convertView;
	}

	class ViewHolder{
		private ImageView imageViewThumbVideo;
		private TextView textViewTitleVideo;
		private TextView textDuration;
	}
	
	// solution produce Out Of Memory
	private class DownloadImageTarget extends ImageViewTarget{
		private ViewHolder holder;
		public DownloadImageTarget(ImageView imageView) {
			super(imageView);
		}
		public DownloadImageTarget(ViewHolder holder){
			super(holder.imageViewThumbVideo);
			holder = this.holder;
		}
		
		@Override
		public void onImageReady(Bitmap bitmap) {
	        // Size of image greater than 100kb, image width and image height 100px

	        if (bitmap!=null && bitmap.getHeight() > 100 && bitmap.getWidth() > 100) {
	            holder.imageViewThumbVideo.setImageBitmap(bitmap);
	        }else{
	        }
		}
	}
	
}
