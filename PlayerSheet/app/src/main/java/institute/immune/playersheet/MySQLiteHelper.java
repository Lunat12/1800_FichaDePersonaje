package institute.immune.playersheet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    //region DATABASE VARIABLES
    private static final String DATABASE_NAME  = "users.sqlite";
    private static final int DATABASE_VERSION = 7;
    private static final String CHARACTER_TABLE_CREATE = "CREATE TABLE character (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " name TEXT, surname TEXT, lvl INTEGER," +
            " dex INTEGER, inte INTEGER, cons INTEGER, strg INTEGER, pow INTEGER, ate INTEGER, money DECIMAL," +
            " s_dex INTEGER, s_inte INTEGER, s_strg INTEGER, s_ate INTEGER," +
            "max_mad INTEGER, max_life INTEGER, max_magic INTEGER," +
            "mad INTEGER, life INTEGER, magic INTEGER, image BLOB)";
    private static final String INVENTORY_TABLE_CREATE = "CREATE TABLE inventory (_id INTEGER PRIMARY KEY AUTOINCREMENT, character INTEGER, item TEXT)";
    //endregion

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db){
        db.execSQL(CHARACTER_TABLE_CREATE);
        db.execSQL(INVENTORY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS character");
        db.execSQL(CHARACTER_TABLE_CREATE);

        db.execSQL("DROP TABLE IF EXISTS inventory");
        db.execSQL(INVENTORY_TABLE_CREATE);

    }

}
