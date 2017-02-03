package com.rafalzajfert.restapi;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Put request
 *
 * @author Rafal Zajfert
 * @see Request
 */
@SuppressWarnings("unused")
public abstract class PutRequest<T> extends Request<T> {

    protected PutRequest() {
    }

    @Override
    protected Response request() throws IOException {
        HttpUrl url = getUrl();
        RestApi.getLogger().i("[PUT]", this.getClass().getSimpleName() + "\n" + url);
        okhttp3.Request request = createRequest(url, getRequestBody());
        return mHttpClient.newCall(request).execute();
    }

    @NonNull
    private okhttp3.Request createRequest(HttpUrl url, RequestBody body) {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE, getMediaType().toString());
        for (Map.Entry<String, String> entry : getHeaders().entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        return builder.put(body).build();
    }
}
