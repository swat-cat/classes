package com.swat_cat.firstapp.screens.movie_details;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.databinding.library.baseAdapters.BR;
import com.swat_cat.firstapp.MovieDetailsActivity;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.data.models.MovieDetails;
import com.swat_cat.firstapp.data.repositories.MovieNetworkRepositoryImpl;
import com.swat_cat.firstapp.data.repositories.MovieRepository;
import com.swat_cat.firstapp.services.mvvm.RecyclerBindingAdapter;
import com.swat_cat.firstapp.services.mvvm.RecyclerConfiguration;
import com.swat_cat.firstapp.services.mvvm.activities.ActivityViewModel;
import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MovieDetailsViewModel extends ActivityViewModel<MovieDetailsActivity> {

    private String movieId;
    public final ObservableBoolean isLoading = new ObservableBoolean(false);
    public final ObservableField<String> title = new ObservableField<>("");
    public final ObservableField<String> poster = new ObservableField<>("https://m.media-amazon.com/images/M/MV5BYzUzOTA5ZTMtMTdlZS00MmQ5LWFmNjEtMjE5MTczN2RjNjE3XkEyXkFqcGdeQXVyNTc2ODIyMzY@._V1_SX300.jpg");
    public final RecyclerConfiguration recyclerConfiguration = new RecyclerConfiguration();
    public final ObservableField<String> ratings = new ObservableField<>("");

    public final ObservableField<String> plot = new ObservableField<>("");
    private List<String> ganres = new ArrayList<>();
    private RecyclerBindingAdapter<String> ganresAdapter;
    private MovieRepository repository;
    private Disposable disposable;

    public MovieDetailsViewModel(MovieDetailsActivity activity, String movieId) {
        super(activity);
        this.movieId = movieId;
        repository = new MovieNetworkRepositoryImpl();
        initRecycler();
    }

    @Override
    public void onResume() {
        super.onResume();
        isLoading.set(true);
        disposable = repository.getMovieDetails(movieId).subscribe(
                movie -> {
                    if (movie!=null) {
                        ganres = movie.getGanres();
                        ganresAdapter.notifyDataSetChanged();
                        title.set(movie.getTitle());
                        plot.set(movie.getPlot());
                        poster.set(movie.getPosterUrl());
                        ratings.set(String.valueOf((movie.getRaiting())));
                        isLoading.set(false);
                    }
                },
                error ->{
                    activity.showInfoDialog(error.getMessage());
                    isLoading.set(false);
                }
        );
    }

    private void initRecycler() {
        ganresAdapter = getAdapter();
        final FlowLayoutManager layoutManager = new FlowLayoutManager().setAlignment(Alignment.LEFT);
        recyclerConfiguration.setLayoutManager(layoutManager);
        recyclerConfiguration.setItemAnimator(new DefaultItemAnimator());
        recyclerConfiguration.setAdapter(ganresAdapter);
    }

    private RecyclerBindingAdapter<String> getAdapter() {
        return new RecyclerBindingAdapter<>(R.layout.ganre_item, BR.ganre, ganres);
    }
}
