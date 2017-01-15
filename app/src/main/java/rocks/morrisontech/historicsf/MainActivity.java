package rocks.morrisontech.historicsf;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.Hashtable;

import rocks.morrisontech.historicsf.entity.LandmarkEntity;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, OnLandmarksReceived {

    private static final String LOG_TAG = "Main Activity";
    private GoogleMap mGoogleMap;
    private LandmarkEntity[] mLandmarkEntities;
    // Marker HashMap to track if image is loaded in marker
    private Hashtable<String, Boolean> markerImageBoolean = new Hashtable<>();


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
            // display error
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

            View infoWindowView = null;
            Marker previousMarker = null;

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LandmarkEntity landmarkEntity = (LandmarkEntity) marker.getTag();

                infoWindowView = getLayoutInflater().inflate(R.layout.marker_info, null);

                previousMarker = marker;

                TextView landmarkTitleText = (TextView) infoWindowView.findViewById(R.id.title_textView);
                landmarkTitleText.setText(landmarkEntity.getName());

                // get image
                ImageView thumbnail = (ImageView) infoWindowView.findViewById(R.id.thumbnail_imageView);
                String thumbnailUrl = landmarkEntity.getThumbnail();

                String imageUrl = JsoupHelper.getImageUrl(thumbnailUrl);

                thumbnail.invalidate();
                boolean imageLoaded = markerImageBoolean.get(marker.getId());

                // TODO: add error handling in case imageUrl comes back null
                if (imageLoaded /* default: false */) {
                    Picasso.with(MainActivity.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_image_black_24px)
                            .into(thumbnail);
                } else {
                    imageLoaded = true;
                    markerImageBoolean.put(marker.getId(), imageLoaded);
                    Picasso.with(MainActivity.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_image_black_24px)
                            .into(thumbnail, new PicassoImageReadyCallback(marker));
                }


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
        mLandmarkEntities = landmarks;
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
            markerImageBoolean.put(marker.getId(), false);

        }
    }
}
