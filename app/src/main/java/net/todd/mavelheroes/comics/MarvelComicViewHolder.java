package net.todd.mavelheroes.comics;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.todd.mavelheroes.R;
import net.todd.mavelheroes.data.MarvelComic;

import rx.functions.Action1;

public class MarvelComicViewHolder extends MarvelViewHolder {
    private final ImageView comicImageView;
    private final TextView comicTitleView;

    public MarvelComicViewHolder(View itemView, final Action1<Integer> itemClickedListener) {
        super(itemView);

        comicImageView = (ImageView)itemView.findViewById(R.id.comic_thumbnail);
        comicTitleView = (TextView)itemView.findViewById(R.id.comic_title);

        itemView.setOnClickListener(v -> itemClickedListener.call(getAdapterPosition()));
    }

    @Override
    public void bindView(MarvelComic comic) {
        comicTitleView.setText(comic.getTitle());
        Glide.with(comicImageView.getContext().getApplicationContext()).load(comic.getThumbnail()).into(comicImageView);
    }
}
