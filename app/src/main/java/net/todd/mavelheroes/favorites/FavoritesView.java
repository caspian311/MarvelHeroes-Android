package net.todd.mavelheroes.favorites;

import net.todd.mavelheroes.PresenterView;
import net.todd.mavelheroes.data.FavoriteCharacter;

import java.util.List;

public interface FavoritesView extends PresenterView {
    void displayFavorites(List<FavoriteCharacter> favorites);
}
