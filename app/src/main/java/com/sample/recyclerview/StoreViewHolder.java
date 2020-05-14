package com.sample.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreViewHolder extends RecyclerView.ViewHolder {
    private TextView name, address, remainStat, createdAt, stockAt;

    public StoreViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        address = itemView.findViewById(R.id.address);
        remainStat = itemView.findViewById(R.id.remain_stat);
        createdAt = itemView.findViewById(R.id.created_at);
        stockAt = itemView.findViewById(R.id.stock_at);
    }

    public void bind(Store store) {
        name.setText(store.name);
        address.setText(store.addr);
        remainStat.setText(store.remain_stat);
        createdAt.setText(store.created_at);
        stockAt.setText(store.stock_at);
    }
}
