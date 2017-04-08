package com.example.dawid.paragonix;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dawid on 08/04/2017.
 */

public class BazaDanych {
    private static final String DEBUG_TAG = "SqLiteTodoManager";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";
    private static final String DB_PARAGONY_TABLE = "paragony";
    private static final String DB_POZYCJE_TABLE = "pozycje";

    // Tabela paragony
    public static final String KEY_ID = "id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;
    public static final String KEY_SKLEP = "sklep";
    public static final String SKLEP_OPTIONS = "TEXT NOT NULL";
    public static final int SKLEP_COLUMN = 1;
    public static final String KEY_SUMA = "suma";
    public static final String SUMA_OPTIONS = "REAL NOT NULL";
    public static final int SUMA_COLUMN = 2;

    // Tabela pozycje
    public static final String KEY_ID2 = "id";
    public static final String ID2_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID2_COLUMN = 0;
    public static final String KEY_ID_PARAGONU = "id_paragonu";
    public static final String ID_PARAGONU_OPTIONS = "INTEGER NOT NULL";
    public static final int ID_PARAGONU_COLUMN = 1;
    public static final String KEY_NAZWA = "nazwa";
    public static final String NAZWA_OPTIONS = "TEXT NOT NULL";
    public static final int NAZWA_COLUMN = 2;
    public static final String KEY_CENA = "cena";
    public static final String CENA_OPTIONS = "REAL NOT NULL";
    public static final int CENA_COLUMN = 3;

    // Tworzenie tabeli paragony
    private static final String DB_CREATE_PARAGONY_TABLE =
            "CREATE TABLE " + DB_PARAGONY_TABLE + "( " + KEY_ID + " " + ID_OPTIONS + ", " +
                    KEY_SKLEP + " " + SKLEP_OPTIONS + ", " + KEY_SUMA + " " + SUMA_OPTIONS + ");";

    // Tworzenie tabelu pozycje
    private static final String DB_CREATE_POZYCJE_TABLE =
            "CREATE TABLE " + DB_POZYCJE_TABLE + "( " + KEY_ID2 + " " + ID2_OPTIONS + ", " +
                    KEY_ID_PARAGONU + " " + ID_PARAGONU_OPTIONS + ", " + KEY_NAZWA + " " + NAZWA_OPTIONS
                    + ", " + KEY_CENA + " " + CENA_OPTIONS + ");";

    private static final String DROP_PARAGONY_TABLE = "DROP TABLE IF EXISTS " + DB_PARAGONY_TABLE;
    private static final String DROP_POZYCJE_TABLE = "DROP TABLE IF EXISTS " + DB_POZYCJE_TABLE;

    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_PARAGONY_TABLE);
            db.execSQL(DB_CREATE_POZYCJE_TABLE);

            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Table " + DB_PARAGONY_TABLE + " ver." + DB_VERSION + " created");
            Log.d(DEBUG_TAG, "Table " + DB_POZYCJE_TABLE + " ver." + DB_VERSION + " created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_PARAGONY_TABLE);
            db.execSQL(DROP_POZYCJE_TABLE);

            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table " + DB_PARAGONY_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "Table " + DB_POZYCJE_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");

            onCreate(db);
        }
    }

    public BazaDanych(Context context) {
        this.context = context;
    }

    public BazaDanych open() {
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }
}

