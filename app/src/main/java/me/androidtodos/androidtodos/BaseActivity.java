package me.androidtodos.androidtodos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nathan on 8/8/15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((TodosApp)getApplication()).inject(this);
    }
}
