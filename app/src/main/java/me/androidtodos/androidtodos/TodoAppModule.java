package me.androidtodos.androidtodos;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidtodos.androidtodos.data.TodoListStore;

/**
 * Created by nathan on 8/8/15.
 */
@Module(
        injects={MainActivity.class, AddTaskActivity.class}
)
public class TodoAppModule {
    private final TodosApp application;
    private final TodoListStore store;
    public TodoAppModule(TodosApp app) {
        application = app;
        store = new TodoListStore(app);
    }

    @Provides
    @Singleton
    TodoListStore provideTodoListStore() {
        return store;
    }
}
