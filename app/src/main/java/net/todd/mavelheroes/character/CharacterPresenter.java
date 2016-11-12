package net.todd.mavelheroes.character;

import net.todd.mavelheroes.Presenter;
import net.todd.mavelheroes.data.FavoriteCharacter;
import net.todd.mavelheroes.data.MarvelCharacterResponse;
import net.todd.mavelheroes.service.MarvelService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
        List<FavoriteCharacter> data = response.getData().getResults();
        List<String> ids = new ArrayList<String>();
        for (FavoriteCharacter character : data) {
            ids.add(character.getCharacterId());
        }
        getView().displayCharacters(ids);
    }

    private void handleError(Throwable throwable) {
        getView().showError(throwable);
    }
}
