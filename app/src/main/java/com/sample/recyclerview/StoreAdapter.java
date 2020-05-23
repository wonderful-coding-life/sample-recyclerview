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

    public StoreAdapter(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setList(List<Store> storeList) {
        this.storeList = storeList;
        Collections.sort(this.storeList, new StoreComparatorByDistance(latitude, longitude));
        notifyDataSetChanged();
    }

    public void sortByDistance() {
        Collections.sort(this.storeList, new StoreComparatorByDistance(latitude, longitude));
        notifyDataSetChanged();
    }

    public void sortByName() {
        Collections.sort(this.storeList, new StoreComparatorByName());
        notifyDataSetChanged();
    }

    public void sortByStat() {
        Collections.sort(this.storeList, new StoreComparatorByStat());
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
        String distanceAsText = Util.getDistanceAsText(distance);
        holder.bind(store, distanceAsText);
    }

    @Override
    public int getItemCount() {
        if (storeList != null) {
            return storeList.size();
        }
        return 0;
    }
}
