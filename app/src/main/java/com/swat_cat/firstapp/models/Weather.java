package com.swat_cat.firstapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by max_ermakov on 9/29/18.
 */

public class Weather implements Parcelable {

    private int temperature;
    private int humidity;
    private int presure;
    private String status;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPresure() {
        return presure;
    }

    public void setPresure(int presure) {
        this.presure = presure;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.temperature);
        dest.writeInt(this.humidity);
        dest.writeInt(this.presure);
        dest.writeString(this.status);
    }

    public Weather() {
    }

    protected Weather(Parcel in) {
        this.temperature = in.readInt();
        this.humidity = in.readInt();
        this.presure = in.readInt();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel source) {
            return new Weather(source);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    @Override
    public String toString() {
        return "Weather{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", presure=" + presure +
                ", status='" + status + '\'' +
                '}';
    }
}
