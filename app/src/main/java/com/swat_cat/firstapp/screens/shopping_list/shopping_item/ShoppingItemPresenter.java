package com.swat_cat.firstapp.screens.shopping_list.shopping_item;

import android.Manifest;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.swat_cat.firstapp.base.App;
import com.swat_cat.firstapp.base.ImageEvent;
import com.swat_cat.firstapp.data.models.ContactWrapper;
import com.swat_cat.firstapp.data.models.ShoppingItem;
import com.swat_cat.firstapp.services.navigation.BackNavigator;
import com.swat_cat.firstapp.utils.Constants;
import com.swat_cat.firstapp.utils.ImagePickChoiceView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.mirrajabi.rxcontacts.Contact;
import ir.mirrajabi.rxcontacts.RxContacts;
import timber.log.Timber;

public class ShoppingItemPresenter implements ShoppingItemContract.Presenter {

    private ShoppingItemContract.View view;
    private ShoppingItem item;
    private CompositeDisposable subscriptions;
    private LoadImageCallback loadImageCallback;
    private File itemFile;
    private List<ShoppingItem> items;
    private BackNavigator backNavigator;
    final RxPermissions rxPermissions;


    public ShoppingItemPresenter(BackNavigator backNavigator, RxPermissions rxPermission) {
        item = new ShoppingItem();
        this.backNavigator = backNavigator;
        this.rxPermissions = rxPermission;
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
        rxPermissions.request(Manifest.permission.READ_CONTACTS)
                .subscribe(granted ->{
                    if (granted){
                        showContacts();
                    }
                });

    }

    private void showContacts() {
        Disposable d = RxContacts.fetch(App.getInstance().getApplicationContext())
                .filter(m->m.getInVisibleGroup() == 1)
                .toSortedList(Contact::compareTo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contacts -> {
                    if(contacts != null && !contacts.isEmpty()){
                        List<ContactWrapper> contactWrappers = new ArrayList<>();
                        for (Contact contact:contacts) {
                            contactWrappers.add(new ContactWrapper(contact, false));
                        }
                        view.showContactModal(contactWrappers, () -> {
                            String names = "";
                            for (ContactWrapper contactWrapper: contactWrappers) {
                                if (contactWrapper.isChecked() && contactWrapper.getContact().getDisplayName() != null && !contactWrapper.getContact().getDisplayName().isEmpty()) {
                                    names += contactWrapper.getContact().getDisplayName() + ", ";
                                }
                            }
                            Log.d("TAG" , names);
                        });
                    }
                });
    }
}

