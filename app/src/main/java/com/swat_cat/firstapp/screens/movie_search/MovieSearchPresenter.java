package com.swat_cat.firstapp.screens.movie_search;

import com.swat_cat.firstapp.data.repositories.MovieRepository;
import com.swat_cat.firstapp.data.repositories.MovieNetworkRepositoryImpl;
import com.swat_cat.firstapp.services.rest.dto.SearchItemDTO;
import com.swat_cat.firstapp.services.rest.dto.SearchResultDTO;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MovieSearchPresenter implements MovieSearchContract.Presenter {

    private MovieSearchContract.View view;
    private MovieRepository repository;
    private CompositeDisposable subscriptions;
    private boolean isFavourite;

    public MovieSearchPresenter() {
        subscriptions = new CompositeDisposable();
    }

    @Override
    public void start(MovieSearchContract.View view) {
        this.view = view;
        repository = new MovieNetworkRepositoryImpl();
        subscriptions.add(
                view.searchChanged()
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
                        .flatMap(new Function<String, ObservableSource<SearchResultDTO>>() {
                            @Override
                            public ObservableSource<SearchResultDTO> apply(String query) throws Exception {
                                return repository.search(query);
                            }
                        })
                        .subscribe(
                                new Consumer<SearchResultDTO>() {
                                    @Override
                                    public void accept(SearchResultDTO searchResultDTO) throws Exception {
                                        view.showLoading(false);
                                        if (searchResultDTO != null) {
                                            List<SearchItemDTO> list = searchResultDTO.getSearch();
                                            if (list != null && !list.isEmpty()) {
                                                view.showEmpty(false);
                                                view.setMovieList(list);
                                                view.showList(true);
                                            } else {
                                                view.showEmpty(true);
                                                view.showList(false);
                                            }

                                        } else {
                                            view.showList(false);
                                            view.showEmpty(true);

                                        }
                                    }
                                }
                        ));
        subscriptions.add(view.menuAction().subscribe(
                o->{
                    if (!isFavourite){
                        view.showSearchField(false);
                        view.setMenuText("Show Search");
                        //TODO fetch movies from db and set to recyclerview
                        isFavourite = true;
                    }else {
                        view.showSearchField(true);
                        view.setMenuText("Show Favourites");
                        isFavourite = false;
                    }
                }
        ));

    }

    @Override
    public void stop() {
        view = null;
       // subscriptions.dispose();
       // subscriptions = null;
    }
}
