package net.todd.mavelheroes.character;

import net.todd.mavelheroes.Presenter;
import net.todd.mavelheroes.data.MarvelCharacter;
import net.todd.mavelheroes.data.MarvelCharacterResponse;
import net.todd.mavelheroes.service.MarvelService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CharacterPresenter extends Presenter<CharacterView> {
    private MarvelService marvelService;

    @Inject
    public CharacterPresenter(MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    public void populateCharactersForComic(String comicId) {
        marvelService.getCharacterForComic(comicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleData, this::handleError);
    }

    private void handleData(MarvelCharacterResponse response) {
        List<MarvelCharacter> data = response.getData().getResults();
        List<String> ids = new ArrayList<String>();
        for (MarvelCharacter character : data) {
            ids.add(character.getId());
        }
        getView().displayCharacters(ids);
    }

    private void handleError(Throwable throwable) {
        getView().showError(throwable);
    }
}
