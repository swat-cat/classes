package com.swat_cat.firstapp.screens.shopping_list.shopping_item;

import android.support.annotation.NonNull;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.swat_cat.firstapp.R;
import com.swat_cat.firstapp.data.models.ContactWrapper;

import java.util.ArrayList;
import java.util.List;

import ir.mirrajabi.rxcontacts.Contact;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<ContactWrapper> contacts;

    public ContactsAdapter(List<ContactWrapper> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactWrapper wrapper = contacts.get(position);
        holder.checkBox.setChecked(wrapper.isChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                wrapper.setChecked(b);
            }
        });
        if (wrapper.getContact() != null && wrapper.getContact().getPhoto() != null) {
            Picasso.get()
                    .load(wrapper.getContact().getPhoto())
                    .into(holder.avatar);
        }else{
            holder.avatar.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.getContext(), android.R.drawable.ic_menu_camera));
        }
        if(wrapper.getContact() != null && wrapper.getContact().getDisplayName() != null && !wrapper.getContact().getDisplayName().isEmpty()){
            holder.displayedName.setText(wrapper.getContact().getDisplayName());
        }
        if(wrapper.getContact() != null && wrapper.getContact().getPhoneNumbers() != null && !wrapper.getContact().getPhoneNumbers().isEmpty()){
            holder.phoneNumber.setText(new ArrayList<String>(wrapper.getContact().getPhoneNumbers()).get(0));
        }


    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatCheckBox checkBox;
        RoundedImageView avatar;
        TextView displayedName;
        TextView phoneNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.check_box);
            avatar = itemView.findViewById(R.id.avatar);
            displayedName = itemView.findViewById(R.id.displayed_name);
            phoneNumber = itemView.findViewById(R.id.phone_number);
        }
    }
}

