package com.swat_cat.firstapp.screens.movie_details;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;

import com.swat_cat.firstapp.MovieDetailsActivity;
import com.swat_cat.firstapp.data.models.MovieDetails;
import com.swat_cat.firstapp.data.repositories.MovieNetworkRepositoryImpl;
import com.swat_cat.firstapp.data.repositories.MovieRepository;
import com.swat_cat.firstapp.services.mvvm.RecyclerBindingAdapter;
import com.swat_cat.firstapp.services.mvvm.RecyclerConfiguration;
import com.swat_cat.firstapp.services.mvvm.activities.ActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsViewModel extends ActivityViewModel<MovieDetailsActivity> {

    private String movieId;
    public final ObservableBoolean isLoading = new ObservableBoolean(false);
    public final ObservableField<String> title = new ObservableField<>("Matrix");
    public final ObservableField<String> poster = new ObservableField<>("https://m.media-amazon.com/images/M/MV5BYzUzOTA5ZTMtMTdlZS00MmQ5LWFmNjEtMjE5MTczN2RjNjE3XkEyXkFqcGdeQXVyNTc2ODIyMzY@._V1_SX300.jpg");
    public final RecyclerConfiguration recyclerConfiguration = new RecyclerConfiguration();
    public final ObservableDouble ratings = new ObservableDouble(0.0);
    public final ObservableField<String> plot = new ObservableField<>("");
    private List<String> ganres = new ArrayList<>();
    private RecyclerBindingAdapter<String> ganresAdapter;
    private MovieRepository repository;

    public MovieDetailsViewModel(MovieDetailsActivity activity, String movieId) {
        super(activity);
        this.movieId = movieId;
        repository = new MovieNetworkRepositoryImpl();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
