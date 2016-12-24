package rocks.morrisontech.historicsf.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 12/22/16.
 */

public class GeoJsonEntity {

    private List<float[]> coordinates = new ArrayList<>();
    private String type;

    public void GeoJsonEntity() {
        // default constructor
    }

    public void setCoordinates(List<float[]> coordinates) {
        this.coordinates = coordinates;
    }

    public List<float[]> getCoordinates() {

        return coordinates;
    }



//    "the_geom": {
//        "type": "MultiPoint",
//                "coordinates": [
//        [
//        -122.40145899999999,
//                37.793304000000035
//        ]
//        ]
//    },
}
