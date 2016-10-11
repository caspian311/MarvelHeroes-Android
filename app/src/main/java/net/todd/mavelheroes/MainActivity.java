package net.todd.mavelheroes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        displayCharacters(CharacterFactory.getCharacters());
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
