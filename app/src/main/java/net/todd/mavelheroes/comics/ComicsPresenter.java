package net.todd.mavelheroes.comics;

import net.todd.mavelheroes.Presenter;
import net.todd.mavelheroes.data.MarvelComic;
import net.todd.mavelheroes.data.MarvelComicsResponse;
import net.todd.mavelheroes.service.MarvelService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ComicsPresenter extends Presenter<ComicsView> {
    private MarvelService marvelService;

    @Inject
    public ComicsPresenter(MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    public void populateScreen() {
        getView().showEmptyView();

        fetchData();
    }

    /*
     1009368 -> IronMan
     1009718 -> Wolverine
     1009718 -> X-Man
     1010733 -> Star-Lord
     */
    private void fetchData() {
        marvelService.getComics("1009368,1009718,1009718,1010733")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleData, this::handleError);
    }

    private void handleError(Throwable throwable) {
        getView().showError(throwable);
    }

    private void handleData(MarvelComicsResponse response) {
        List<MarvelComic> comics = response.getData().getResults();
        getView().hideEmptyView();
        getView().setComics(comics);
    }

    public void selectComic(String id) {
        getView().goToComic(id);
    }
}
