package com.swat_cat.firstapp.shopping_list.shopping_item;

import android.widget.ImageView;

import com.swat_cat.firstapp.utils.ImagePickChoiceView;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;

public interface ShoppingItemContract {
    interface View{
        Observable<Object> itemImageAction();
        void setItemImage(File file);
        Observable<CharSequence> titleInputChanged();
        String getTitleText();
        Observable<CharSequence> descriptionInputChanged();
        String getDescriptionText();
        void setSaveButttonEnabled(boolean enabled);
        Observable<Object> saveBtnAction();
        void showImageChoiceModal(boolean show, ImagePickChoiceView.ImagePickViewCallback callback);
    }
    interface Presenter{
        void setLoadImageCallback(LoadImageCallback loadImageCallback);
        void start(View view);
        void stop();
        void saveItem();

    }
}
