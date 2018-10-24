package com.swat_cat.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.swat_cat.firstapp.base.BaseActivity;
import com.swat_cat.firstapp.movie.MovieAdapter;
import com.swat_cat.firstapp.movie.MovieContract;
import com.swat_cat.firstapp.movie.MoviePresenter;
import com.swat_cat.firstapp.movie.MovieView;

public class MovieActivity extends BaseActivity {

    private View root;
    private MovieContract.View view;
    private MovieContract.Presenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);



       /* root = findViewById(R.id.root);
        view = new MovieView(root);
        presenter = new MoviePresenter();*/
    }
}
