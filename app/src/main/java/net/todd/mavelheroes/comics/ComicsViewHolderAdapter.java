package net.todd.mavelheroes.comics;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.todd.mavelheroes.R;
import net.todd.mavelheroes.data.MarvelComic;

import java.util.ArrayList;
import java.util.List;

public class ComicsViewHolderAdapter extends RecyclerView.Adapter<MarvelComicViewHolder> {
    private final List<MarvelComic> comics = new ArrayList<>();
    private final ItemClickedListener itemClickedListener;

    public ComicsViewHolderAdapter(ItemClickedListener itemClickedListener) {
        this.itemClickedListener = itemClickedListener;
    }

    public void addAll(List<MarvelComic> allComics) {
        comics.clear();
        comics.addAll(allComics);
        notifyDataSetChanged();
    }

    @Override
    public MarvelComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_row, parent, false);
        return new MarvelComicViewHolder(view, itemClickedListener);
    }

    @Override
    public void onBindViewHolder(MarvelComicViewHolder holder, int position) {
        holder.bindView(comics.get(position));
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    public MarvelComic getComic(int position) {
        return comics.get(position);
    }
}
