package rocks.morrisontech.historicsf.entity;

import java.io.Serializable;

/**
 * Created by Quinn on 12/22/16.
 */

public class LandmarkEntity implements Serializable {

    private String address;
    private String architect;
    private String designatio;
    private int landmark_no;
    private String large_photo;
    private float latitude;
    private float longitude;
    private String name;
    private int objectid;
    private String parcel;
    private String pimlink;
    private GeoJsonEntity the_geom;
    private String thumbnail;
    private String year_built;

    public void LandmarkEntity() {
        // default constructor
    }
//    Json Response ----

//    "address": "400 CALIFORNIA ST",
//            "architect": "BLISS & FAVILLE",
//            "designatio": "http://sfplanninggis.org/docs/landmarks_and_districts/LM3.pdf",
//            "landmark_no": "3",
//            "large_photo": "http://sfplanninggis.org/Preservation/Landmarks/Large/3.jpg",
//            "latitude": "37.793304",
//            "longitude": "-122.401459",
//            "name": "BANK OF CALIFORNIA",
//            "objectid": "3",
//            "parcel": "0239/003",
//            "pimlink": "http://propertymap.sfplanning.org?search=0239/003",
//            "the_geom": {
//        "type": "MultiPoint",
//                "coordinates": [
//        [
//        -122.40145899999999,
//                37.793304000000035
//        ]
//        ]
//    },
//            "thumbnail": "http://sfplanninggis.org/Preservation/Landmarks/Docs/3.jpg",
//            "year_built": "1906/1908"


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArchitect() {
        return architect;
    }

    public void setArchitect(String architect) {
        this.architect = architect;
    }

    public String getDesignatio() {
        return designatio;
    }

    public void setDesignatio(String designatio) {
        this.designatio = designatio;
    }

    public int getLandmark_no() {
        return landmark_no;
    }

    public void setLandmark_no(int landmark_no) {
        this.landmark_no = landmark_no;
    }

    public String getLarge_photo() {
        return large_photo;
    }

    public void setLarge_photo(String large_photo) {
        this.large_photo = large_photo;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getObjectid() {
        return objectid;
    }

    public void setObjectid(int objectid) {
        this.objectid = objectid;
    }

    public String getParcel() {
        return parcel;
    }

    public void setParcel(String parcel) {
        this.parcel = parcel;
    }

    public String getPimlink() {
        return pimlink;
    }

    public void setPimlink(String pimlink) {
        this.pimlink = pimlink;
    }

    public GeoJsonEntity getThe_geom() {
        return the_geom;
    }

    public void setThe_geom(GeoJsonEntity the_geom) {
        this.the_geom = the_geom;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getYear_built() {
        return year_built;
    }

    public void setYear_built(String year_built) {
        this.year_built = year_built;
    }
}
