package com.swat_cat.firstapp.screens.shopping_list.shopping_item;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.swat_cat.firstapp.base.App;
import com.swat_cat.firstapp.base.ImageEvent;
import com.swat_cat.firstapp.data.models.ShoppingItem;
import com.swat_cat.firstapp.services.navigation.BackNavigator;
import com.swat_cat.firstapp.utils.Constants;
import com.swat_cat.firstapp.utils.ImagePickChoiceView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class ShoppingItemPresenter implements ShoppingItemContract.Presenter {

    private ShoppingItemContract.View view;
    private ShoppingItem item;
    private CompositeDisposable subscriptions;
    private LoadImageCallback loadImageCallback;
    private File itemFile;
    private List<ShoppingItem> items;
    private BackNavigator backNavigator;

    public ShoppingItemPresenter(BackNavigator backNavigator) {
        item = new ShoppingItem();
        this.backNavigator = backNavigator;
    }

    @Override
    public void start(ShoppingItemContract.View view) {
        this.view = view;
        subscriptions = new CompositeDisposable();
        item = new ShoppingItem();
        items = Paper.book().read(Constants.ITEMS,new ArrayList<>());
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
        item.setTitle(view.getTitleText());

        if (itemFile != null) {
            item.setImage(itemFile.getAbsolutePath());
        }
        // set position to notify adapter what item changed
         item.setPosition(items.size());

        item.setSubTitle(view.getDescriptionText()+ item.getPosition());
        items.add(item);


        Paper.book().write(Constants.ITEMS, items);
       /* if(itemFile == null) {
            item.setImage(null);
        }*/
        Toast.makeText(App.getInstance().getApplicationContext(),"Press Back to check",Toast.LENGTH_SHORT).show();
    }

    public void setLoadImageCallback(LoadImageCallback loadImageCallback) {
        this.loadImageCallback = loadImageCallback;
    }

    @Subscribe
    public void onEvent(ImageEvent event){
        itemFile = event.file;
        view.setItemImage(itemFile);
    }

    @Subscribe
    public void onEvent(InfoUserActionEvent event){
        view.showInfo();
    }
}

