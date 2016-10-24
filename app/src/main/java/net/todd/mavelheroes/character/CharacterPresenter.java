package net.todd.mavelheroes.character;

import net.todd.mavelheroes.Presenter;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelCharacter;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelCharacterResponse;
import net.todd.mavelheroes.service.MarvelService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterPresenter extends Presenter<CharacterView> {
    private MarvelService marvelService;

    @Inject
    public CharacterPresenter(MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    public void populateCharactersForComic(String comicId) {
        marvelService.getCharacterForComic(comicId).enqueue(new Callback<MarvelCharacterResponse>() {
            @Override
            public void onResponse(Call<MarvelCharacterResponse> call, Response<MarvelCharacterResponse> response) {
                if (response.isSuccessful()) {
                    List<MarvelCharacter> data = response.body().getData().getResults();
                    List<String> ids = new ArrayList<String>();
                    for (MarvelCharacter character : data) {
                        ids.add(character.getId());
                    }
                    getView().displayCharacters(ids);
                } else {
                    try {
                        getView().showError(response.errorBody().string());
                    } catch (Exception e) {
                        getView().showError(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<MarvelCharacterResponse> call, Throwable t) {
                getView().showError(t);
            }
        });
    }
}
