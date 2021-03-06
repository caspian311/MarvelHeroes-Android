package net.todd.mavelheroes.character;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import net.todd.mavelheroes.ActivityModule;
import net.todd.mavelheroes.DaggerApp;
import net.todd.mavelheroes.R;
import net.todd.mavelheroes.comics.ComicsActivity;

import java.util.List;

import javax.inject.Inject;

public class CharacterActivity extends AppCompatActivity implements CharacterView {
    public static final String FETCHING_CHARACTER_FOR_COMIC_DATA = "Fetching characters ids";

    @Inject
    CharacterPresenter characterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DaggerApp) getApplication()).getApplicationComponent().plus(new ActivityModule()).inject(this);

        setContentView(R.layout.character_activity);

        characterPresenter.setView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String comicId = getIntent().getStringExtra(ComicsActivity.COMIC_ID);
        characterPresenter.populateCharactersForComic(comicId);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void displayCharacters(final List<String> characterIds) {
        ViewPager pager = (ViewPager) findViewById(R.id.character_pager);

        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return CharacterFragment.newInstance(characterIds.get(position));
            }

            @Override
            public int getCount() {
                return characterIds.size();
            }
        });
    }

    public void showError(Throwable t) {
        Log.e(FETCHING_CHARACTER_FOR_COMIC_DATA, "Error", t);
        Toast.makeText(this, "Error: " + t.getMessage(), Toast.LENGTH_LONG);
    }
}
