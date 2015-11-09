package com.ringcentral.rcandroidsdkdemoapp;

import platform.Platform;

/**
 * Created by vyshakh.babji on 11/9/15.
 */
public class Singleton {

    private static Singleton ourInstance = null;

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    Platform platform;

    public static Singleton getInstance() {

        if(ourInstance==null)
            ourInstance= new Singleton();

        return ourInstance;
    }

    private Singleton() {
    }


}
