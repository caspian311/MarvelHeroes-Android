package net.todd.mavelheroes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.todd.mavelheroes.data.FavoriteCharacter;

public class MarvelDbOpenHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "MarvelComics.db";
    public static final int VERSION = 1;

    public MarvelDbOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL.CREATE_FAVORITES_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private static class SQL {
        private static final String CREATE_FAVORITES_SQL;
        static {
            CREATE_FAVORITES_SQL = new StringBuilder()
                    .append("CREATE TABLE ").append(FavoriteCharacter.Entity.TABLE_NAME).append(" (")
                    .append(FavoriteCharacter.Entity._ID).append(" INTEGER PRIMARY KEY, ")
                    .append(FavoriteCharacter.Entity.COLUMN_CHARACTER_ID).append(" TEXT, ")
                    .append(FavoriteCharacter.Entity.COLUMN_IMAGE_URL).append(" TEXT, ")
                    .append(FavoriteCharacter.Entity.COLUMN_NAME).append(" TEXT, ")
                    .append(FavoriteCharacter.Entity.COLUMN_BIO).append(" TEXT, ")
                    .append(FavoriteCharacter.Entity.COLUMN_FAVORITE).append(" INTEGER ")
                    .append(")")
                    .toString();
        }
    }
}
