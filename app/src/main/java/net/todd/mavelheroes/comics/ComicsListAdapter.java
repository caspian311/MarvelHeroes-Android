package net.todd.mavelheroes.comics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.todd.mavelheroes.R;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelComic;

import java.util.List;

public class ComicsListAdapter extends ArrayAdapter<MarvelComic> {
    private final LayoutInflater inflater;

    public ComicsListAdapter(Context context, int resource, List<MarvelComic> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MarvelComic comic = getItem(position);

        View view = inflater.inflate(R.layout.comic_row, null);
        TextView comicTitleView = (TextView) view.findViewById(R.id.comic_title);
        ImageView comicImageView = (ImageView) view.findViewById(R.id.comic_thumbnail);

        comicTitleView.setText(comic.getTitle());
        Glide.with(getContext()).load(comic.getThumbnailPath()).into(comicImageView);

        return view;
    }
}
