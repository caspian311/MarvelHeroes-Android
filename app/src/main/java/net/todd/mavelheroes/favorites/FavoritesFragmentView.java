package net.todd.mavelheroes.favorites;

import net.todd.mavelheroes.PresenterView;

public interface FavoritesFragmentView extends PresenterView {
    void populateImage(String imagePath);

    void populateName(String characterName);

    void populateBio(String bio);

    void hideWaiting();

    void unsetFavorite();

    void setFavorite();
}
