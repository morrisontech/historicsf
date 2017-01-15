package rocks.morrisontech.historicsf;

import android.util.Log;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;

/**
 * Created by Quinn on 1/15/17.
 */

public class PicassoImageReadyCallback implements Callback {

    Marker markerToRefresh = null;

    PicassoImageReadyCallback(Marker marker) {
        this.markerToRefresh = marker;
    }

    @Override
    public void onError() {
        Log.e(getClass().getSimpleName(), "Error loading thumbnail!");
    }

    @Override
    public void onSuccess() {
        if (markerToRefresh != null && markerToRefresh.isInfoWindowShown()) {
            markerToRefresh.showInfoWindow();
        }

    }

}


