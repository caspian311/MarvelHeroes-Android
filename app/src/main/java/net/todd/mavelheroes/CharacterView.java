package net.todd.mavelheroes;

import java.util.List;

public interface CharacterView extends PresenterView {
    void displayCharacters(final List<String> characters);

    void showError(Throwable t);

    void showError(String string);
}
