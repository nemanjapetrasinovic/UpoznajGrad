package com.example.nemanja.upoznajgrad;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyService extends Service {

    private static final String TAG = "LOCATION_SERVICE";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 5f;
    private int mNotificationId = 001;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotifyMgr;

    private class LocationListener implements android.location.LocationListener
    {
        Location mLastLocation;
        Location currLocation;

        public LocationListener(String provider)
        {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }


        @Override
        public void onLocationChanged(Location location)
        {
            currLocation = location;
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            ExecutorService transThread = Executors.newSingleThreadExecutor();
            transThread.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        callBroadcastReceiver();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            Location tasklocation=new Location(LocationManager.GPS_PROVIDER);
            tasklocation.setLatitude(43.665307);
            tasklocation.setLongitude(20.828688);
            double distance = location.distanceTo(tasklocation);

            if(distance<10.00){
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
                mNotificationId++;
            }
        }

        public void callBroadcastReceiver(){
            Intent myFilteredResponse = new
                    Intent("com.example.nemanja.mylocationtracker.LOCATION");
            myFilteredResponse.putExtra("latitude", currLocation.getLatitude());
            myFilteredResponse.putExtra("longitude", currLocation.getLongitude());

            sendBroadcast(myFilteredResponse);
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    // LocationListener GPSListener;
    //LocationListener NetworkListener;
    LocationListener GPSListener =  new LocationListener(LocationManager.GPS_PROVIDER);
    LocationListener NetworkListener =  new LocationListener(LocationManager.NETWORK_PROVIDER);
    public MyService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
        Log.e(TAG, "initializeLocationManager");

        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    NetworkListener);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    GPSListener);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }

        mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_near_me_white_48px)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder.setVibrate(new long[] {1000,200,1000,200});
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationManager != null) {

            try {
                mLocationManager.removeUpdates(GPSListener);
                mLocationManager.removeUpdates(NetworkListener);
            } catch (Exception ex) {
                Log.i(TAG, "fail to remove location listners, ignore", ex);
            }

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
