package net.todd.mavelheroes;

import net.todd.mavelheroes.character.CharacterActivity;
import net.todd.mavelheroes.character.CharacterFragment;
import net.todd.mavelheroes.comics.ComicsActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(CharacterActivity mainActivity);

    void inject(CharacterFragment characterFragment);

    void inject(ComicsActivity comicsActivity);
}
