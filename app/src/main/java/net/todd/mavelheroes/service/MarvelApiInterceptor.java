package net.todd.mavelheroes.service;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MarvelApiInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl originalUrl = chain.request().url();

        MarvelAuth auth = new MarvelAuth();

        HttpUrl newUrl = originalUrl.newBuilder()
                .addQueryParameter("ts", auth.getTimestamp())
                .addQueryParameter("apikey", auth.getPublicKey())
                .addQueryParameter("hash", auth.getHash())
                .addQueryParameter("limit", "100")
                .build();

        Request newRequest = new Request.Builder().url(newUrl).build();
        return chain.proceed(newRequest);
    }
}
