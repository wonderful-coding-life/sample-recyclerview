package com.sample.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView storeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("반경 5km 이내의 공적마스크 판매처 목록");

        storeList = findViewById(R.id.store_list);
        storeList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        storeList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Intent intent = getIntent();
        double longitude = intent.getDoubleExtra("longitude", 0);
        double latitude = intent.getDoubleExtra("latitude", 0);
        fetchStoreSales(longitude, latitude, 5000);
    }

    private void fetchStoreSales(final double longitude, final double latitude, int meter) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://8oi9s0nnth.apigw.ntruss.com").addConverterFactory(GsonConverterFactory.create()).build();
        MaskApi maskApi = retrofit.create(MaskApi.class);
        maskApi.getStoresByGeo(latitude, longitude, meter).enqueue(new Callback<StoreSaleResult>() {
            @Override
            public void onResponse(Call<StoreSaleResult> call, Response<StoreSaleResult> response) {
                if (response.code() == 200) {
                    StoreSaleResult result = response.body();
                    // sort by stat
                    // Collections.sort(result.stores);
                    // sort by name
                    //Collections.sort(result.stores, new Store.NameSorter());
                    // sort by distance
                    // Collections.sort(result.stores, new Store.DistanceSorter(latitude, longitude));
                    StoreAdapter adapter = new StoreAdapter(result.stores, latitude, longitude);
                    storeList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<StoreSaleResult> call, Throwable t) {

            }
        });
    }
}
