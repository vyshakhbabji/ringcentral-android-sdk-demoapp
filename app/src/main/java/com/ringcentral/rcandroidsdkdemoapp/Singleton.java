package com.ringcentral.rcandroidsdkdemoapp;

import com.ringcentral.rc_android_sdk.rcsdk.platform.Platform;

/**
 * Created by vyshakh.babji on 11/9/15.
 */
public class Singleton {

    private static Singleton ourInstance = null;
    Platform platform;

    private Singleton() {
    }

    public static Singleton getInstance() {

        if (ourInstance == null)
            ourInstance = new Singleton();

        return ourInstance;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }


}
