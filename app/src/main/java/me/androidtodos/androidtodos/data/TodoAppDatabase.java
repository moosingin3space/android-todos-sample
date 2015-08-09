package me.androidtodos.androidtodos.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by nathan on 8/8/15.
 */
public class TodoAppDatabase extends SQLiteOpenHelper {
    public static final String KEY_ID = BaseColumns._ID;
    public static final String KEY_NAME = "name";
    public static final String KEY_DONE = "done";
    public static final int COL_ID = 0;
    public static final int COL_NAME = 1;
    public static final int COL_DONE = 2;
    public static final String TODO_TABLE_NAME = "todos";

    private static final String DATABASE_NAME = "todo_db.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TODO_TABLE_CREATE =
            "CREATE TABLE " + TODO_TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT NOT NULL, " +
                    KEY_DONE + " INTEGER NOT NULL);";

    private interface DbOperation {
        int op(String selection, String[] selectionArgs);
    }

    public TodoAppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TODO_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE_NAME + ";");
        onCreate(db);
    }

    private int doOperation(long id, DbOperation op) {
        String selection = KEY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        return op.op(selection, selectionArgs);
    }

    public long insert(String tableName, ContentValues values) {
        return getWritableDatabase().insert(tableName, null, values);
    }

    public int update(String tableName, long id, ContentValues values) {
        return doOperation(id, (selection, selectionArgs) ->
                getWritableDatabase().update(tableName, values, selection, selectionArgs));
    }

    public int delete(String tableName, long id) {
        return doOperation(id, (selection, selectionArgs) ->
                getWritableDatabase().delete(tableName, selection, selectionArgs));
    }

    public Cursor query(String tableName, String orderedBy) {
        String[] projection = {KEY_ID, KEY_NAME, KEY_DONE};
        return getReadableDatabase().query(tableName, projection, null, null, null, null, orderedBy);
    }
}
