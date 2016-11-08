package net.todd.mavelheroes.character;

import android.view.View;

import net.todd.mavelheroes.PresenterView;
import net.todd.mavelheroes.R;
import net.todd.mavelheroes.data.FavoriteCharacter;

public interface CharacterFragmentView extends PresenterView {
    void populateName(String name);
    void populateBio(String bio);
    void populateImage(String imagePath);

    void showError(Throwable t);

    void updateFavorite(boolean isFavoriteCharacter);

    void showWaiting();
    void hideWaiting();

}
