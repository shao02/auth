package com.life.event.www.eventlife.location;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import im.delight.android.location.SimpleLocation;

/**
 * Created by xu_s on 5/31/17.
 */

public class GpsLocation {
    private static final String TAG = "GpsLocation";
    private SimpleLocation location;
    private Geocoder geocoder;
    public GpsLocation(Activity activity){
        location = new SimpleLocation(activity);
        geocoder = new Geocoder(activity, Locale.getDefault());
    }

    public String getZipLocation() throws IOException {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Log.d(TAG, "latitude:" + latitude + " longitude:" + longitude);
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
      //  return addresses.get(0).getPostalCode();
        return "heloo";
    }

    public String getZipLocationWithHttp (){
        location.beginUpdates();

        AsyncHttpClient client = new AsyncHttpClient();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Log.d(TAG, "latitude:" + latitude + " longitude:" + longitude);
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + ","+longitude+"&sensor=true";
        Log.d(TAG, "url:" + url);

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String body = new String(responseBody);
                Log.d(TAG, "body:" + body);
                Gson gson = new Gson();
                Map<String,Object> cur = gson.fromJson(body, Map.class);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
        return null;
    }
}
