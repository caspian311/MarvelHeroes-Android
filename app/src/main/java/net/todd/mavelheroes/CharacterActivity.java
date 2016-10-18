package net.todd.mavelheroes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelCharacter;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelCharacterData;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelCharacterResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterActivity extends AppCompatActivity {
    @Inject
    MarvelService marvelService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DaggerApp) getApplication()).getApplicationComponent().plus(new ActivityModule()).inject(this);

        setContentView(R.layout.main_activity);

        String comicId = getIntent().getStringExtra(ComicsActivity.COMIC_ID);
        marvelService.getCharacterForComic(comicId).enqueue(new Callback<MarvelCharacterResponse>() {
            @Override
            public void onResponse(Call<MarvelCharacterResponse> call, Response<MarvelCharacterResponse> response) {
                List<MarvelCharacter> data = response.body().getData().getResults();
                List<String> ids = new ArrayList<String>();
                for (MarvelCharacter character : data) {
                    ids.add(character.getId());
                }
                displayCharacters(ids);
            }

            @Override
            public void onFailure(Call<MarvelCharacterResponse> call, Throwable t) {

            }
        });
    }

    private void displayCharacters(final List<String> characters) {
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
}
