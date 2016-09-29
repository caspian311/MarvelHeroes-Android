package net.todd.mavelheroes;

import dagger.Provides;

public class CharacterIdProvider implements ICharacterIdProvider {
    @Override
    public String getId() {
        return "1009368";
    }
}
