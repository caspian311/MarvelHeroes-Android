package net.todd.mavelheroes.character;

import net.todd.mavelheroes.Presenter;
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

public class CharacterFragmentPresenter extends Presenter<CharacterFragmentView> {
    private final MarvelService marvelService;

    @Inject
    public CharacterFragmentPresenter(MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    public void populateScreen(String characterId) {
        getView().showWaiting();

        fetchData(characterId);
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

        MarvelCharacter character = response.getData().getResults().get(0);

        getView().populateName(character.getName());
        getView().populateBio(character.getDescription());
        getView().populateImage(character.getImagePath());
    }
}
