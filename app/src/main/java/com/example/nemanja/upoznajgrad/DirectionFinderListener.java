package com.example.nemanja.upoznajgrad;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import com.example.nemanja.upoznajgrad.Route;


public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}