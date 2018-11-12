package com.swat_cat.firstapp.screens.movie_search;

import com.swat_cat.firstapp.data.models.Movie;
import com.swat_cat.firstapp.data.repositories.MovieLocalRepositoryImpl;
import com.swat_cat.firstapp.data.repositories.MovieRepository;
import com.swat_cat.firstapp.data.repositories.MovieNetworkRepositoryImpl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.CompletableObserver;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import timber.log.Timber;

public class MovieSearchPresenter implements MovieSearchContract.Presenter {

    private MovieSearchContract.View view;
    private MovieRepository networkRepository;
    private  MovieRepository localRepository;
    private CompositeDisposable subscriptions;
    private boolean isFavourite;
    private Disposable _search;
    private Disposable _favorites;
    private List<Movie> movies;

    public MovieSearchPresenter() {
        subscriptions = new CompositeDisposable();
    }

    @Override
    public void start(MovieSearchContract.View view) {
        this.view = view;
        networkRepository = new MovieNetworkRepositoryImpl();
        localRepository = new MovieLocalRepositoryImpl();
        showSearch();
        subscriptions.add(view.menuAction().subscribe(
                o->{
                    if (!isFavourite){
                        view.showSearchField(false);
                        view.setMenuText("Show Search");
                        //TODO fetch movies from db and set to recyclerview
                        isFavourite = true;
                        if (_favorites!=null) {
                            _favorites.dispose();
                        }
                        showFavourites();
                    }else {
                        view.showSearchField(true);
                        view.setMenuText("Show Favourites");
                        isFavourite = false;
                        if (_search!=null) {
                            _search.dispose();
                        }
                        showSearch();
                    }
                }
        ));

    }

    private void showSearch() {
        _search = view.searchChanged()
                .debounce(400,TimeUnit.MILLISECONDS)
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        view.showLoading(true);
                    }
                })
                .map(new Function<CharSequence, String>() {
                    @Override
                    public String apply(CharSequence charSequence) throws Exception {
                        return charSequence.toString().trim();
                    }
                })
                .flatMap(new Function<String, ObservableSource<List<Movie>>>() {
                    @Override
                    public ObservableSource<List<Movie>> apply(String query) throws Exception {
                        return networkRepository.search(query);
                    }
                })
                .subscribe(
                        this::setMovieList,
                        e ->{

                        }
                );
    }

    private void showFavourites() {

    }

    private void setMovieList(List<Movie> movies) {
        view.showLoading(false);
        if (movies != null && !movies.isEmpty()) {
            view.showEmpty(false);
            view.setMovieList(movies, this::addToFavourite);
            view.showList(true);
        } else {
            view.showEmpty(true);
            view.showList(false);
        }
    }

    private void  addToFavourite(Movie m){
        localRepository.saveMovie(m).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                subscriptions.add(d);
            }

            @Override
            public void onComplete() {
                Timber.d("Saved");
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void stop() {
        view = null;
        subscriptions.dispose();
        subscriptions = null;
    }
}
