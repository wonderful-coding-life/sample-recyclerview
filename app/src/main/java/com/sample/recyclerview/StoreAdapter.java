package com.sample.recyclerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {
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

    public class StoreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView pharmacy;
        private TextView name, address, distance, createdAt, stockAt;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            pharmacy = itemView.findViewById(R.id.pharmacy);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            distance = itemView.findViewById(R.id.distance);
            createdAt = itemView.findViewById(R.id.created_at);
            stockAt = itemView.findViewById(R.id.stock_at);
            itemView.setOnClickListener(this);
        }

        public void bind(Store store, String distanceAsText) {
            pharmacy.setColorFilter(getStockColor(store.remain_stat));
            name.setText(store.name);
            address.setText(store.addr);
            distance.setText(distanceAsText);
            createdAt.setText("업데이트: " + store.created_at);
            stockAt.setText("재고입고: " + store.stock_at);
        }

        private int getStockColor(String remainStat) {
            if ("plenty".equals(remainStat)) return ContextCompat.getColor(pharmacy.getContext(), R.color.stockPlenty);
            if ("some".equals(remainStat)) return ContextCompat.getColor(pharmacy.getContext(), R.color.stockSome);
            if ("few".equals(remainStat)) return ContextCompat.getColor(pharmacy.getContext(), R.color.stockFew);
            if ("empty".equals(remainStat)) return ContextCompat.getColor(pharmacy.getContext(), R.color.stockEmpty);
            if ("break".equals(remainStat)) return ContextCompat.getColor(pharmacy.getContext(), R.color.stockBreak);
            return 0;
        }

        @Override
        public void onClick(View view) {
            Store store = storeList.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), MapActivity.class);
            intent.putExtra("currentLatitude", latitude);
            intent.putExtra("currentLongitude", longitude);
            intent.putExtra("storeLatitude", store.lat);
            intent.putExtra("storeLongitude", store.lng);
            intent.putExtra("remainStat", store.remain_stat);
            view.getContext().startActivity(intent);
        }
    }
}
