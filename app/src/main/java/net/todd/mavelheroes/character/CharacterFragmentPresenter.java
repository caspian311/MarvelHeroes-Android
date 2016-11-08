package net.todd.mavelheroes.character;

import android.content.ContentValues;

import net.todd.mavelheroes.Presenter;
import net.todd.mavelheroes.data.FavoriteCharacter;
import net.todd.mavelheroes.data.MarvelCharacter;
import net.todd.mavelheroes.data.MarvelCharacterResponse;
import net.todd.mavelheroes.db.ObservableDatabase;
import net.todd.mavelheroes.service.MarvelService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class CharacterFragmentPresenter extends Presenter<CharacterFragmentView> {
    private final MarvelService marvelService;
    private final ObservableDatabase observableDatabase;

    private CompositeSubscription subscription;
    private MarvelCharacter character;
    private boolean isFavorite;

    @Inject
    public CharacterFragmentPresenter(MarvelService marvelService, ObservableDatabase observableDatabase) {
        this.marvelService = marvelService;
        this.observableDatabase = observableDatabase;
    }

    public void populateScreen(String characterId) {
        getView().showWaiting();

        fetchData(characterId);

        if (subscription != null) {
            subscription.unsubscribe();
        }
        subscription = new CompositeSubscription();
        subscription.add(observableDatabase.favoriteCharacter(characterId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateFavorite));
    }

    private void updateFavorite(FavoriteCharacter favoriteCharacter) {
        isFavorite = favoriteCharacter.isFavorite();
        getView().updateFavorite(favoriteCharacter.isFavorite());
    }

    private void fetchData(String characterId) {
        marvelService.getCharacter(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleData, this::handleError);
    }

    private void handleError(Throwable throwable) {
        getView().showError(throwable);
    }

    private void handleData(MarvelCharacterResponse response) {
        getView().hideWaiting();

        character = response.getData().getResults().get(0);

        getView().populateName(character.getName());
        getView().populateBio(character.getDescription());
        getView().populateImage(character.getImagePath());
    }

    public void unsubscribe() {
        subscription.unsubscribe();
        subscription = null;
    }

    public void favoriteToggle() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteCharacter.Entity.COLUMN_CHARACTER_ID, character.getId());
        contentValues.put(FavoriteCharacter.Entity.COLUMN_NAME, character.getName());
        contentValues.put(FavoriteCharacter.Entity.COLUMN_IMAGE_URL, character.getImagePath());
        contentValues.put(FavoriteCharacter.Entity.COLUMN_BIO, character.getDescription());
        contentValues.put(FavoriteCharacter.Entity.COLUMN_FAVORITE, !isFavorite);

        observableDatabase.toggleFavorite(contentValues);
    }
}
