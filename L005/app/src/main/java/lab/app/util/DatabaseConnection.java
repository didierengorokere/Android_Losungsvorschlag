/*
 * Copyright © 2017-2022
 * Merz Information and Communication Technology AG.
 * All rights reserved.
 *
 * C015-DE
 * Entwicklung von Mobile Apps mit Android: Programmiersprache Java
 */

package lab.app.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    private static final int databaseVersion = 1;
    private static final String databaseName = "data";
    private static final String userTableName = "user";

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public DatabaseConnection(Context context) {
        databaseHelper = new DatabaseHelper(context, databaseName, null, databaseVersion);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void insertUserEntry(String name) {
        var values = new ContentValues();
        values.put("name", name);
        database.insert(userTableName, null, values);
    }

    public List<String> selectUserEntries() {
        var cursor = database.query(userTableName, null, null, null, null, null, null);
        var userEntries = new ArrayList<String>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                userEntries.add(cursor.getString(1));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return userEntries;
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            var query = String.format("CREATE TABLE %s (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);", userTableName);
            database.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            var query = String.format("DROP TABLE IF EXISTS %s;", userTableName);
            database.execSQL(query);
            onCreate(database);
        }
    }
}
