package com.swat_cat.firstapp.shopping_list;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.swat_cat.firstapp.ForgotPasswordActivity;
import com.swat_cat.firstapp.list_example.ItemBoughtCallback;
import com.swat_cat.firstapp.models.ShoppingItem;
import com.swat_cat.firstapp.services.Navigator;
import com.swat_cat.firstapp.services.navigation.Screen;
import com.swat_cat.firstapp.services.navigation.ScreenType;
import com.swat_cat.firstapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by max_ermakov on 10/3/18.
 */

public class ShoppingListPresenter implements ShoppingListContract.Presenter {

    private ShoppingListContract.View view;
    private CompositeDisposable subscriptions;
    private Handler handler;
    private Navigator navigator;

    public ShoppingListPresenter() {
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void start(ShoppingListContract.View view) {
        this.view = view;
        subscriptions = new CompositeDisposable();
        List<ShoppingItem> items = Paper.book().read(Constants.ITEMS,new ArrayList<>());
        view.setShoppingList(items, new ItemBoughtCallback() {
            @Override
            public void itemBought(ShoppingItem item, boolean bought) {
                item.setBought(bought);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.updateList();
                    }
                },50);
            }
        });
        view.addItemAction().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscriptions.add(d);
            }

            @Override
            public void onNext(Object o) {
                addItem();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void stop() {
        subscriptions.dispose();
        subscriptions = null;
    }

    @Override
    public void addItem() {
        if (navigator!=null) {
            navigator.navigateTo(Screen.ITEM_DETAILS,ScreenType.FRAGMENT);
        }
    }

    @Override
    public void setView(ShoppingListContract.View view) {
        this.view = view;
    }

    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }
}
