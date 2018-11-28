package com.swat_cat.firstapp.data.models;

import ir.mirrajabi.rxcontacts.Contact;

public class ContactWrapper {

    private Contact contact;
    private boolean checked;

    public ContactWrapper(Contact contact, boolean checked) {
        this.contact = contact;
        this.checked = checked;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
