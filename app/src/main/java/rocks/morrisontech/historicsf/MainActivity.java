package rocks.morrisontech.historicsf;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import rocks.morrisontech.historicsf.entity.LandmarkEntity;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private static final String LOG_TAG = "Main Activity";
    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            MapFragment mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_map);
            mMapFragment.getMapAsync(this);
            // async task to download initial historic districts and sites
            new DownloadData().execute();
        } else {
            // display error
        }
    }

    /**
     * Set all map UI
     * OnMarkerClickListener
     * OnPolygonClickListener
     * setMapToolbarEnabled(true)
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);

        LatLng sanFrancisco = new LatLng(37.763147, -122.445662);

       mGoogleMap.addMarker(new MarkerOptions().position(sanFrancisco));
       mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
           @Override
           public View getInfoWindow(Marker marker) {
               return null;
           }

           @Override
           public View getInfoContents(Marker marker) {
               View infoWindowView = getLayoutInflater().inflate(R.layout.marker_info, null);
               return infoWindowView;
           }
       });

        new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        };

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sanFrancisco, 12f));
    }

    private class DownloadData extends AsyncTask<String, Void, String> {


        StringBuilder jsonString = new StringBuilder();
        HttpsURLConnection urlConnection;
        BufferedReader reader = null;

        @Override
        protected String doInBackground(String... strings) {
            // build uri
            // download and return json string

            try {

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https");
                builder.authority("data.sfgov.org");
                builder.appendPath("resource");
                builder.appendPath(/*endpoint*/"798h-cfqf.json");
                builder.appendQueryParameter("$$app_token", "XmhHBPPmpGboNkk0yEwWb3R46");


                String url = builder.build().toString();
                Log.i("URL", url);

                // build custom URL here based on params passed in String... strings
                URL serviceUrl = new URL(url);
                urlConnection = (HttpsURLConnection) serviceUrl.openConnection();
                urlConnection.setRequestMethod("GET");
                InputStream ins = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(ins);
                reader = new BufferedReader(isr);
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    jsonString.append(inputLine);
                }

                Log.i("JSON", jsonString.toString());
                urlConnection.disconnect();
                // only returns null if there is an exception
                return String.valueOf(jsonString);

        } catch (IOException e) {
            Log.e(LOG_TAG, "unable to retrieve data");
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
    }


    }
}
