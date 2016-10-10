package net.todd.mavelheroes;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter extends Presenter<MainView> {
    private final ICharacterIdProvider characterIdProvider;
    private final MarvelService marvelService;

    @Inject
    public MainPresenter(ICharacterIdProvider characterIdProvider, MarvelService marvelService) {
        this.characterIdProvider = characterIdProvider;
        this.marvelService = marvelService;
    }

    public void populateScreen() {
        fetchData().enqueue(new Callback<MarvelCharacterResponse>() {
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

    private Call<MarvelCharacterResponse> fetchData() {
        return marvelService.getCharacter(characterIdProvider.getId());
    }
}
