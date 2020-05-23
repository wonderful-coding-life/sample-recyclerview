package com.sample.recyclerview;

import java.util.Comparator;

public class StoreComparatorByStat implements Comparator<Store> {
    @Override
    public int compare(Store o1, Store o2) {
        return getIndex(o1.remain_stat) - getIndex(o2.remain_stat);
    }

    private int getIndex(String stat) {
        if ("plenty".equalsIgnoreCase(stat)) {
            return 0;
        } else if ("some".equalsIgnoreCase(stat)) {
            return 1;
        } else if ("few".equalsIgnoreCase(stat)) {
            return 2;
        } else if ("empty".equalsIgnoreCase(stat)) {
            return 3;
        } else if ("break".equalsIgnoreCase(stat)) {
            return 4;
        }
        return 5;
    }
}
