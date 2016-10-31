package net.todd.mavelheroes.character;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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

    public static CharacterFragment newInstance(String characterId) {
        CharacterFragment fragment = new CharacterFragment();
        Bundle args = new Bundle();
        args.putString(CHARACTER_ID, characterId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((DaggerApp) getActivity().getApplication()).getApplicationComponent().plus(new ActivityModule()).inject(this);

        mainPresenter.setView(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        String characterId = getArguments().getString(CHARACTER_ID);

        if (subscription != null) {
            subscription.unsubscribe();
        }
        subscription = new CompositeSubscription();
        subscription.add(observableDatabase.favoriteCharacter(characterId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateFavorite));

        mainPresenter.populateScreen(characterId);
    }

    private void updateFavorite(FavoriteCharacter favoriteCharacter) {
        // TODO finish up UI
    }

    @Override
    public void onPause() {
        super.onPause();

        subscription.unsubscribe();
        subscription = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.character_fragment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void populateImage(String imagePath) {
        ImageView imageView = (ImageView) getView().findViewById(R.id.character_image);
        Glide.with(this).load(imagePath).into(imageView);
    }

    public void populateName(String name) {
        TextView characterNameTextView = (TextView) getView().findViewById(R.id.character_name);
        characterNameTextView.setText(name);
    }

    public void populateBio(String bio) {
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
