package me.androidtodos.androidtodos;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import me.androidtodos.androidtodos.data.TodoListStore;

/**
 * Created by nathan on 8/8/15.
 */
public class TodosApp extends Application {
    private ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();
        graph = ObjectGraph.create(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.asList(
            new TodoAppModule(this)
        );
    }

    public <T> T inject(T obj) {
        return graph.inject(obj);
    }
}
