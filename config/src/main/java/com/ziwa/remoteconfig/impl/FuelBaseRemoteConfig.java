package com.ziwa.remoteconfig.impl;

import android.util.Log;

import com.ziwa.remoteconfig.IFuelBaseRemoteConfig;
import com.ziwa.remoteconfig.net.HttpHelp;
import com.ziwa.remoteconfig.net.Task;

import java.io.IOException;

import okhttp3.Response;

public class FuelBaseRemoteConfig implements IFuelBaseRemoteConfig {

    private static FuelBaseRemoteConfig INSTANCE;

    public static String KEY_REQUEST_UID = "uid";
    public static String KEY_REQUEST_CHANNEL = "ch";
    public static String KEY_REQUEST_REG = "reg";
    public static String TEST_UID = "416523575797910770b";
    public static final long DEFAULT_CACHE_EXPIRATION_SECONDS = 86400L;


    public static IFuelBaseRemoteConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (FuelBaseRemoteConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FuelBaseRemoteConfig();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Task fetch() {
        return fetch(DEFAULT_CACHE_EXPIRATION_SECONDS);
    }

    @Override
    public boolean activateFetched() {
        return false;
    }

    @Override
    public Task fetch(long cacheExpirationSeconds) {
        HttpHelp.Params urlParam = new HttpHelp.Params();
        urlParam.addParam(KEY_REQUEST_UID, "TEST_UID");
        Task task = new Task();
        HttpHelp.getInstance().request(DEFAULT_HTTP_SERVER, urlParam, null, task);
        return task;
    }

    @Override
    public int getInt(String key) {
        return 0;
    }

    @Override
    public int getInt(String key, String nameSpace) {
        return 0;
    }

    @Override
    public long getLong(String key) {
        return 0;
    }

    @Override
    public long getLong(String key, String nameSpace) {
        return 0;
    }

    @Override
    public float getFloat(String key) {
        return 0;
    }

    @Override
    public float getFloat(String key, String nameSpace) {
        return 0;
    }

    @Override
    public double getDouble(String key) {
        return 0;
    }

    @Override
    public double getDouble(String key, String nameSpace) {
        return 0;
    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public String getString(String key, String nameSpace) {
        return null;
    }

    @Override
    public byte[] getBytes(String key) {
        return new byte[0];
    }

    @Override
    public byte[] getBytes(String key, String nameSpace) {
        return new byte[0];
    }
}
