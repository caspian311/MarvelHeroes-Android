package net.todd.mavelheroes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
    public static final String FETCHING_CHARACTER_DATA = "Fetching character data";

    @Inject
    ICharacterIdProvider characterIdProvider;
    @Inject
    MarvelService marvelService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DaggerApp) getApplication()).getApplicationComponent().plus(new ActivityModule()).inject(this);
        setContentView(R.layout.main_activity);

        populateScreen();
    }

    private void populateScreen() {
        fetchData().enqueue(new Callback<MarvelCharacterResponse>() {
            @Override
            public void onResponse(Call<MarvelCharacterResponse> call, Response<MarvelCharacterResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        MarvelCharacter character = response.body().data.results[0];
                        bindData(character);
                    } else {
                        String errorMessage = response.errorBody().string();
                        Toast.makeText(MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG);
                        Log.e(FETCHING_CHARACTER_DATA, "Error: " + errorMessage);
                    }
                } catch (Exception e){
                    Log.e(FETCHING_CHARACTER_DATA, "Error", e);
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<MarvelCharacterResponse> call, Throwable t) {
                Log.e(FETCHING_CHARACTER_DATA, "Error", t);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    private Call<MarvelCharacterResponse> fetchData() {
        return marvelService.getCharacter(characterIdProvider.getId());
    }

    private void bindData(MarvelCharacter character) {
        TextView characterNameTextView = (TextView)findViewById(R.id.character_name);
        characterNameTextView.setText(character.getName());

        TextView bioTextView = (TextView)findViewById(R.id.bio);
        bioTextView.setText(character.getBio());

        ImageView imageView = (ImageView) findViewById(R.id.character_image);
        Glide.with(this).load(character.getImagePath()).into(imageView);
    }
}
