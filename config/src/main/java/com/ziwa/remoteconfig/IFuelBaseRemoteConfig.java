package com.ziwa.remoteconfig;

import com.ziwa.remoteconfig.net.Task;

/**
 * Created by alex on 17-1-6.
 */

public interface IFuelBaseRemoteConfig {
    String KEY_HTTP_SERVER = "key_http_server";
    String DEFAULT_HTTP_SERVER = "http://api.ziwatek.com/v1/operation/config";
    String DEFAULT_VALUE_FOR_STRING = "";
    long DEFAULT_VALUE_FOR_LONG = 0L;
    double DEFAULT_VALUE_FOR_DOUBLE = 0.0D;
    boolean DEFAULT_VALUE_FOR_BOOLEAN = false;
    byte[] DEFAULT_VALUE_FOR_BYTE_ARRAY = new byte[0];
    int VALUE_SOURCE_STATIC = 0;
    int VALUE_SOURCE_DEFAULT = 1;
    int VALUE_SOURCE_REMOTE = 2;
    int LAST_FETCH_STATUS_SUCCESS = -1;
    int LAST_FETCH_STATUS_NO_FETCH_YET = 0;
    int LAST_FETCH_STATUS_FAILURE = 1;
    int LAST_FETCH_STATUS_THROTTLED = 2;

    Task<Void> fetch();
    boolean activateFetched();

    Task<Void> fetch(long cacheExpirationSeconds);
    int getInt(String key);
    int getInt(String key, String nameSpace);
    long getLong(String key);
    long getLong(String key, String nameSpace);
    float getFloat(String key);
    float getFloat(String key, String nameSpace);
    double getDouble(String key);
    double getDouble(String key, String nameSpace);
    String getString(String key);
    String getString(String key, String nameSpace);
    byte[] getBytes(String key);
    byte[] getBytes(String key, String nameSpace);
}
