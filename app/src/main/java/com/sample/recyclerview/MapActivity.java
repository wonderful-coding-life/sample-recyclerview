package com.sample.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, ViewTreeObserver.OnGlobalLayoutListener {

    private SupportMapFragment mapFragment;
    private double currentLatitude, currentLongitude, storeLatitude, storeLongitude;
    private String remainStat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        currentLatitude = getIntent().getDoubleExtra("currentLatitude", 0);
        currentLongitude = getIntent().getDoubleExtra("currentLongitude", 0);
        storeLatitude = getIntent().getDoubleExtra("storeLatitude", 0);
        storeLongitude = getIntent().getDoubleExtra("storeLongitude", 0);
        remainStat = getIntent().getStringExtra("remainStat");

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getView().getViewTreeObserver().addOnGlobalLayoutListener(this);
        //mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng currentPosition = new LatLng(currentLatitude, currentLongitude);
        LatLng storePosition = new LatLng(storeLatitude, storeLongitude);

        googleMap.addMarker(new MarkerOptions().position(currentPosition).title("현재위치"));

        int stockIconResource = R.drawable.poi_gray;
        if ("plenty".equals(remainStat)) {
            stockIconResource = R.drawable.poi_green;
        } else if ("some".equals(remainStat)) {
            stockIconResource = R.drawable.poi_yellow;
        } else if ("few".equals(remainStat)) {
            stockIconResource = R.drawable.poi_red;
        }
        googleMap.addMarker(new MarkerOptions().position(storePosition).title("약국").icon(BitmapDescriptorFactory.fromResource(stockIconResource)));

        LatLngBounds.Builder builder = LatLngBounds.builder();
        builder.include(currentPosition);
        builder.include(storePosition);
        LatLngBounds bounds = builder.build();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, convertDpToPixel(100, this)));
    }

    @Override
    public void onGlobalLayout() {
        mapFragment.getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        mapFragment.getMapAsync(this);
    }

    public int convertDpToPixel(int dp, Context context) {
        return (int)(dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}