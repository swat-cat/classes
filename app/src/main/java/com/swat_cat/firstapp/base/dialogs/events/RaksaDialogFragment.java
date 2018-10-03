package com.swat_cat.firstapp.base.dialogs.events;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.doctorraksa.R;

/**
 * Created by max_ermakov on 4/20/18.
 */

public class RaksaDialogFragment extends DialogFragment {

    private ViewGroup contentContainer;

    public RaksaDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_modal_message, container);
        contentContainer = view.findViewById(R.id.dialog_content_container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    public ViewGroup getContentContainer() {
        return contentContainer;
    }

}
