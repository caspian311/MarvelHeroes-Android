package net.todd.mavelheroes;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);

    void inject(CharacterFragment characterFragment);
}
