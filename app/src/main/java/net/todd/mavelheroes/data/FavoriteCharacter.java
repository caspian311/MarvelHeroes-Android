package net.todd.mavelheroes.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

public class FavoriteCharacter {
    private final Long id;
    private final String characterId;
    private final String name;
    private final String bio;
    private final boolean favorite;
    private final String imageUrl;

    public FavoriteCharacter(Long id, String characterId, String name, String imageUrl, String bio, boolean favorite) {
        this.id = id;
        this.characterId = characterId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.bio = bio;
        this.favorite = favorite;
    }

    public Long getId() { return id; }

    public String getCharacterId() { return characterId; }

    public String getBio() { return bio; }

    public String getImageUrl() { return imageUrl; }

    public String getName() { return name; }

    public boolean isFavorite() { return favorite; }

    public static class Entity implements BaseColumns {
        public static final String TABLE_NAME = "favorite_characters";
        public static final String COLUMN_CHARACTER_ID = "character_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE_URL = "image_url";
        public static final String COLUMN_BIO = "bio";
        public static final String COLUMN_FAVORITE = "favorite";
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Entity._ID, getId());
        contentValues.put(Entity.COLUMN_CHARACTER_ID, getCharacterId());
        contentValues.put(Entity.COLUMN_NAME, getName());
        contentValues.put(Entity.COLUMN_BIO, getBio());
        contentValues.put(Entity.COLUMN_IMAGE_URL, getImageUrl());
        return contentValues;
    }

    public static final Func1<? super SqlBrite.Query, ? extends List<FavoriteCharacter>> QUERY_MAP = new Func1<SqlBrite.Query, List<FavoriteCharacter>>() {
        @Override
        public List<FavoriteCharacter> call(final SqlBrite.Query query) {
            final List<FavoriteCharacter> marvelCharacters = new ArrayList<>();
            try (final Cursor cursor = query.run()) {
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        Long id = cursor.getLong(cursor.getColumnIndexOrThrow(Entity._ID));

                        String characterId = cursor.getString(cursor.getColumnIndexOrThrow(Entity.COLUMN_CHARACTER_ID));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(Entity.COLUMN_NAME));
                        String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(Entity.COLUMN_IMAGE_URL));
                        String bio = cursor.getString(cursor.getColumnIndexOrThrow(Entity.COLUMN_BIO));
                        Boolean favorite = cursor.getInt(cursor.getColumnIndexOrThrow(Entity.COLUMN_FAVORITE)) == 1;

                        marvelCharacters.add(new FavoriteCharacter(id, characterId, name, imageUrl, bio, favorite));
                    }
                }
            }
            return marvelCharacters;
        }
    };
}
