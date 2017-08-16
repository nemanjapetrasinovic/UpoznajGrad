package com.example.nemanja.upoznajgrad;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    private int mNotificationId = 001;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_near_me_white_48px)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        final NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        mBuilder.setVibrate(new long[] {1000,200,1000,200});

        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        mNotificationId++;

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                100, mLocationListener);

    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            double longitude=location.getLongitude();
            double latitude=location.getLatitude();
            System.out.println(longitude);
            System.out.println(latitude);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };

}
