package com.swat_cat.firstapp.screens.movie_search;

import com.swat_cat.firstapp.data.repositories.MovieRepository;
import com.swat_cat.firstapp.data.repositories.MovieRepositoryImpl;
import com.swat_cat.firstapp.services.rest.dto.SearchItemDTO;
import com.swat_cat.firstapp.services.rest.dto.SearchResultDTO;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MovieSearchPresenter implements MovieSearchContract.Presenter {

    private MovieSearchContract.View view;
    private MovieRepository repository;
    private CompositeDisposable subscriptions;

    public MovieSearchPresenter() {
        subscriptions = new CompositeDisposable();
    }

    @Override
    public void start(MovieSearchContract.View view) {
        this.view = view;
        repository = new MovieRepositoryImpl();
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
                        .flatMap(new Function<CharSequence, ObservableSource<SearchResultDTO>>() {
                            @Override
                            public ObservableSource<SearchResultDTO> apply(CharSequence charSequence) throws Exception {
                                String query= charSequence.toString().trim();
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

        view.searchChanged()
                .debounce(400, TimeUnit.MILLISECONDS)
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        subscriptions.add(d);
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        String query = charSequence.toString().trim();
                        view.showLoading(true);
                        repository.search(query)
                                .subscribe(new Observer<SearchResultDTO>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        subscriptions.add(d);
                                    }

                                    @Override
                                    public void onNext(SearchResultDTO searchResultDTO) {

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

                                    @Override
                                    public void onError(Throwable e) {
                                        view.showLoading(false);
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoading(false);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void stop() {
        view = null;
       // subscriptions.dispose();
       // subscriptions = null;
    }
}
