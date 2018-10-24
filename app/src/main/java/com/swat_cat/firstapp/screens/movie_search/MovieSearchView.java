package com.swat_cat.firstapp.screens.movie_search;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.services.rest.dto.SearchItemDTO;

import java.util.List;

import io.reactivex.Observable;

public class MovieSearchView implements MovieSearchContract.View{

    private View root;
    private EditText searchField;
    private RecyclerView searchResult;
    private View empty;
    private View progress;

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
    }

    @Override
    public Observable<CharSequence> searchChanged() {
        return RxTextView.textChanges(searchField);
    }

    @Override
    public void setMovieList(List<SearchItemDTO> movies) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(root.getContext(), 2, GridLayoutManager.VERTICAL,false);
        adapter = new MovieAdapter(movies);
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
}
