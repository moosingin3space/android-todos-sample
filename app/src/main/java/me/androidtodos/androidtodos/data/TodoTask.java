package me.androidtodos.androidtodos.data;

/**
 * Created by nathan on 8/8/15.
 */
public class TodoTask {
    public String text;
    public boolean done;

    public TodoTask(String text) {
        this.text = text;
        this.done = false;
    }

    public TodoTask(String text, boolean done) {
        this.text = text;
        this.done = done;
    }
}
