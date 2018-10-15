package com.swat_cat.firstapp.shopping_list.shopping_item;

import com.squareup.otto.Subscribe;
import com.swat_cat.firstapp.base.ImageEvent;
import com.swat_cat.firstapp.models.ShoppingItem;
import com.swat_cat.firstapp.utils.ImagePickChoiceView;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ShoppingItemPresenter implements ShoppingItemContract.Presenter {

    private ShoppingItemContract.View view;
    private File itemImageFile;
    private ShoppingItem item;
    private CompositeDisposable subscriptions;
    private LoadImageCallback loadImageCallback;
    private File itemFile;

    public ShoppingItemPresenter() {
        item = new ShoppingItem();
    }

    @Override
    public void start(ShoppingItemContract.View view) {
        this.view = view;
        subscriptions = new CompositeDisposable();
        initActions();
    }

    private void initActions() {
        view.setSaveButttonEnabled(false);
        view.titleInputChanged().subscribe(new Observer<CharSequence>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscriptions.add(d);
            }

            @Override
            public void onNext(CharSequence charSequence) {
               view.setSaveButttonEnabled(isSaveEnabled());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        view.descriptionInputChanged().subscribe(new Observer<CharSequence>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscriptions.add(d);
            }

            @Override
            public void onNext(CharSequence charSequence) {
                view.setSaveButttonEnabled(isSaveEnabled());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        view.itemImageAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscriptions.add(d);
            }

            @Override
            public void onNext(Object o) {
                requestImageChoice();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void requestImageChoice() {
        view.showImageChoiceModal(true, new ImagePickChoiceView.ImagePickViewCallback() {
            @Override
            public void camera() {
                if (loadImageCallback!=null){
                    loadImageCallback.loadImage(LoadImageType.CAMERA);
                    view.showImageChoiceModal(false,null);
                }
            }

            @Override
            public void gallery() {
                if (loadImageCallback!=null){
                    loadImageCallback.loadImage(LoadImageType.GALLERY);
                    view.showImageChoiceModal(false,null);
                }
            }

            @Override
            public void cancel() {
                view.showImageChoiceModal(false,null);
            }
        });
    }

    private boolean isSaveEnabled() {
        return !view.getTitleText().isEmpty() && !view.getDescriptionText().isEmpty();
    }

    @Override
    public void stop() {
        if (subscriptions!=null){
            subscriptions.dispose();
            subscriptions = null;
        }
    }

    @Override
    public void saveItem() {

    }

    public void setLoadImageCallback(LoadImageCallback loadImageCallback) {
        this.loadImageCallback = loadImageCallback;
    }

    @Subscribe
    public void onEvent(ImageEvent event){
        itemFile = event.file;
        view.setItemImage(itemFile);
    }
}
