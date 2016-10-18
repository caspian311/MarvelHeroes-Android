package net.todd.mavelheroes.comics;

import net.todd.mavelheroes.Presenter;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelComic;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelComicsResponse;
import net.todd.mavelheroes.service.MarvelService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicsPresenter extends Presenter<ComicsView> {
    private MarvelService marvelService;

    @Inject
    public ComicsPresenter(MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    public void populateScreen() {
        marvelService.getComics("1009368").enqueue(new Callback<MarvelComicsResponse>() {
            @Override
            public void onResponse(Call<MarvelComicsResponse> call, Response<MarvelComicsResponse> response) {
                if (response.isSuccessful()) {
                    List<MarvelComic> comics = response.body().getData().getResults();
                    getView().setComics(comics);

                } else {
                    try {
                        getView().showError(response.errorBody().string());
                    } catch (Exception e) {
                        getView().showError(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<MarvelComicsResponse> call, Throwable t) {
                getView().showError(t);
            }
        });
    }

    public void selectComic(String id) {
        getView().goToComic(id);
    }
}
