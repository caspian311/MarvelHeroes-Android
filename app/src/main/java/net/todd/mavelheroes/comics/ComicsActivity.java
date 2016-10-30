package net.todd.mavelheroes.comics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.todd.mavelheroes.ActivityModule;
import net.todd.mavelheroes.DaggerApp;
import net.todd.mavelheroes.R;
import net.todd.mavelheroes.character.CharacterActivity;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelComic;

import java.util.List;

import javax.inject.Inject;

public class ComicsActivity extends Activity implements ComicsView {
    public static final String COMIC_ID = "comic.id";
    private static final String FETCHING_COMIC_DATA = "Fetching comic data";

    @Inject
    ComicsPresenter comicsPresenter;

    private ComicsViewHolderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DaggerApp) getApplication()).getApplicationComponent().plus(new ActivityModule()).inject(this);

        setContentView(R.layout.comics_activity);

        comicsPresenter.setView(this);

        RecyclerView comicsListView = (RecyclerView) findViewById(R.id.comics_list_view);
        comicsListView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ComicsViewHolderAdapter(new ItemClickedListener() {
            @Override
            public void itemClicked(int position) {
                String selectedComicId = adapter.getComic(position).getId();
                comicsPresenter.selectComic(selectedComicId);
            }
        });
        comicsListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        comicsPresenter.populateScreen();
    }

    @Override
    public void showEmptyView() {
        findViewById(R.id.empty_comics_view).setVisibility(View.VISIBLE);
        findViewById(R.id.comics_list_view).setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideEmptyView() {
        findViewById(R.id.empty_comics_view).setVisibility(View.INVISIBLE);
        findViewById(R.id.comics_list_view).setVisibility(View.VISIBLE);
    }

    @Override
    public void setComics(List<MarvelComic> comics) {
        adapter.addAll(comics);
    }

    @Override
    public void goToComic(String comicId) {
        Intent intent = new Intent(ComicsActivity.this, CharacterActivity.class);
        intent.putExtra(COMIC_ID, comicId);
        startActivity(intent);
    }

    @Override
    public void showError(Throwable t) {
        Log.e(FETCHING_COMIC_DATA, "Error", t);
        Toast.makeText(this, "Error: " + t.getMessage(), Toast.LENGTH_LONG);
    }

    @Override
    public void showError(String errorMessage) {
        Log.e(FETCHING_COMIC_DATA, "Error: " + errorMessage);
        Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_LONG);
    }
}
