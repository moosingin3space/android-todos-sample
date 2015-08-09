package me.androidtodos.androidtodos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.androidtodos.androidtodos.data.TodoListStore;
import me.androidtodos.androidtodos.data.TodoTask;
import rx.Observable;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;

public class AddTaskActivity extends BaseActivity {

    @Bind(R.id.button)
    Button button;

    @Bind(R.id.editText)
    EditText text;

    @Inject
    TodoListStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Observable<Boolean> isTextValid = WidgetObservable.text(text)
                .map(OnTextChangeEvent::text)
                .map(t -> t.length() > 0);

        isTextValid.distinctUntilChanged()
                .subscribe(button::setEnabled);
    }

    @OnClick(R.id.button)
    public void saveNewTask() {
        TodoTask task = new TodoTask(text.getText().toString());
        store.insertTask(task);

        // Return to previous activity
        setResult(Activity.RESULT_OK);
        finish();
    }
}
