package net.todd.mavelheroes.comics;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.todd.mavelheroes.R;
import net.todd.mavelheroes.data.MarvelComic;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action0;
import rx.functions.Action1;

public class ComicsViewHolderAdapter extends RecyclerView.Adapter<MarvelViewHolder> {
    private static final int FAVORITE_TYPE = 0;
    private static final int COMIC_TYPE = 1;
    private final List<MarvelComic> comics = new ArrayList<>();
    private final Action1<Integer> itemClickedListener;
    private final Action0 favoritesClickListener;

    public ComicsViewHolderAdapter(Action1<Integer> itemClickedListener, Action0 favoritesClickListener) {
        this.itemClickedListener = itemClickedListener;
        this.favoritesClickListener = favoritesClickListener;
    }

    public void addAll(List<MarvelComic> allComics) {
        comics.clear();
        comics.addAll(allComics);
        notifyDataSetChanged();
    }

    @Override
    public MarvelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == COMIC_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_row, parent, false);
            return new MarvelComicViewHolder(view, itemClickedListener);
        } else  {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row, parent, false);
            return new FavoriteCharactersViewHolder(view, favoritesClickListener);
        }
    }

    @Override
    public void onBindViewHolder(MarvelViewHolder holder, int position) {
        holder.bindView(comics.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? FAVORITE_TYPE : COMIC_TYPE;
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    public MarvelComic getComic(int position) {
        return comics.get(position);
    }
}
