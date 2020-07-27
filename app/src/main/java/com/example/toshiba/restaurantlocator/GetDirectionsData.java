package com.example.toshiba.restaurantlocator;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetDirectionsData  extends AsyncTask<Object, String, String> {

    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    String duration, distance;
    LatLng LatLng;



    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        LatLng = (LatLng)objects[2];

        DownloadUrl downloadUrl = new DownloadUrl();
        try
        {
            googleDirectionsData = downloadUrl.ReadTheURL(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s)
    {
        HashMap<String, String> directionsList = null;
        DataParser dataParser = new DataParser();
        directionsList = dataParser.parseDirections(s);
        duration = directionsList.get("duration");
        distance = directionsList.get("distance");

        mMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(LatLng);
        markerOptions.draggable(true);
        markerOptions.title("Duration = " + duration);
        markerOptions.snippet("Distance = " + distance);

        mMap.addMarker(markerOptions);


    }
}
