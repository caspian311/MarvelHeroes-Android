package net.todd.mavelheroes.character;

import net.todd.mavelheroes.PresenterView;

import java.util.List;

public interface CharacterView extends PresenterView {
    void displayCharacters(final List<String> characters);

    void showError(Throwable t);

    void showError(String string);
}
