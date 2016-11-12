package net.todd.mavelheroes.favorites;

import net.todd.mavelheroes.Presenter;
import net.todd.mavelheroes.db.ObservableDatabase;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FavoritesPresenter extends Presenter<FavoritesView> {
    private final ObservableDatabase observableDatabase;

    @Inject
    public FavoritesPresenter(ObservableDatabase observableDatabase) {
        this.observableDatabase = observableDatabase;
    }

    public void populateCharacters() {
        observableDatabase.allFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getView()::displayFavorites);
    }
}
