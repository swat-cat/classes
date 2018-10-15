package com.swat_cat.firstapp.shopping_list.shopping_item;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.swat_cat.firstapp.R;

import io.reactivex.Observable;

public class ShoppingItemView implements ShoppingItemContract.View {

    private View root;

    private ImageView imageInput;
    private EditText titleInput;
    private EditText descriptionInput;
    private View saveBtn;

    ShoppingItemView(View root){
        this.root = root;
        initView();
    }

    public void initView(){
        imageInput = root.findViewById(R.id.item_image);
        titleInput = root.findViewById(R.id.title);
        descriptionInput = root.findViewById(R.id.subtitle);
        saveBtn = root.findViewById(R.id.save_btn);
    }


    @Override
    public Observable<Object> itemImageInputChanged() {
        return RxView.clicks(imageInput);
    }

    @Override
    public ImageView getImageView() {
        return imageInput;
    }

    @Override
    public Observable<CharSequence> titleInputChanged() {
        return RxTextView.textChanges(titleInput);
    }

    @Override
    public String getTitleText() {
        return titleInput.getText().toString().trim();
    }

    @Override
    public void setTitleInputError(String error) {
        titleInput.setError(error);
    }

    @Override
    public Observable<CharSequence> descriptionInputChanged() {
        return RxTextView.textChanges(descriptionInput);
    }

    @Override
    public String getDescriptionText() {
        return descriptionInput.getText().toString().trim();
    }

    @Override
    public void setDescriptionInputError(String error) {
        descriptionInput.setError(error);
    }

    @Override
    public Observable<Object> saveBtnAction() {
        return RxView.clicks(saveBtn);
    }
}
