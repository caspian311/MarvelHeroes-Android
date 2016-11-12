package net.todd.mavelheroes.comics;

import android.view.View;

import rx.functions.Action0;

public class FavoriteCharactersViewHolder extends MarvelViewHolder {
    public FavoriteCharactersViewHolder(View view, Action0 favoritesClickListener) {
        super(view);
        view.setOnClickListener(v -> favoritesClickListener.call());
    }
}
