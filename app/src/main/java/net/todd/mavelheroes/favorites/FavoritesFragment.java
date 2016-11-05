package net.todd.mavelheroes.favorites;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.todd.mavelheroes.ActivityModule;
import net.todd.mavelheroes.DaggerApp;
import net.todd.mavelheroes.R;
import net.todd.mavelheroes.data.FavoriteCharacter;
import net.todd.mavelheroes.db.ObservableDatabase;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;


public class FavoritesFragment extends Fragment implements FavoritesFragmentView {
    private FavoriteCharacter character;

    @Inject
    FavoritesFragmentPresenter favoritesFragmentPresenter;
    @Inject
    ObservableDatabase observableDatabase;

    private CompositeSubscription subscription;
    private FloatingActionButton favoriteFab;

    public static FavoritesFragment newInstance(FavoriteCharacter character) {
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.character = character;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.character_fragment, container, false);

        ((DaggerApp) getActivity().getApplication()).getApplicationComponent().plus(new ActivityModule()).inject(this);

        favoriteFab = (FloatingActionButton) view.findViewById(R.id.favorite_fab);

        favoritesFragmentPresenter.setView(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (subscription != null) {
            subscription.unsubscribe();
        }
        subscription = new CompositeSubscription();
        subscription.add(observableDatabase.favoriteCharacter(character.getCharacterId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoritesFragmentPresenter::updateFavorite));

        favoriteFab.setOnClickListener(v -> favoriteToggle());

        favoritesFragmentPresenter.populateScreen(character);
    }

    public void favoriteToggle() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteCharacter.Entity.COLUMN_CHARACTER_ID, this.character.getCharacterId());
        contentValues.put(FavoriteCharacter.Entity.COLUMN_NAME, this.character.getName());
        contentValues.put(FavoriteCharacter.Entity.COLUMN_IMAGE_URL, this.character.getImageUrl());
        contentValues.put(FavoriteCharacter.Entity.COLUMN_BIO, this.character.getBio());
        contentValues.put(FavoriteCharacter.Entity.COLUMN_FAVORITE, !this.character.isFavorite());

        observableDatabase.addFavorite(contentValues);
    }

    @Override
    public void unsetFavorite() {
        favoriteFab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_star_rate_black_18dp));
    }

    @Override
    public void setFavorite() {
        favoriteFab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_stars_black_18dp));
    }

    @Override
    public void onPause() {
        super.onPause();

        favoriteFab.setOnClickListener(null);

        subscription.unsubscribe();
        subscription = null;
    }

    @Override
    public void populateImage(String imagePath) {
        ImageView imageView = (ImageView) getView().findViewById(R.id.character_image);
        Glide.with(this).load(imagePath).into(imageView);
    }

    @Override
    public void populateName(String name) {
        TextView characterNameTextView = (TextView) getView().findViewById(R.id.character_name);
        characterNameTextView.setText(name);
    }

    @Override
    public void populateBio(String bio) {
        TextView characterNameTextView = (TextView) getView().findViewById(R.id.bio);
        characterNameTextView.setText(bio);
    }

    @Override
    public void hideWaiting() {
        getView().findViewById(R.id.character_ready_now).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.character_not_ready_yet).setVisibility(View.GONE);
    }
}