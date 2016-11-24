package com.example.vividy.mannanapp;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.widget.Toast;

/**
 * Created by MANNAN on 9/10/2016.
 */
public class MannanApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

//        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
//        FirebaseCrash.log("Activity created");

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        Toast.makeText(MannanApp.this, "App is Terminated.", Toast.LENGTH_SHORT).show();
    }
    
}
