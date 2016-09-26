package net.todd.mavelheroes;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListHerosActivity extends Activity {
    public static final String FETCHING_CHARACTER_DATA = "Fetching character data";
    private static final String NAME_PLACEHOLDER_TEXT = "Search by hero name";
    private HeroListAdapter adapter;
    private String name;
    private static final int limit = 10;
    private int offset;
    private EditText searchHeroText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hero_list);

        ListView listView = (ListView) findViewById(R.id.list_of_heroes);
        adapter = new HeroListAdapter(this, new ArrayList<MarvelCharacter>());
        listView.setAdapter(adapter);
        View header = getLayoutInflater().inflate(R.layout.list_header, null);
        searchHeroText = (EditText)header.findViewById(R.id.search_hero);
        searchHeroText.setText(NAME_PLACEHOLDER_TEXT);
        searchHeroText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && searchHeroText.getText().toString().equals(NAME_PLACEHOLDER_TEXT)) {
                    searchHeroText.setText("");
                } else if (!hasFocus && searchHeroText.getText().toString().length() == 0) {
                    searchHeroText.setText(NAME_PLACEHOLDER_TEXT);
                }
            }
        });
        Button searchButton = (Button) header.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offset = 0;
                refreshData();
            }
        });

        View footer = getLayoutInflater().inflate(R.layout.list_footer, null);
        final Button backButton = (Button)footer.findViewById(R.id.list_back);
        backButton.setEnabled(false);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offset -= limit;
                backButton.setEnabled(offset != 0);
                refreshData();
            }
        });
        Button forwardButton = (Button)footer.findViewById(R.id.list_forward);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offset += limit;
                backButton.setEnabled(offset != 0);
                refreshData();
            }
        });

        listView.addHeaderView(header);
        listView.addFooterView(footer);

        refreshData();
    }

    private void refreshData() {
        fetchData().enqueue(new Callback<MarvelCharacterResponse>() {
            @Override
            public void onResponse(Call<MarvelCharacterResponse> call, Response<MarvelCharacterResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        MarvelCharacter[] characters = response.body().data.results;
                        adapter.clear();
                        adapter.addAll(characters);
                    } else {
                        String errorMessage = response.errorBody().string();
                        Toast.makeText(ListHerosActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG);
                        Log.e(FETCHING_CHARACTER_DATA, "Error: " + errorMessage);
                    }
                } catch (Exception e){
                    Log.e(FETCHING_CHARACTER_DATA, "Error", e);
                    Toast.makeText(ListHerosActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<MarvelCharacterResponse> call, Throwable t) {
                Log.e(FETCHING_CHARACTER_DATA, "Error", t);
                Toast.makeText(ListHerosActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    private String getName(){
        String name = (searchHeroText != null) ? searchHeroText.getText().toString() : null;
        return (name == null || name.equals(NAME_PLACEHOLDER_TEXT)) ? null : name;
    }

    private Call<MarvelCharacterResponse> fetchData() {
        return MarvelServiceFactory.getService().listCharacters(getName(), limit, offset);
    }
}
