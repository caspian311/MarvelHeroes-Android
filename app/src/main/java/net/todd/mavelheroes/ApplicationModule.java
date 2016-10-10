package net.todd.mavelheroes;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {
    private DaggerApp daggerApp;

    public ApplicationModule(DaggerApp daggerApp) {
        this.daggerApp = daggerApp;
    }

    @Provides
    public DaggerApp provideApplication() {
        return daggerApp;
    }

    @Provides
    @Named("application_context")
    public Context provideApplicationContext() {
        return daggerApp.getApplicationContext();
    }

    private static final String baseUrl = "https://gateway.marvel.com/v1/public/";

    @Provides
    public ICharacterIdProvider characterIdProvider() {
        return new CharacterIdProvider();
    }

    @Provides
    public MarvelService marvelService() {
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
