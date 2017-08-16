package com.example.nemanja.upoznajgrad;

import android.*;
import android.Manifest;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    private int mNotificationId = 001;

    private LocationListener lListener;
    private LocationManager lManager;

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


        mBuilder.setVibrate(new long[]{1000, 200, 1000, 200});

        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        mNotificationId++;


        lListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
                mNotificationId++;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent goToSettings=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                goToSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(goToSettings);
            }
        };

        lManager=(LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        //noinspection MissingPermission
        lManager.requestLocationUpdates(lManager.GPS_PROVIDER,5000,0,lListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(lManager!=null){
            lManager.removeUpdates(lListener);
        }
    }
}
