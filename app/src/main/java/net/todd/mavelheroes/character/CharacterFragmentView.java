package net.todd.mavelheroes.character;

import net.todd.mavelheroes.PresenterView;

public interface CharacterFragmentView extends PresenterView {
    void populateName(String name);
    void populateBio(String bio);
    void populateImage(String imagePath);

    void showError(String string);
    void showError(Throwable t);
}
