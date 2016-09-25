package net.todd.mavelheroes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HeroListAdapter extends ArrayAdapter<MarvelCharacter> {
    public HeroListAdapter(Context context, ArrayList<MarvelCharacter> characters) {
        super(context, 0, characters);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = layoutInflator.inflate(R.layout.hero, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.hero_name);
        textView.setText(getItem(position).getName());

        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(getContext(), CardActivity.class);
                intent.putExtra(CardActivity.CHARACTER_ID, getItem(position).getId());
                getContext().startActivity(intent);
                return true;
            }
        });

        return convertView;
    }
}
