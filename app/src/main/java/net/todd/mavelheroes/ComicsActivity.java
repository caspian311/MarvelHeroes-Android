package net.todd.mavelheroes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelCharacterResponse;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelComic;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelComicsData;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelComicsResponse;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicsActivity extends Activity {
    private static final String COMIC_ID = "comic.id";
    @Inject
    MarvelService marvelService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DaggerApp) getApplication()).getApplicationComponent().plus(new ActivityModule()).inject(this);

        setContentView(R.layout.comics_activity);

        final ListView comicsListView = (ListView) findViewById(R.id.comics_list_view);
        comicsListView.setEmptyView(findViewById(R.id.empty_comics_view));

        marvelService.getComics().enqueue(new Callback<MarvelComicsResponse>() {
            @Override
            public void onResponse(Call<MarvelComicsResponse> call, Response<MarvelComicsResponse> response) {
                try {
                    List<MarvelComic> comics = response.body().getData().getResults();

                    final ComicsListAdapter adapter = new ComicsListAdapter(ComicsActivity.this, R.layout.comic_row, comics);
                    comicsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            MarvelComic selectedComic = adapter.getItem(position);
                            Intent intent = new Intent(ComicsActivity.this, CharacterActivity.class);
                            intent.putExtra(COMIC_ID, selectedComic.getId());
                            startActivity(intent);
                        }
                    });
                    comicsListView.setAdapter(adapter);
                } catch(Exception e) {
                    // handle error
                }
            }

            @Override
            public void onFailure(Call<MarvelComicsResponse> call, Throwable t) {
                // handle error
            }
        });
    }
}
