package net.todd.mavelheroes.favorites;

import net.todd.mavelheroes.Presenter;
import net.todd.mavelheroes.data.FavoriteCharacter;
import net.todd.mavelheroes.db.ObservableDatabase;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class FavoritesFragmentPresenter extends Presenter<FavoritesFragmentView> {
    private final ObservableDatabase observableDatabase;

    private CompositeSubscription subscription;
    private FavoriteCharacter favoriteCharacter;

    @Inject
    public FavoritesFragmentPresenter(ObservableDatabase observableDatabase) {
        this.observableDatabase = observableDatabase;
    }

    public void showFavorite(String characterId) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        subscription = new CompositeSubscription();

        subscription.add(observableDatabase.favoriteCharacter(characterId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showFavorite));
    }

    private void showFavorite(FavoriteCharacter character) {
        this.favoriteCharacter = character;
        getView().populateName(character.getName());
        getView().populateImage(character.getImageUrl());
        getView().populateBio(character.getBio());

        if (character.isFavorite()) {
            getView().setFavorite();
        } else {
            getView().unsetFavorite();
        }
    }

    public void unsubscribe() {
        subscription.unsubscribe();
        subscription = null;
    }

    public void toggleFavorite() {
        observableDatabase.toggleFavorite(favoriteCharacter.toContentValues());
    }
}
