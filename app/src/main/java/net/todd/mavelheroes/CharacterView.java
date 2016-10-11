package net.todd.mavelheroes;

public interface CharacterView extends PresenterView {
    void populateName(String name);
    void populateBio(String bio);
    void populateImage(String imagePath);

    void showError(String string);
    void showError(Throwable t);
}
