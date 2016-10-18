package net.todd.mavelheroes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

public class CharacterActivity extends AppCompatActivity implements CharacterView {
    private static final String FETCHING_CHARACTER_FOR_COMIC_DATA = "Fetching characters ids";
    @Inject
    CharacterPresenter characterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DaggerApp) getApplication()).getApplicationComponent().plus(new ActivityModule()).inject(this);

        setContentView(R.layout.main_activity);

        characterPresenter.setView(this);

        String comicId = getIntent().getStringExtra(ComicsActivity.COMIC_ID);

        characterPresenter.populateCharacersForComic(comicId);
    }

    public void displayCharacters(final List<String> characters) {
        ViewPager pager = (ViewPager) findViewById(R.id.character_pager);

        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return CharacterFragment.newInstance(characters.get(position));
            }

            @Override
            public int getCount() {
                return characters.size();
            }
        });
    }


    public void showError(String errorMessage) {
        Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_LONG);
        Log.e(FETCHING_CHARACTER_FOR_COMIC_DATA, "Error: " + errorMessage);
    }

    public void showError(Throwable t) {
        Log.e(FETCHING_CHARACTER_FOR_COMIC_DATA, "Error", t);
        Toast.makeText(this, "Error: " + t.getMessage(), Toast.LENGTH_LONG);
    }
}
