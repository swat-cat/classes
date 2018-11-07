package com.swat_cat.firstapp.screens.movie_search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.data.models.Movie;
import com.swat_cat.firstapp.services.rest.dto.SearchItemDTO;

import java.util.List;

import io.reactivex.Observable;

public class MovieSearchView implements MovieSearchContract.View{

    private View root;
    private EditText searchField;
    private RecyclerView searchResult;
    private View empty;
    private View progress;
    private View filterIcon;
    private View menu;
    private TextView menuText;

    private MovieAdapter adapter;
    public MovieSearchView(View root) {
        this.root = root;
        initView();
    }

    private void initView() {
        searchField = root.findViewById(R.id.search);
        searchResult = root.findViewById(R.id.result);
        empty = root.findViewById(R.id.empty);
        progress = root.findViewById(R.id.progress);
        filterIcon = root.findViewById(R.id.filter);
        menu = root.findViewById(R.id.menu);
        menuText = root.findViewById(R.id.menu_text);
        initMenu();
    }

    @Override
    public void setMenuText(String text) {
        menuText.setText(text);
    }

    private void initMenu() {
        filterIcon.setOnClickListener(v -> {
            if (menu.getVisibility() != View.VISIBLE) {
                showMenu();
            } else {
                hideMenu();
            }
        });

    }

    private void hideMenu() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(menu,"translationY", 0.0f,-Float.valueOf(menu.getHeight()));
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menu.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    private void showMenu() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(menu,"translationY", -Float.valueOf(menu.getHeight()), 0.0f);
        animator.setDuration(500);
        menu.setVisibility(View.VISIBLE);
        animator.start();
    }

    @Override
    public void showSearchField(boolean show) {
        searchField.setVisibility(show?View.VISIBLE:View.GONE);
    }

    @Override
    public Observable<Object> menuAction() {
        return RxView.clicks(menuText);
    }

    @Override
    public Observable<CharSequence> searchChanged() {
        return RxTextView.textChanges(searchField);
    }

    @Override
    public void setMovieList(List<Movie> movies, AddToFavouriteCallback callback) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(root.getContext(), 2, GridLayoutManager.VERTICAL,false);
        adapter = new MovieAdapter(movies,callback);
        searchResult.setLayoutManager(gridLayoutManager);
        searchResult.setAdapter(adapter);
    }

    @Override
    public void showLoading(boolean show) {
        progress.setVisibility(show?View.VISIBLE:View.GONE);
    }

    @Override
    public void showEmpty(boolean show) {
        empty.setVisibility(show?View.VISIBLE:View.GONE);
    }

    @Override
    public void showList(boolean show) {
        searchResult.setVisibility(show?View.VISIBLE:View.GONE);
    }
}
