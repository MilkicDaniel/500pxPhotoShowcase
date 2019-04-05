package com.example.a500pxphotoshowcase.Models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class PhotoModel {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("image_url")
    private ArrayList<String> imageUrls;


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

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public String getThumbnailImageURL(){
        return imageUrls.get(0);
    }

    public String getExpandedImageURL(){
        return imageUrls.get(1);
    }

}
