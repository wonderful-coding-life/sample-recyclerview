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
        remainStat.setText(getStockDesc(store.remain_stat));
        createdAt.setText("업데이트: " + store.created_at);
        stockAt.setText("재고입고: " + store.stock_at);
    }

    private String getStockDesc(String remainStat) {
        if ("plenty".equals(remainStat)) return "재고 100개 이상";
        if ("some".equals(remainStat)) return "재고 30개 이상";
        if ("few".equals(remainStat)) return "재고 2개 이상";
        if ("empty".equals(remainStat)) return "재고 0 ~ 1개";
        if ("break".equals(remainStat)) return "판매중지";
        return null;
    }
}
