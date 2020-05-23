package com.sample.recyclerview;

import java.util.Comparator;

public class StoreComparatorByDistance implements Comparator<Store> {
    private double latitude, longitude;

    public StoreComparatorByDistance(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public int compare(Store o1, Store o2) {
        double distance1 = Util.getDistance(latitude, longitude, o1.lat, o1.lng);
        double distance2 = Util.getDistance(latitude, longitude, o2.lat, o2.lng);
        return (int)(distance1 - distance2);
    }
}
