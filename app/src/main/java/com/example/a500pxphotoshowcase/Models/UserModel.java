package com.example.a500pxphotoshowcase.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserModel implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("username")
    private String username;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("userpic_url")
    private String userpic_url;
    @SerializedName("country")
    private String country;


    public UserModel(int id, String username, String fullname, String userpic_url, String country) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.userpic_url = userpic_url;
        this.country = country;
    }


    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username=" + username +
                ", fullname=" + fullname +
                ", userpic_url=" + userpic_url +
                ", country=" + country +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUserpic_url() {
        return userpic_url;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.fullname);
        dest.writeString(this.userpic_url);
        dest.writeString(this.country);
    }

    protected UserModel(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.fullname = in.readString();
        this.userpic_url = in.readString();
        this.country = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
