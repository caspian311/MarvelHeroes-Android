package net.todd.mavelheroes.favorites;

import net.todd.mavelheroes.Presenter;
import net.todd.mavelheroes.data.FavoriteCharacter;

import javax.inject.Inject;

public class FavoritesFragmentPresenter extends Presenter<FavoritesFragmentView> {
    @Inject
    public FavoritesFragmentPresenter() {}

    public void populateScreen(FavoriteCharacter character) {
        getView().hideWaiting();

        getView().populateName(character.getName());
        getView().populateImage(character.getImageUrl());
        getView().populateBio(character.getBio());
    }

    public void updateFavorite(FavoriteCharacter favoriteCharacter) {
        if (favoriteCharacter.isFavorite()) {
            getView().setFavorite();
        } else {
            getView().unsetFavorite();
        }

    }
}
