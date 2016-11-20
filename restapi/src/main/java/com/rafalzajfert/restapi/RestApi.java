package com.rafalzajfert.restapi;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.text.TextUtils;

import com.rafalzajfert.restapi.exceptions.DefaultErrorResponse;
import com.rafalzajfert.restapi.serialization.Deserializer;
import com.rafalzajfert.restapi.serialization.ErrorDeserializer;
import com.rafalzajfert.restapi.serialization.JacksonSerializer;
import com.rafalzajfert.restapi.serialization.JsonDeserializer;
import com.rafalzajfert.restapi.serialization.JsonErrorDeserializer;
import com.rafalzajfert.restapi.serialization.Serializer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * TODO: Documentation
 *
 * @author Rafał Zajfert
 */
@SuppressWarnings({"unused","WeakerAccess"})
public class RestApi {

    private static Config sConfiguration;

    /**
     * Set configuration of the connection to the api. This method must be called before using any connection with the Api
     * @param configuration object with rest configuration
     */
    public static void setConfiguration(Config configuration){
        sConfiguration = configuration;
    }

    static Config getConfiguration() {
        return sConfiguration;
    }

    static boolean isConfigured() {
        return sConfiguration != null;
    }

    public static class Config {

        public static final String HTTP = "http";
        public static final String HTTPS = "https";

        private int mTimeout = 60 * 1000;
        @Config.Scheme
        private String mScheme = HTTP;
        private String mHost;
        private int mPort = -1;
        @Nullable
        private BasicAuthorization mBasicAuthorization;
        @Nullable
        private InitialRequirements mInitialRequirements;

        @NonNull
        private ErrorDeserializer mErrorDeserializer = new JsonErrorDeserializer();

        @NonNull
        private Deserializer mDeserializer = new JsonDeserializer();

        @NonNull
        private Serializer mSerializer = new JacksonSerializer();

        private RestAuthorizationService mUserService;

        private Class<? extends DefaultErrorResponse> mErrorResponseClass;

        private RestAuthorizationService mRestAuthorizationService;

        /**
         * Timeout for the connections.
         * A value of 0 means no timeout, otherwise values must be between 1 and Integer.MAX_VALUE milliseconds.<br/>
         * default: 1min
         */
        public int getTimeout() {
            return mTimeout;
        }

        /**
         * Sets the default connect timeout for the connections.
         * A value of 0 means no timeout, otherwise values must be between 1 and Integer.MAX_VALUE milliseconds.<br/>
         * default: 1min
         */
        public Config setTimeout(int timeout) {
            mTimeout = timeout;
            return this;
        }

        /**
         * Url scheme - http or https
         * <p>
         * default: http
         */
        public String getScheme() {
            return mScheme;
        }

        /**
         * Sets the default url scheme - http or https
         * <p>
         * default: http
         */
        public Config setScheme(@NonNull @Config.Scheme String scheme) {
            mScheme = scheme;
            return this;
        }

        /**
         * Rest Api host - either a regular hostname, International Domain Name, IPv4 address, or IPv6
         * address.
         */
        public String getHost() {
            return mHost;
        }

        /**
         * Rest Api host - either a regular hostname, International Domain Name, IPv4 address, or IPv6
         * address.
         */
        public Config setHost(@NonNull String host) {
            mHost = host;
            return this;
        }

        /**
         * Port for communication with rest api
         * </p>
         * default: 80 if {@code scheme.equals("http")}, 443 if {@code scheme.equals("https")} and -1
         * otherwise.
         */
        public int getPort() {
            return mPort;
        }

        /**
         * Port for communication with rest api
         * </p>
         * default: 80 if {@code scheme.equals("http")}, 443 if {@code scheme.equals("https")} and -1
         * otherwise.
         */
        public Config setPort(int port) {
            mPort = port;
            return this;
        }

        /**
         * Basic authorization for all requests
         */
        @Nullable
        public BasicAuthorization getBasicAuthorization() {
            return mBasicAuthorization;
        }

        /**
         * Sets basic authorization for all requests
         */
        public Config setAuthorization(@NonNull String user, @NonNull String password) {
            if (TextUtils.isEmpty(user) || TextUtils.isEmpty(password)) {
                mBasicAuthorization = null;
                return this;
            }
            mBasicAuthorization = new BasicAuthorization(user, password);
            return this;
        }

        /**
         * Initial restrictions for all requests
         */
        @Nullable
        public InitialRequirements getInitialRequirements() {
            return mInitialRequirements;
        }

        /**
         * Initial restrictions for all requests
         */
        public Config setInitialRequirements(@NonNull InitialRequirements initialRequirements) {
            mInitialRequirements = initialRequirements;
            return this;
        }

        /**
         * Response deserializer
         * </p>
         * default: {@link com.rafalzajfert.restapi.serialization.JsonDeserializer JsonDeserializer}
         */
        @NonNull
        public Deserializer getDeserializer() {
            return mDeserializer;
        }


        /**
         * Response deserializer
         * </p>
         * default: {@link com.rafalzajfert.restapi.serialization.JsonDeserializer JsonDeserializer}
         */
        public void setDeserializer(@NonNull Deserializer deserializer) {
            mDeserializer = deserializer;
        }


        /**
         * Request parameters serializer
         * </p>
         * default: {@link com.rafalzajfert.restapi.serialization.JacksonSerializer JacksonSerializer}
         */
        @NonNull
        public Serializer getSerializer() {
            return mSerializer;
        }


        /**
         * Request parameters serializer
         * </p>
         * default: {@link com.rafalzajfert.restapi.serialization.JacksonSerializer JacksonSerializer}
         */
        public void setSerializer(@NonNull Serializer serializer) {
            mSerializer = serializer;
        }

        /**
         * Authorization service
         * </p>
         * default: null
         */
        @Nullable
        public RestAuthorizationService getRestAuthorizationService() {
            return mRestAuthorizationService;
        }

        /**
         * Authorization service
         * </p>
         * default: null
         */
        public void setRestAuthorizationService(RestAuthorizationService authorizationService) {
            mRestAuthorizationService = authorizationService;
        }

        /**
         * Error response deserializer
         * </p>
         * default: {@link com.rafalzajfert.restapi.serialization.JsonErrorDeserializer JsonErrorDeserializer}
         */
        @NonNull
        public ErrorDeserializer getErrorDeserializer() {
            return mErrorDeserializer;
        }

        /**
         * Error response deserializer
         * </p>
         * default: {@link com.rafalzajfert.restapi.serialization.JsonErrorDeserializer JsonErrorDeserializer}
         */
        public void setErrorDeserializer(@NonNull ErrorDeserializer errorDeserializer) {
            mErrorDeserializer = errorDeserializer;
        }

        /**
         * @hide
         */
        @StringDef({HTTP, HTTPS})
        @Retention(RetentionPolicy.SOURCE)
        public @interface Scheme {
        }

    }
}
