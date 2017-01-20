package rocks.morrisontech.historicsf;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import rocks.morrisontech.historicsf.entity.LandmarkEntity;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, OnLandmarksReceived, GoogleMap.OnInfoWindowClickListener {

    private static final String LOG_TAG = "Main Activity";
    private GoogleMap mGoogleMap;
    // Marker HashMap to track if image is loaded in marker
    //private Hashtable<String, Boolean> markerImageBoolean = new Hashtable<>();
    Target target = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            MapFragment mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_map);
            mMapFragment.getMapAsync(this);
            // async task to download initial historic districts and sites
            new AsyncLandmarkEntities(this).execute();
            Picasso.with(MainActivity.this).setLoggingEnabled(true);
        } else {
            // display error in dialog that allows user to go to settings to change network settings
        }
    }

    /**
     * Set all map UI
     * OnMarkerClickListener
     * OnPolygonClickListener
     * setMapToolbarEnabled(true)
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);

        // used to set center view
        LatLng sanFrancisco = new LatLng(37.763147, -122.445662);

        mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            // todo: convert this to custom adapter class
            View infoWindowView = null;

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(final Marker marker) {
                LandmarkEntity landmarkEntity = (LandmarkEntity) marker.getTag();

                if(infoWindowView == null) {
                    infoWindowView = getLayoutInflater().inflate(R.layout.marker_info, null);
                }

                // TODO: if the following LandmarkEntity values are null, set view visibility to Gone
                TextView landmarkTitleText = (TextView) infoWindowView.findViewById(R.id.title_textView);
                if (landmarkEntity.getName() != null) {
                    landmarkTitleText.setVisibility(View.VISIBLE);
                    landmarkTitleText.setText(landmarkEntity.getName());
                } else {
                    landmarkTitleText.setVisibility(View.GONE);
                }

                TextView yearBuiltTextView = (TextView) infoWindowView.findViewById(R.id.year_built);
                if (landmarkEntity.getYear_built() != null) {
                    yearBuiltTextView.setVisibility(View.VISIBLE);
                    yearBuiltTextView.setText(landmarkEntity.getYear_built());
                } else {
                    yearBuiltTextView.setVisibility(View.GONE);
                }

                TextView architectTextView = (TextView) infoWindowView.findViewById(R.id.architect_textView);
                if (landmarkEntity.getArchitect() != null) {
                    architectTextView.setVisibility(View.VISIBLE);
                    architectTextView.setText(landmarkEntity.getArchitect());
                } else {
                    architectTextView.setVisibility(View.GONE);
                }
                mGoogleMap.setOnInfoWindowClickListener(MainActivity.this);

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


    @Override
    public void onLandmarksReceived(LandmarkEntity[] landmarks) {
        LandmarkEntity[] mLandmarkEntities = landmarks;
        // add to map
        for (LandmarkEntity landmark : mLandmarkEntities) {
            Marker marker;
            LatLng latLng = new LatLng(landmark.getLatitude(), landmark.getLongitude());
            marker = mGoogleMap.addMarker(new MarkerOptions()
                    .snippet(landmark.getThumbnail())
                    .position(latLng)
                    .title(landmark.getName())
            );

            marker.setTag(landmark);
            //markerImageBoolean.put(marker.getId(), false);

        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        LandmarkEntity landmarkEntity = (LandmarkEntity) marker.getTag();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("MyClass", landmarkEntity);
        startActivity(intent);

    }
}
