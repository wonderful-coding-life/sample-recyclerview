package com.sample.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreViewHolder> {
    private List<Store> storeList;
    private double latitude, longitude;

    public StoreAdapter(List<Store> storeList, double latitude, double longitude) {
        this.storeList = storeList;
        this.latitude = latitude;
        this.longitude = longitude;

        Collections.sort(storeList, new Store.DistanceSorter(latitude, longitude));
    }

    public void sortByDistance() {
        Collections.sort(storeList, new Store.DistanceSorter(latitude, longitude));
        notifyDataSetChanged();
    }

    public void sortByName() {
        Collections.sort(storeList, new Store.NameSorter());
        notifyDataSetChanged();
    }

    public void sortByStat() {
        Collections.sort(storeList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_store, parent, false);
        StoreViewHolder viewHolder = new StoreViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store store = storeList.get(position);
        double distance = Util.getDistance(latitude, longitude, store.lat, store.lng);
        String distanceText = Util.getDistanceAsText(distance);
        holder.bind(store, distanceText);
    }

    @Override
    public int getItemCount() {
        if (storeList != null) {
            return storeList.size();
        }
        return 0;
    }
}
