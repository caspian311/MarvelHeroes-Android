package net.todd.mavelheroes;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(CharacterActivity mainActivity);

    void inject(CharacterFragment characterFragment);

    void inject(ComicsActivity comicsActivity);
}
