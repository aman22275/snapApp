package com.example.singh.snap_app;

import android.app.Application;

import com.parse.Parse;


public class SnapappApplication extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "d9lGEMCQrFiwsGz6r9Sy8dzbs49EVPS2mbTMY5d7", "ptKB7Wg0WvllNw0jQBIQr706WmOR609wExPCPSIX");



    }

}
