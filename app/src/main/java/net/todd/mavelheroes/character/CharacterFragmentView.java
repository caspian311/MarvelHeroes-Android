package net.todd.mavelheroes.character;

import android.view.View;

import net.todd.mavelheroes.PresenterView;
import net.todd.mavelheroes.R;

public interface CharacterFragmentView extends PresenterView {
    void populateName(String name);
    void populateBio(String bio);
    void populateImage(String imagePath);

    void showError(String string);
    void showError(Throwable t);

    void showWaiting();
    void hideWaiting();
}
