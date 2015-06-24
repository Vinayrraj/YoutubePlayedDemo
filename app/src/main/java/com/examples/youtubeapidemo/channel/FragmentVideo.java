package com.examples.youtubeapidemo.channel;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.examples.youtubeapidemo.R;
import com.google.android.youtube.player.YouTubeIntents;


public class FragmentVideo extends Fragment {

    private static final String IS_PERSONAL_V_FEED = "IS_PERSONAL_V_FEED";
    private static final String CHANNEL_ID = "UCNJcSUSzUeFm8W9P7UUlSeQ";
    private View container = null;
    private ListView listNews;
    private View footerListView = null;
    private AdapterVideo adapterVideo = null;
    private Activity activity = null;
    private Video video = null;
    private boolean isPersonal = true;
    private OnItemClickListener onItenClick = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Video.VideoItem feed = (Video.VideoItem) parent.getAdapter().getItem(position);
            Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(activity, feed.getId().getVideoId(), true,
                    false);
            startActivity(intent);
        }
    };

    public static FragmentVideo newInstance(boolean isPersonal) {
        FragmentVideo f = new FragmentVideo();
        Bundle b = new Bundle();
        b.putBoolean(IS_PERSONAL_V_FEED, isPersonal);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isPersonal = getArguments().getBoolean(IS_PERSONAL_V_FEED);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup groupContainer, Bundle savedInstanceState) {
        container = inflater.inflate(R.layout.fragment_list, groupContainer, false);
        final LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        listNews = (ListView) container.findViewById(R.id.NEWS_list);
        listNews.setOnItemClickListener(onItenClick);
        setFooterToList(li);
        fetchVideos();
        return container;
    }

    public void refreshVideos() {
        video = null;
        if (adapterVideo != null) {
            adapterVideo.notifyDataSetChanged();
        }
        fetchVideos();

    }

    private void setFooterToList(final LayoutInflater li) {
        footerListView = li.inflate(R.layout.adapter_footer_loadmore, null, false);
        footerListView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                fetchVideos();

            }
        });

        listNews.addFooterView(footerListView, null, false);

    }

    private void fetchVideos() {

        fetchVideosFromServer();

    }

    private void fetchVideosFromServer() {

        Listener<Video> onSuccess = new Listener<Video>() {

            @Override
            public void onResponse(Video response) {
                if (video == null) {
                    video = response;
                    adapterVideo = new AdapterVideo(getActivity(), video.getItems());
                    listNews.setAdapter(adapterVideo);

                } else {
                    video.getItems().addAll(response.getItems());
                    video.setNextPageToken(response.getNextPageToken());
                    adapterVideo.notifyDataSetChanged();

                }
                if (video.getNextPageToken() == null) {
                    footerListView.setVisibility(View.GONE);
                } else {
                    footerListView.setVisibility(View.VISIBLE);
                }
            }
        };

        ErrorListener onError = new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        };
        String nextPageTOken = null;
        if (video != null && video.getNextPageToken() != null) {
            nextPageTOken = video.getNextPageToken();
        }
        final RequestVideoYoutube<Video> request = new RequestVideoYoutube<Video>(activity, onSuccess, onError,
                nextPageTOken, CHANNEL_ID);

        RequestQueue volleyRequestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        volleyRequestQueue.add(request);
    }


}
