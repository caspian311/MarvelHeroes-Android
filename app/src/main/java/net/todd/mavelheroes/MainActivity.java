package net.todd.mavelheroes;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        System.out.println("Fetching character from API...");
        fetchData().enqueue(new Callback<MarvelCharacterResponse>() {
            @Override
            public void onResponse(Call<MarvelCharacterResponse> call, Response<MarvelCharacterResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        MarvelCharacter character = response.body().data.results[0];
                        MainActivity.this.bindData(character);
                    } else {
                        String errorMessage = response.errorBody().string();
                        Toast.makeText(MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG);
                        Log.e(MainActivity.class.toString(), "Error: " + errorMessage);
                    }
                } catch (Exception e){
                    Log.e(MainActivity.class.toString(), "Error", e);
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<MarvelCharacterResponse> call, Throwable t) {
                Log.e(MainActivity.class.toString(), "Error", t);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    private void bindData(MarvelCharacter character) {
        TextView characterNameTextView = (TextView)findViewById(R.id.character_name);
        characterNameTextView.setText(character.getName());

        TextView bioTextView = (TextView)findViewById(R.id.bio);
        bioTextView.setText(character.getBio());
    }

    private Call<MarvelCharacterResponse> fetchData() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://gateway.marvel.com/v1/public/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            MarvelService service = retrofit.create(MarvelService.class);

            MarvelAuth auth = new MarvelAuth();
            return service.getCharacter("1009368", auth.getPublicKey(), auth.getTimestamp(), auth.getHash());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
