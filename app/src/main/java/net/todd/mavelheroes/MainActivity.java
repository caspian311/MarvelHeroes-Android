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

public class MainActivity extends Activity implements MainView {
    public static final String FETCHING_CHARACTER_DATA = "Fetching character data";

    @Inject
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DaggerApp) getApplication()).getApplicationComponent().plus(new ActivityModule()).inject(this);
        setContentView(R.layout.main_activity);

        mainPresenter.setView(this);
        mainPresenter.populateScreen();
    }

    public void populateImage(String imagePath) {
        ImageView imageView = (ImageView) findViewById(R.id.character_image);
        Glide.with(this).load(imagePath).into(imageView);
    }

    public void populateName(String name) {
        TextView characterNameTextView = (TextView) findViewById(R.id.character_name);
        characterNameTextView.setText(name);
    }

    public void populateBio(String bio) {
        TextView characterNameTextView = (TextView) findViewById(R.id.bio);
        characterNameTextView.setText(bio);
    }

    public void showError(String errorMessage) {
        Toast.makeText(MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG);
        Log.e(FETCHING_CHARACTER_DATA, "Error: " + errorMessage);
    }

    public void showError(Throwable t) {
        Log.e(FETCHING_CHARACTER_DATA, "Error", t);
        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG);
    }
}
