package com.ah.androidapp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Modifier;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * @author Luong
 */
public class APIService {
    private static APIService instance;
    private IAPIServiceDefinition mService;
    private OkHttpClient mClient;

    public static APIService getInstance() {
        if (instance == null) {
            instance = new APIService();
        }
        return instance;
    }

    private APIService() {
        mClient = new OkHttpClient();
        mClient.setReadTimeout(15, TimeUnit.SECONDS);
        mClient.setConnectTimeout(15, TimeUnit.SECONDS);

        final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }};

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (final java.security.GeneralSecurityException ex) {
            ex.printStackTrace();
        }

        try {
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname,
                                      final SSLSession session) {
                    return true;
                }
            };
            mClient.setHostnameVerifier(hostnameVerifier);
            mClient.setSslSocketFactory(ctx.getSocketFactory());
        } catch (final Exception e) {
            e.printStackTrace();
        }

        RestAdapter restAdapter = null;
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
            gsonBuilder.registerTypeAdapter(Date.class, new DateConverter());
            Gson gson = gsonBuilder.create();

            restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://maps.googleapis.com")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setConverter(new GsonConverter(gson))
                    .setClient(new OkClient(mClient)).build();
            mService = restAdapter.create(IAPIServiceDefinition.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setService(IAPIServiceDefinition service) {
        mService = service;
    }

    public IAPIServiceDefinition getService() {
        return mService;
    }
}
