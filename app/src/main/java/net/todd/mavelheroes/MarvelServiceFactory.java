package net.todd.mavelheroes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarvelServiceFactory {
    private static final String baseUrl = "https://gateway.marvel.com/v1/public/";

    public static MarvelService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(MarvelService.class);
    }
}
