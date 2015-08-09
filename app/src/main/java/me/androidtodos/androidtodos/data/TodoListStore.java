package me.androidtodos.androidtodos.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by nathan on 8/8/15.
 */
public class TodoListStore {
    public TodoAppDatabase db;
    private Context mCtx;
    public TodoListStore(Context context) {
        db = new TodoAppDatabase(context);
        mCtx = context;
    }

    public Cursor listAll() {
        return db.query(TodoAppDatabase.TODO_TABLE_NAME, TodoAppDatabase.KEY_NAME);
    }

    public static TodoTask deserializeCursor(Cursor c) {
        return new TodoTask(
                c.getString(TodoAppDatabase.COL_NAME),
                (c.getInt(TodoAppDatabase.COL_DONE) == 1));
    }

    public SimpleCursorAdapter getSimpleListAdapter() {
        Cursor c = db.query(TodoAppDatabase.TODO_TABLE_NAME, TodoAppDatabase.KEY_NAME);
        String[] from = new String[]{TodoAppDatabase.KEY_NAME};
        int[] to = new int[]{android.R.id.text1};

        return new SimpleCursorAdapter(mCtx,
                android.R.layout.simple_list_item_activated_1, c, from, to, 0);
    }

    public void insertTask(TodoTask task) {
        ContentValues vals = new ContentValues();
        vals.put(TodoAppDatabase.KEY_NAME, task.text);
        int done = task.done ? 1 : 0;
        vals.put(TodoAppDatabase.KEY_DONE, done);

        db.insert(TodoAppDatabase.TODO_TABLE_NAME, vals);
    }
}

