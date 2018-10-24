package com.swat_cat.firstapp.movie;

import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.swat_cat.firstapp.R;

import java.io.File;

import io.reactivex.Observable;

public class MovieView implements MovieContract.View {

    private View root;
    private View gridView;

    private View progress;
    private EditText searchInput;
    private View searchBtn;
    private View deleteBtn;
    private TextView noResultText;

    public MovieView(View root){
        this.root = root;
        initView();
    }

    private void initView() {
        gridView = root.findViewById(R.id.grid_view);
        progress = root.findViewById(R.id.progress);
        searchInput = root.findViewById(R.id.search_movie);
        searchBtn = root.findViewById(R.id.search_button);
        deleteBtn = root.findViewById(R.id.delete_button);
        noResultText = root.findViewById(R.id.no_result_text);
    }

    @Override
    public void showNoResult(boolean show) {
        noResultText.setVisibility(show?View.VISIBLE:View.GONE);
        gridView.setVisibility(show?View.GONE:View.VISIBLE);
    }

    @Override
    public void showLoading(boolean show) {
        progress.setVisibility(show?View.VISIBLE:View.GONE);
        gridView.setVisibility(show?View.GONE:View.VISIBLE);
    }

    @Override
    public void setMovieList(File file) {

    }

    @Override
    public Observable<CharSequence> searchInputChanged() {
        return RxTextView.textChanges(searchInput);
    }

    @Override
    public String getSearchText() {
        return searchInput.getText().toString().trim();
    }

    @Override
    public void setSearchInputError(String error) {
        searchInput.setError(error);
    }

    @Override
    public Observable<Object> searchBtnAction() {
        return RxView.clicks(searchBtn);
    }

    @Override
    public Observable<Object> deleteBtnAction() {
        return RxView.clicks(deleteBtn);
    }

}
