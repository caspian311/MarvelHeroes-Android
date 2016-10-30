package net.todd.mavelheroes.comics;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.todd.mavelheroes.R;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelComic;

public class MarvelComicViewHolder extends RecyclerView.ViewHolder {
    private final ImageView comicImageView;
    private final TextView comicTitleView;

    public MarvelComicViewHolder(View itemView, final ItemClickedListener itemClickedListener) {
        super(itemView);

        comicImageView = (ImageView)itemView.findViewById(R.id.comic_thumbnail);
        comicTitleView = (TextView)itemView.findViewById(R.id.comic_title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickedListener.itemClicked(getAdapterPosition());
            }
        });
    }

    public void bindView(MarvelComic comic) {
        comicTitleView.setText(comic.getTitle());
        Glide.with(comicImageView.getContext().getApplicationContext()).load(comic.getThumbnailPath()).into(comicImageView);
    }
}
