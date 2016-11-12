package net.todd.mavelheroes.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

public class FavoriteCharacter {
    @SerializedName("db_id")
    private Long id;
    private boolean favorite;
    private String imageUrl;

    @SerializedName("description")
    private String bio;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String characterId;
    @SerializedName("thumbnail")
    private CharacterThumbnail thumbnail;

    public FavoriteCharacter() {
    }

    public Long getId() { return id; }

    public String getCharacterId() { return characterId; }

    public String getBio() { return bio; }

    public String getImageUrl() { return imageUrl == null ? thumbnail.getImagePath() : imageUrl; }

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

    public static class Builder {
        private final FavoriteCharacter favoriteCharacter = new FavoriteCharacter();

        public Builder id(long id) {
            favoriteCharacter.id = id;
            return this;
        }

        public Builder characterId(String characterId) {
            favoriteCharacter.characterId = characterId;
            return this;
        }

        public Builder name(String name) {
            favoriteCharacter.name = name;
            return this;
        }

        public Builder description(String description) {
            favoriteCharacter.bio = description;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            favoriteCharacter.imageUrl = imageUrl;
            return this;
        }


        public Builder favorite(boolean favorite) {
            favoriteCharacter.favorite = favorite;
            return this;
        }

        public FavoriteCharacter build() {
            return favoriteCharacter;
        }
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

                        marvelCharacters.add(new Builder()
                                .id(id)
                                .characterId(characterId)
                                .description(bio)
                                .name(name)
                                .imageUrl(imageUrl)
                                .favorite(favorite)
                                .build());
                    }
                }
            }
            return marvelCharacters;
        }
    };
}
