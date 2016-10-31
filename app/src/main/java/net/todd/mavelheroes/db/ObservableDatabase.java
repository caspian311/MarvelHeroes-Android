package net.todd.mavelheroes.db;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import net.todd.mavelheroes.data.FavoriteCharacter;

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
        briteDatabase.insert(FavoriteCharacter.Entity.TABLE_NAME, contentValues);
    }

    public void removeFavorite(String characterId) {
        briteDatabase.delete(FavoriteCharacter.Entity.TABLE_NAME, "character_id = ?", characterId);
    }

    public Observable<FavoriteCharacter> favoriteCharacter(String characterId) {
        return briteDatabase.createQuery(FavoriteCharacter.Entity.TABLE_NAME, "SELECT * FROM " + FavoriteCharacter.Entity.TABLE_NAME + " WHERE character_id = ?", characterId)
                .map(FavoriteCharacter.QUERY_MAP)
                .filter(characters -> characters.size() == 1)
                .map(characters -> characters.get(0));
    }
}
