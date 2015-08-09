package me.androidtodos.androidtodos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import me.androidtodos.androidtodos.data.TodoAppDatabase;
import me.androidtodos.androidtodos.data.TodoListStore;
import me.androidtodos.androidtodos.data.TodoTask;

/**
 * Created by nathan on 8/8/15.
 */
public class TodoCursorAdapter extends CursorAdapter {
    private LayoutInflater mInflater;
    private TodoListStore mStore;

    public TodoCursorAdapter(Context context, TodoListStore store) {
        super(context, store.listAll(), 0);
        mInflater = LayoutInflater.from(context);
        mStore = store;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.todo_item, parent, false);
        long id = cursor.getLong(TodoAppDatabase.COL_ID);
        ViewHolder holder = new ViewHolder(mStore, id, view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        TodoTask task = TodoListStore.deserializeCursor(cursor);
        holder.checkbox.setChecked(task.done);
        holder.textView.setText(task.text);
    }

    static class ViewHolder {
        @Bind(R.id.checkbox)
        CheckBox checkbox;

        @Bind(R.id.todo_text)
        TextView textView;

        private long mId;
        private TodoListStore mStore;

        public ViewHolder(TodoListStore store, long id, View view) {
            mId = id;
            mStore = store;
            ButterKnife.bind(this, view);
        }

        @OnCheckedChanged(R.id.checkbox)
        public void changeDoneStatus() {
            ContentValues vals = new ContentValues();
            vals.put(TodoAppDatabase.KEY_DONE, checkbox.isChecked());
            mStore.db.update(TodoAppDatabase.TODO_TABLE_NAME, mId, vals);
        }
    }
}
