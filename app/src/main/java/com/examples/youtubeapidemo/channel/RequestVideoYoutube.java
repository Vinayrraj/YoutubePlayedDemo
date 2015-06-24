package com.examples.youtubeapidemo.channel;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.examples.youtubeapidemo.DeveloperKey;
import com.google.gson.Gson;

public class RequestVideoYoutube<T extends Video> extends Request<T> {

    private Priority priority = Priority.HIGH;
    private Listener<T> listener;

    public RequestVideoYoutube(Context context, Listener<T> listener, ErrorListener errorListener,
                               String nextPageToken, String channelId) {
        super(Method.GET, getUrl(context, nextPageToken, channelId), errorListener);
        this.listener = listener;

        setRetryPolicy(new DefaultRetryPolicy(20000, 2, 1));
    }

    private static String getUrl(Context context, String pageToken, String channelId) {
        if (pageToken == null || pageToken == "null") {
            pageToken = "";
        } else {
            pageToken = "&pageToken=" + pageToken;
        }
        return "https://www.googleapis.com/youtube/v3/search" + "?key="
                + DeveloperKey.DEVELOPER_KEY_SERVER + "&channelId=" + channelId
                + "&part=snippet,id&order=date&maxResults=10&safeSearch=none&order=date&type=video" + pageToken;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            Video videoData = new Gson().fromJson(json, Video.class);

            return (Response<T>) Response.success(videoData, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        if (listener != null)
            listener.onResponse(response);

    }

}
