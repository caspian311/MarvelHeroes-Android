package net.todd.mavelheroes.favorites;

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

import javax.inject.Inject;

public class FavoritesFragment extends Fragment implements FavoritesFragmentView {
    private static final String CHARACTER_ID = "character.id";

    @Inject
    FavoritesFragmentPresenter favoritesFragmentPresenter;

    private FloatingActionButton favoriteFab;

    public static FavoritesFragment newInstance(String characterId) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CHARACTER_ID, characterId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.character_fragment, container, false);

        ((DaggerApp) getActivity().getApplication()).getApplicationComponent().plus(new ActivityModule()).inject(this);

        view.findViewById(R.id.character_not_ready_yet).setVisibility(View.GONE);

        favoriteFab = (FloatingActionButton) view.findViewById(R.id.favorite_fab);

        favoritesFragmentPresenter.setView(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        String characterId = getArguments().getString(CHARACTER_ID);
        favoritesFragmentPresenter.showFavorite(characterId);

        favoriteFab.setOnClickListener(v -> favoritesFragmentPresenter.toggleFavorite());
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
        favoritesFragmentPresenter.unsubscribe();
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