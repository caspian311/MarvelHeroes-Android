package net.todd.mavelheroes;

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

import javax.inject.Inject;


public class CharacterFragment extends Fragment implements CharacterView {
    public static final String FETCHING_CHARACTER_DATA = "Fetching character data";
    private static final String CHARACTER_ID = "character.id";

    @Inject
    CharacterPresenter mainPresenter;

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

        String characterId = getArguments().getString(CHARACTER_ID);

        mainPresenter.setView(this);
        mainPresenter.populateScreen(characterId);
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
}
