package me.androidtodos.androidtodos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.androidtodos.androidtodos.data.TodoListStore;

public class MainActivity extends BaseActivity {
    private static final int REQUEST_ADD_NEW_TASK = 1;

    @Bind(R.id.todo_list)
    ListView mList;

    @Inject
    TodoListStore store;

    private CursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new TodoCursorAdapter(this, store);

        mList.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // TODO bind something to the list adapter
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            // Start next activity
            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivityForResult(intent, REQUEST_ADD_NEW_TASK);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD_NEW_TASK && resultCode == RESULT_OK) {
            mAdapter.swapCursor(store.listAll());
        }
    }
}
