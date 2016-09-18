package net.todd.mavelheroes;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarvelServiceFactory {
    private static final String baseUrl = "https://gateway.marvel.com/v1/public/";

    public static MarvelService getService() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new MarvelApiInterceptor())
                .addInterceptor(new ApiLoggerInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        return retrofit.create(MarvelService.class);
    }
}
