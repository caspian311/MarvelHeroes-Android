package net.todd.mavelheroes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import net.todd.mavelheroes.data.FavoriteCharacter;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

public class ObservableDatabase {
    private final BriteDatabase briteDatabase;

    public ObservableDatabase(Context context) {
        SqlBrite instance = new SqlBrite.Builder().logger(message -> Log.d(ObservableDatabase.class.getSimpleName(), message)).build();
        briteDatabase = instance.wrapDatabaseHelper(new MarvelDbOpenHelper(context), Schedulers.io());
        briteDatabase.setLoggingEnabled(true);
    }

    public void addFavorite(ContentValues contentValues) {
        String characterId = contentValues.getAsString(FavoriteCharacter.Entity.COLUMN_CHARACTER_ID);
        Cursor c = briteDatabase.query("SELECT * FROM " + FavoriteCharacter.Entity.TABLE_NAME + " WHERE character_id = ?", characterId);
        if (c.getCount() > 0) {
            briteDatabase.update(FavoriteCharacter.Entity.TABLE_NAME, contentValues, "character_id = ?", characterId);
        } else {
            briteDatabase.insert(FavoriteCharacter.Entity.TABLE_NAME, contentValues);
        }
    }

    public Observable<FavoriteCharacter> favoriteCharacter(String characterId) {
        return briteDatabase.createQuery(FavoriteCharacter.Entity.TABLE_NAME, "SELECT * FROM " + FavoriteCharacter.Entity.TABLE_NAME + " WHERE character_id = ?", characterId)
                .map(FavoriteCharacter.QUERY_MAP)
                .filter(characters -> characters.size() == 1)
                .map(characters -> characters.get(0));
    }

    public Observable<List<FavoriteCharacter>> allFavorites() {
        return briteDatabase.createQuery(FavoriteCharacter.Entity.TABLE_NAME, "SELECT * FROM " + FavoriteCharacter.Entity.TABLE_NAME + " WHERE " + FavoriteCharacter.Entity.COLUMN_FAVORITE + " = 1")
                .map(FavoriteCharacter.QUERY_MAP);
    }
}
