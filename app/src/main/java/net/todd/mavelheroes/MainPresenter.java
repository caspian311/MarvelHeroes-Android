package net.todd.mavelheroes;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter extends Presenter<CharacterView> {
    private final MarvelService marvelService;

    @Inject
    public MainPresenter(MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    public void populateScreen(String characterId) {
        fetchData(characterId).enqueue(new Callback<MarvelCharacterResponse>() {
            @Override
            public void onResponse(Call<MarvelCharacterResponse> call, Response<MarvelCharacterResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        MarvelCharacter character = response.body().getData().getResults()[0];

                        getView().populateName(character.getName());
                        getView().populateBio(character.getBio());
                        getView().populateImage(character.getImagePath());
                    } else {
                        getView().showError(response.errorBody().string());
                    }
                } catch (Exception e){
                    getView().showError(e);
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
