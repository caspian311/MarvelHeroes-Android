package net.todd.mavelheroes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardActivity extends Activity {
    public static final String FETCHING_CHARACTER_DATA = "Fetching character data";
    public static final String CHARACTER_ID = "character-id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);

        String characterId = getIntent().getStringExtra(CHARACTER_ID);
        populateScreen(characterId);
    }

    private void populateScreen(String characterId) {
        fetchData(characterId).enqueue(new Callback<MarvelCharacterResponse>() {
            @Override
            public void onResponse(Call<MarvelCharacterResponse> call, Response<MarvelCharacterResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        MarvelCharacter character = response.body().data.results[0];
                        bindData(character);
                    } else {
                        String errorMessage = response.errorBody().string();
                        Toast.makeText(CardActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG);
                        Log.e(FETCHING_CHARACTER_DATA, "Error: " + errorMessage);
                    }
                } catch (Exception e){
                    Log.e(FETCHING_CHARACTER_DATA, "Error", e);
                    Toast.makeText(CardActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<MarvelCharacterResponse> call, Throwable t) {
                Log.e(FETCHING_CHARACTER_DATA, "Error", t);
                Toast.makeText(CardActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    private Call<MarvelCharacterResponse> fetchData(String characterId) {
        return MarvelServiceFactory.getService().getCharacter(characterId);
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