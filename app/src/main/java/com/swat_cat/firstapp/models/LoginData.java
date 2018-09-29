package com.swat_cat.firstapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by max_ermakov on 9/29/18.
 */

public class LoginData implements Parcelable {

    private String token;
    private int userId;
    private Weather weather;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeInt(this.userId);
        dest.writeParcelable(this.weather, flags);
    }

    public LoginData() {
    }

    protected LoginData(Parcel in) {
        this.token = in.readString();
        this.userId = in.readInt();
        this.weather = in.readParcelable(Weather.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoginData> CREATOR = new Parcelable.Creator<LoginData>() {
        @Override
        public LoginData createFromParcel(Parcel source) {
            return new LoginData(source);
        }

        @Override
        public LoginData[] newArray(int size) {
            return new LoginData[size];
        }
    };
}
