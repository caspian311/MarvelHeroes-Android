package net.todd.mavelheroes;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.todd.mavelheroes.db.ObservableDatabase;
import net.todd.mavelheroes.service.ApiLoggerInterceptor;
import net.todd.mavelheroes.service.MarvelApiInterceptor;
import net.todd.mavelheroes.service.MarvelService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

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
    public MarvelService marvelService() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new MarvelApiInterceptor())
                .addInterceptor(new ApiLoggerInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .client(httpClient)
                .build();

        return retrofit.create(MarvelService.class);
    }

    @Provides
    public ObservableDatabase observableDatabase() {
        return new ObservableDatabase(provideApplicationContext());
    }
}
