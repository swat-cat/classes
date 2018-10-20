package com.swat_cat.firstapp.shopping_list.shopping_item;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.squareup.otto.Subscribe;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.base.ImageEvent;
import com.swat_cat.firstapp.models.ShoppingItem;
import com.swat_cat.firstapp.utils.Constants;
import com.swat_cat.firstapp.utils.ImagePickChoiceView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
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
        item = new ShoppingItem();
        initActions();
    }

    private void initActions() {
        view.setSaveButtonEnabled(false);
        view.titleInputChanged().subscribe(new Observer<CharSequence>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscriptions.add(d);
            }

            @Override
            public void onNext(CharSequence charSequence) {
               view.setSaveButtonEnabled(isSaveEnabled());
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
                view.setSaveButtonEnabled(isSaveEnabled());
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
        view.saveBtnAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscriptions.add(d);
            }

            @Override
            public void onNext(Object o) {
                saveItem();
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
        List<ShoppingItem> items = new ArrayList<>();
        item.setTitle(view.getTitleText());
        item.setSubTitle(view.getDescriptionText());
        //item.setImage();
        items.add(item);
       // Bitmap myBitmap = BitmapFactory.decodeFile(itemFile.getAbsolutePath());

        //Paper.book().write(Constants.ITEMS, items);
       /* if(itemFile == null) {
            item.setImage(null);
        }*/
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

