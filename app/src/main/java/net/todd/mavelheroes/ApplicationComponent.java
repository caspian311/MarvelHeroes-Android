package net.todd.mavelheroes;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    ActivityComponent plus(ActivityModule activityModule);
}
