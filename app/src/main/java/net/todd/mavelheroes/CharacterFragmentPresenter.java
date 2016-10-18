package net.todd.mavelheroes;

import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelCharacter;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelCharacterResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterFragmentPresenter extends Presenter<CharacterFragmentView> {
    private final MarvelService marvelService;

    @Inject
    public CharacterFragmentPresenter(MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    public void populateScreen(String characterId) {
        fetchData(characterId).enqueue(new Callback<MarvelCharacterResponse>() {
            @Override
            public void onResponse(Call<MarvelCharacterResponse> call, Response<MarvelCharacterResponse> response) {
                    if (response.isSuccessful()) {
                        MarvelCharacter character = response.body().getData().getResults().get(0);

                        getView().populateName(character.getName());
                        getView().populateBio(character.getDescription());
                        getView().populateImage(character.getImagePath());
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

    private Call<MarvelCharacterResponse> fetchData(String characterId) {
        return marvelService.getCharacter(characterId);
    }
}
