package com.ziwa.remoteconfig.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class Task<T> {
    private List<OnCompleteListener<T>> mCompleteListener;

    public interface OnCompleteListener<T> {
        void onComplete(Task<T> task);
    }

    protected boolean isSuccessful = false;
    protected boolean isComplete = false;

    public Task () {
        mCompleteListener = new ArrayList<>();
        isComplete = false;
        isSuccessful = false;
    }

    public void addOnCompleteListener(OnCompleteListener<T> listener) {
        if (!mCompleteListener.contains(listener)) {
            mCompleteListener.add(listener);
        }
    }

    /**
     * Called when the request could not be executed due to cancellation, a connectivity problem or
     * timeout. Because networks can fail during an exchange, it is possible that the remote server
     * accepted the request before the failure.
     */
    public final void onFailure(IOException e) {
        this.isSuccessful = false;
        this.isComplete = true;
        for (OnCompleteListener listener : mCompleteListener) {
            listener.onComplete(this);
        }
    }

    /**
     * Called when the HTTP response was successfully returned by the remote server. The callback may
     * proceed to read the response body with {@link Response#body}. The response is still live until
     * its response body is {@linkplain ResponseBody closed}. The recipient of the callback may
     * consume the response body on another thread.
     *
     * <p>Note that transport-layer success (receiving a HTTP response code, headers and body) does
     * not necessarily indicate application-layer success: {@code response} may still indicate an
     * unhappy HTTP response code like 404 or 500.
     */
    public final void onResponse(Response response) throws IOException {
        this.isSuccessful = true;
        this.isComplete = true;
        for (OnCompleteListener listener : mCompleteListener) {
            listener.onComplete(this);
        }
    }

    public boolean isComplete() {
        return isComplete;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void execute() {

    }
}
