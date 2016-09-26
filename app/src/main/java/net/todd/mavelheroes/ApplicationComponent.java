package net.todd.mavelheroes;

import dagger.Component;

@Component(modules = {ApplicationModule.class, ViewModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
}
