package com.sample.recyclerview;

import java.util.Comparator;

public class Store implements Comparable<Store> {
    public String addr;
    public String code;
    public String created_at;
    public double lat;
    public double lng;
    public String name;
    public String remain_stat;
    public String stock_at;
    public String type;

    public int getAmount() {
        if ("plenty".equalsIgnoreCase(remain_stat)) {
            return 0;
        } else if ("some".equalsIgnoreCase(remain_stat)) {
            return 1;
        } else if ("few".equalsIgnoreCase(remain_stat)) {
            return 2;
        } else if ("empty".equalsIgnoreCase(remain_stat)) {
            return 3;
        } else if ("break".equalsIgnoreCase(remain_stat)) {
            return 4;
        } else {
            return 5;
        }
    }

    @Override
    public int compareTo(Store other) {
        return getAmount() - other.getAmount();
    }

    public static class NameSorter implements Comparator<Store> {
        public int compare(Store store1, Store store2) {
            store1.name = (store1.name == null) ? "" : store1.name;
            store2.name = (store2.name == null) ? "" : store2.name;
            return store1.name.compareTo(store2.name);
        }
    }

    public static class DistanceSorter implements Comparator<Store> {
        private double latitude, longitude;

        public DistanceSorter(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public int compare(Store store1, Store store2) {
            double distance1 = Util.getDistance(latitude, longitude, store1.lat, store1.lng);
            double distance2 = Util.getDistance(latitude, longitude, store2.lat, store2.lng);
            return (int) (distance1 - distance2);
        }
    }
}
