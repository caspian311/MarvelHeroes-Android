package net.todd.mavelheroes;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModule {
    @Provides
    TeaPot provideStringProvider() {
        return new TeaPot();
    }
}
