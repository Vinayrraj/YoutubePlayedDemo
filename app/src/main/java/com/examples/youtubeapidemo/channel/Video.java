package com.examples.youtubeapidemo.channel;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

import com.google.gson.annotations.SerializedName;


public class Video implements Serializable {


	@SerializedName("nextPageToken")
	private String nextPageToken;

	@SerializedName("items")
	private ArrayList<VideoItem> items;

	public String getNextPageToken() {
		return nextPageToken;
	}

	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}

	public ArrayList<VideoItem> getItems() {
		if (items == null)
			items = new ArrayList<VideoItem>();
		return items;
	}

	public void setItems(ArrayList<VideoItem> items) {
		this.items = items;
	}

	public class VideoItem implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1636372722697317550L;

		@SerializedName("id")
		private Id id;

		@SerializedName("snippet")
		private Snippet snippet;

		public Id getId() {
			return id;
		}

		public void setId(Id id) {
			this.id = id;
		}

		public Snippet getSnippet() {
			return snippet;
		}

		public void setSnippet(Snippet snippet) {
			this.snippet = snippet;
		}

	}

	public class VideoThumbnail implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -9023931726811989099L;
		@SerializedName("high")
		private UrlContainer urlContainer;

		public UrlContainer getUrlContainer() {
			return urlContainer;
		}

		public void setUrlContainer(UrlContainer urlContainer) {
			this.urlContainer = urlContainer;
		}
	}

	public class UrlContainer implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1305602505088135893L;
		@SerializedName("url")
		private String url;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	public class Id implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4495416755700241037L;
		@SerializedName("videoId")
		private String videoId = null;

		public String getVideoId() {
			return videoId;
		}

		public void setVideoId(String videoId) {
			this.videoId = videoId;
		}
	}

	public class Snippet implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5534403546492843591L;

		@SerializedName("videoId")
		private String videoId;
		@SerializedName("publishedAt")
		private String publishedAt;

		@SerializedName("title")
		private String title;

		@SerializedName("description")
		private String description;

		@SerializedName("thumbnails")
		private VideoThumbnail thumbnails;

		@SerializedName("liveBroadcastContent")
		private String liveBroadcastContent;

		private String formatedDate;

		public void setPublishedAt(String publishedAt) {
			this.publishedAt = publishedAt;
		}

		public String getPublished(Context context) {
			if (formatedDate == null) {
				formatDate(context);
			}
			return formatedDate;
		}

		private void formatDate(Context context) {
//			formatedDate = Utility.dateConvertObjToString(
//					context.getString(R.string.DateFormat_BusinessFeeds_Output),
//					Utility.dateConvertStringToObject(
//							context.getString(R.string.DateFormat_VideoFeeds_Input_WithMicroSec), publishedAt));
			if (formatedDate == null) {
				formatedDate = publishedAt;
			}
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public VideoThumbnail getThumbnails() {
			return thumbnails;
		}

		public void setThumbnails(VideoThumbnail thumbnails) {
			this.thumbnails = thumbnails;
		}

		public String getLiveBroadcastContent() {
			return liveBroadcastContent;
		}

		public void setLiveBroadcastContent(String liveBroadcastContent) {
			this.liveBroadcastContent = liveBroadcastContent;
		}

	}
}
