package com.example.vitalina.moneytrack.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class LocationManager extends LocationCallback {
    private FusedLocationProviderClient provider;
    private Context context;
    private BehaviorSubject<Location> locationSubject = BehaviorSubject.create();
    public LocationManager(Context context) {
        this.context = context;
        provider = LocationServices.getFusedLocationProviderClient(context);
        LocationRequest request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(15*1000);
        request.setFastestInterval(10 * 1000);
        request.setSmallestDisplacement(1);
        boolean permissionGranted = ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (permissionGranted) {
            provider.requestLocationUpdates(request, this, Looper.myLooper());
        }
    }
    @Override
    public void onLocationResult(LocationResult locationResult) {
        super.onLocationResult(locationResult);
        locationSubject.onNext(locationResult.getLastLocation());
    }

    public BehaviorSubject<Location> getLocationSubject() {
        return locationSubject;
    }
}
