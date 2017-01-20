package rocks.morrisontech.historicsf;

import rocks.morrisontech.historicsf.entity.LandmarkEntity;

/**
 * Created by Quinn on 1/7/17.
 */

public interface OnLandmarksReceived {
    void onLandmarksReceived(LandmarkEntity[] entities);
}
