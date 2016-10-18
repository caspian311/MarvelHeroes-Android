package net.todd.mavelheroes;

import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelComic;

import java.util.List;

public interface ComicsView extends PresenterView {
    void setComics(List<MarvelComic> comics);

    void goToComic(String comicId);

    void showError(Throwable throwable);

    void showError(String string);
}
