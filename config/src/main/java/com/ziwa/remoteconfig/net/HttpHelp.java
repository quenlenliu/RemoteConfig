package com.ziwa.remoteconfig.net;

import com.ziwa.remoteconfig.IFuelBaseRemoteConfig;
import com.ziwa.remoteconfig.impl.FuelBaseRemoteConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpHelp {

    private OkHttpClient mClient = null;

    private static HttpHelp mHelp = new HttpHelp();

    private static long CONN_TIMEOUT = 5000;
    private static long READ_TIMEOUT = 5000;
    private IFuelBaseRemoteConfig mRemoteConfig;

    public static HttpHelp getInstance() {
        return mHelp;
    }

    private HttpHelp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONN_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
        mClient = builder.build();
        mRemoteConfig = FuelBaseRemoteConfig.getInstance();
    }

    public void request(String url, Params urlParams, Params bodyParams, final Task task) {
        url = url == null ? getServer() : url;
        if (urlParams != null) {
            url = url + '?' + urlParams.toUrlParams();
        }
        RequestBody requestBody = null;
        if (bodyParams != null) {
            FormBody.Builder bodyBuild = new FormBody.Builder();
            Iterator<String> iterator = bodyParams.getKeys();
            if (iterator != null) {
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = bodyParams.getParam(key);
                    bodyBuild.add(key, value);
                }
                requestBody = bodyBuild.build();
            }
        }

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (requestBody != null) {
            builder.post(requestBody);
        }
        Request request = builder.build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                task.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                task.onResponse(response);
            }
        });
    }

    private String getServer() {
        String url = mRemoteConfig.getString(IFuelBaseRemoteConfig.KEY_HTTP_SERVER);;
        return url != null && !"".equals(url) ? url : IFuelBaseRemoteConfig.DEFAULT_HTTP_SERVER;
    }

    public static class Params {
        public HashMap<String, String> mParams;
        public static final char URL_PARAM_SPLIT = '&';
        public Params() {
            mParams = new HashMap<>();
        }
        public void addParam(String key, String value) {
            mParams.put(key, value);
        }

        public String getParam(String key) {
            return mParams.get(key);
        }

        public String removeParam(String key) {
            return mParams.remove(key);
        }

        public Iterator<String> getKeys() {
            return mParams.keySet().iterator();
        }


        public String toUrlParams() {
            StringBuilder sb = new StringBuilder();
            Iterator<String> iterator = getKeys();
            if (iterator != null) {
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = getParam(key);
                    if (value != null) {
                        sb.append(key).append('=').append(value).append(URL_PARAM_SPLIT);
                    }
                }
                sb.delete(sb.length() - 1, sb.length());
            }
            return sb.toString();
        }
    }
}
