package com.examples.youtubeapidemo.channel;

import java.util.ArrayList;

import com.examples.youtubeapidemo.R;
import com.examples.youtubeapidemo.channel.Video.VideoItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AdapterVideo extends BaseAdapter {

	private Context context;
	private ArrayList<VideoItem> videoFeeds;
	private LayoutInflater inflater;
	//protected DisplayImageOptions options;
	//protected ImageLoader imageLoader;

	public AdapterVideo(Context context, ArrayList<VideoItem> videoFeeds) {
		super();
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.videoFeeds = videoFeeds;
		//imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder().cacheInMemory(true)
//				.showImageForEmptyUri(R.drawable.language_divider).bitmapConfig(Bitmap.Config.RGB_565)
//				.showImageOnFail(R.drawable.language_divider).cacheOnDisc(true).build();
	}

	@Override
	public int getCount() {
		if (videoFeeds != null && videoFeeds.size() > 0)
			return videoFeeds.size();
		else
			return 0;
	}

	@Override
	public VideoItem getItem(int arg0) {
		try {
			return videoFeeds.get(arg0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			holder = new ViewHolder(convertView = inflater.inflate(R.layout.record_video, null));
		}

		if (getItem(position).getSnippet() != null && getItem(position).getSnippet().getTitle() != null) {
			holder.title.setVisibility(View.VISIBLE);
			holder.title.setText(getItem(position).getSnippet().getTitle());
		} else {
			holder.title.setVisibility(View.GONE);
		}

		if (getItem(position).getSnippet() != null && getItem(position).getSnippet().getDescription() != null) {
			holder.detail.setVisibility(View.VISIBLE);
			holder.detail.setText(getItem(position).getSnippet().getTitle());
		} else {
			holder.detail.setVisibility(View.GONE);
		}

		if (getItem(position).getSnippet() != null && getItem(position).getSnippet().getPublished(context) != null) {
			holder.date.setVisibility(View.VISIBLE);
			holder.date.setText(getItem(position).getSnippet().getPublished(context));
		} else {
			holder.date.setVisibility(View.GONE);
		}

//		try {
//			imageLoader.displayImage(getItem(position).getSnippet().getThumbnails().getUrlContainer().getUrl(),
//					holder.image, options);
//		} catch (Exception e) {
//			holder.image.setImageResource(R.drawable.language_divider);
//			e.printStackTrace();
//		}

		return convertView;

	}

	protected class ViewHolder {
		private TextView title;
		private TextView date;
		private TextView detail;
		private ImageView image;

		public ViewHolder(View view) {
			title = (TextView) view.findViewById(R.id.RECORD_VIDEO_heading);
			date = (TextView) view.findViewById(R.id.RECORD_VIDEO_date);
			detail = (TextView) view.findViewById(R.id.RECORD_VIDEO_detail);
			image = (ImageView) view.findViewById(R.id.RECORD_Vodeo_iv);
			view.setTag(this);
		}
	}

}
