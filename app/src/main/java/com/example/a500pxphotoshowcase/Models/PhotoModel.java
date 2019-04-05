package com.example.a500pxphotoshowcase.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class PhotoModel implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("image_url")
    private ArrayList<String> imageUrls;
    @SerializedName("user")
    private UserModel user;


    public PhotoModel(int id, String name, String description, ArrayList<String> imageUrls) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrls = imageUrls;
    }

    @Override
    public String toString() {
        return "PhotoModel{\n" +
                "\n id=" + id +
                ",\n name='" + name + '\'' +
                ",\n description='" + description + '\'' +
                ",\n thumbnail URL='" + getThumbnailImageURL() + '\'' +
                ",\n expanded URL='" + getExpandedImageURL() + '\'' +
                ",\n user='" + user.toString() + '\'' +
                "\n}";
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UserModel getUser() {
        return user;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public String getThumbnailImageURL(){
        return imageUrls.get(0);
    }

    public String getExpandedImageURL(){
        return imageUrls.get(1);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeStringList(this.imageUrls);
    }

    protected PhotoModel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.imageUrls = in.createStringArrayList();
    }

    public static final Parcelable.Creator<PhotoModel> CREATOR = new Parcelable.Creator<PhotoModel>() {
        @Override
        public PhotoModel createFromParcel(Parcel source) {
            return new PhotoModel(source);
        }

        @Override
        public PhotoModel[] newArray(int size) {
            return new PhotoModel[size];
        }
    };
}
