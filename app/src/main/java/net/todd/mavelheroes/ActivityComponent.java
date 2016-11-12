package net.todd.mavelheroes;

import net.todd.mavelheroes.character.CharacterActivity;
import net.todd.mavelheroes.character.CharacterFragment;
import net.todd.mavelheroes.comics.ComicsActivity;
import net.todd.mavelheroes.favorites.FavoritesActivity;
import net.todd.mavelheroes.favorites.FavoritesFragment;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(CharacterActivity mainActivity);

    void inject(CharacterFragment characterFragment);

    void inject(ComicsActivity comicsActivity);

    void inject(FavoritesActivity favoritesActivity);

    void inject(FavoritesFragment favoritesFragment);
}
