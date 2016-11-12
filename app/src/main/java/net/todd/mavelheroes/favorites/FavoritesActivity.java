package net.todd.mavelheroes.favorites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import net.todd.mavelheroes.ActivityModule;
import net.todd.mavelheroes.DaggerApp;
import net.todd.mavelheroes.R;
import net.todd.mavelheroes.data.FavoriteCharacter;

import java.util.List;

import javax.inject.Inject;

public class FavoritesActivity extends AppCompatActivity implements FavoritesView {
    @Inject
    FavoritesPresenter favoritesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DaggerApp) getApplication()).getApplicationComponent().plus(new ActivityModule()).inject(this);

        setContentView(R.layout.character_activity);

        favoritesPresenter.setView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        favoritesPresenter.populateCharacters();
    }

    @Override
    public void displayFavorites(final List<FavoriteCharacter> characters) {
        ViewPager pager = (ViewPager) findViewById(R.id.character_pager);

        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FavoritesFragment.newInstance(characters.get(position).getCharacterId());
            }

            @Override
            public int getCount() {
                return characters.size();
            }
        });
    }
}
