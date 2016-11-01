package net.todd.mavelheroes.character;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.todd.mavelheroes.ActivityModule;
import net.todd.mavelheroes.DaggerApp;
import net.todd.mavelheroes.R;
import net.todd.mavelheroes.data.FavoriteCharacter;
import net.todd.mavelheroes.db.ObservableDatabase;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;


public class CharacterFragment extends Fragment implements CharacterFragmentView {
    public static final String FETCHING_CHARACTER_DATA = "Fetching character data";
    private static final String CHARACTER_ID = "character.id";

    @Inject
    CharacterFragmentPresenter mainPresenter;
    @Inject
    ObservableDatabase observableDatabase;

    private CompositeSubscription subscription;
    private FloatingActionButton favoriteFab;

    private String characterName;
    private String characterBio;
    private String characterImageUrl;
    private String characterId;
    private View.OnClickListener fabClickListener;

    public static CharacterFragment newInstance(String characterId) {
        CharacterFragment fragment = new CharacterFragment();
        Bundle args = new Bundle();
        args.putString(CHARACTER_ID, characterId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        this.characterId = getArguments().getString(CHARACTER_ID);

        if (subscription != null) {
            subscription.unsubscribe();
        }
        subscription = new CompositeSubscription();
        subscription.add(observableDatabase.favoriteCharacter(characterId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateFavorite));

        favoriteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteToggle();
            }
        });

        mainPresenter.populateScreen(characterId);
    }

    public void favoriteToggle() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteCharacter.Entity.COLUMN_CHARACTER_ID, this.characterId);
        contentValues.put(FavoriteCharacter.Entity.COLUMN_NAME, this.characterName);
        contentValues.put(FavoriteCharacter.Entity.COLUMN_IMAGE_URL, this.characterImageUrl);
        contentValues.put(FavoriteCharacter.Entity.COLUMN_BIO, this.characterBio);

        observableDatabase.addFavorite(contentValues);
    }

    private void updateFavorite(FavoriteCharacter favoriteCharacter) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.character_fragment, container, false);

        ((DaggerApp) getActivity().getApplication()).getApplicationComponent().plus(new ActivityModule()).inject(this);

        favoriteFab = (FloatingActionButton)view.findViewById(R.id.favorite_fab);

        mainPresenter.setView(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void populateImage(String imagePath) {
        this.characterImageUrl = imagePath;
        ImageView imageView = (ImageView) getView().findViewById(R.id.character_image);
        Glide.with(this).load(imagePath).into(imageView);
    }

    public void populateName(String name) {
        this.characterName = name;
        TextView characterNameTextView = (TextView) getView().findViewById(R.id.character_name);
        characterNameTextView.setText(name);
    }

    public void populateBio(String bio) {
        this.characterBio = bio;
        TextView characterNameTextView = (TextView) getView().findViewById(R.id.bio);
        characterNameTextView.setText(bio);
    }

    public void showError(String errorMessage) {
        Toast.makeText(this.getActivity(), "Error: " + errorMessage, Toast.LENGTH_LONG);
        Log.e(FETCHING_CHARACTER_DATA, "Error: " + errorMessage);
    }

    public void showError(Throwable t) {
        Log.e(FETCHING_CHARACTER_DATA, "Error", t);
        Toast.makeText(this.getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG);
    }

    @Override
    public void showWaiting() {
        getView().findViewById(R.id.character_ready_now).setVisibility(View.GONE);
        getView().findViewById(R.id.character_not_ready_yet).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWaiting() {
        getView().findViewById(R.id.character_ready_now).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.character_not_ready_yet).setVisibility(View.GONE);
    }
}
