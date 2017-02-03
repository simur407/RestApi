package com.rafalzajfert.restapi;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.rafalzajfert.androidlogger.Logger;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Response;

/**
 * Get request
 *
 * @author Rafal Zajfert
 * @see Request
 */
public abstract class GetRequest<E> extends Request<E> {

    protected GetRequest() {
    }

    @Override
    protected Response request() throws IOException {
        HttpUrl url = getUrl();
        RestApi.getLogger().i("[GET]", this.getClass().getSimpleName() + "\n" + url);
        return mHttpClient.newCall(createRequest(url)).execute();
    }

    /**
     * Same as {@link #putUrlParameter(String, Object)}
     */
    @Override
    protected void putParameter(@NonNull String name, Object value) {
        super.putUrlParameter(name, value);
    }

    /**
     * Same as {@link #putUrlParameter(Object)}
     */
    @Override
    protected void putParameter(@Nullable Object value) {
        super.putParameter(value);
    }

    @NonNull
    private okhttp3.Request createRequest(HttpUrl url) {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder()
                .url(url)
                .get();
        for (Map.Entry<String, String> entry : getHeaders().entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
