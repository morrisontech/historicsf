package rocks.morrisontech.historicsf;

import android.util.Log;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;

/**
 * Callback to refresh MarkerInfoWindow once Bitmap has been drawn
 * Created by Quinn on 1/15/17.
 */

class PicassoImageReadyCallback implements Callback {

    private Marker markerToRefresh = null;

    PicassoImageReadyCallback(Marker marker) {
        this.markerToRefresh = marker;
    }

    @Override
    public void onError() {
        Log.e(getClass().getSimpleName(), "Error loading thumbnail!");
    }

    @Override
    public void onSuccess() {
        Log.i(getClass().toString(), "SUCCESS LOADING IMAGE IN CALLBACK");
        if (markerToRefresh != null && markerToRefresh.isInfoWindowShown()) {
            Log.i(getClass().toString(), "TRYING TO REFRESH");
            markerToRefresh.showInfoWindow();
        }

    }

}


