package com.example.fa_mehulbhai_c0849394_android;

public class ModelClass {

    int id;
    private String placeLatitude;
    private String placeLongitude;
    private String placeName;

    public ModelClass() {
    }

    public ModelClass(int id, String placeLatitude, String placeLongitude, String placeName) {
        this.id = id;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.placeName = placeName;
    }

    public ModelClass(String pLati, String pLongi, String pName) {
        this.placeLatitude = pLati;
        this.placeLongitude = pLongi;
        this.placeName = pName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceLatitude() {
        return placeLatitude;
    }

    public void setPlaceLatitude(String placeLatitude) {
        this.placeLatitude = placeLatitude;
    }

    public String getPlaceLongitude() {
        return placeLongitude;
    }

    public void setPlaceLongitude(String placeLongitude) {
        this.placeLongitude = placeLongitude;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

}
