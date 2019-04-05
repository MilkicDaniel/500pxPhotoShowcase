package com.example.a500pxphotoshowcase.Models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class PageModel {

    @SerializedName("feature")
    private String feature;
    @SerializedName("current_page")
    private int currentPage;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_items")
    private int totalItems;
    @SerializedName("photos")
    private ArrayList<PhotoModel> photos = new ArrayList<>();


    public PageModel(){}

    public PageModel(String feature , int currentPage, int totalPages, int totalItems, ArrayList<PhotoModel> photos) {
        this.feature = feature;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "PageModel{" +
                "feature='" + feature + '\'' +
                ", currentPage=" + currentPage +
                ", totalPages=" + totalPages +
                ", totalItems=" + totalItems +
                ", photos=" + photos +
                '}';
    }


    public String getFeature() {
        return feature;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public ArrayList<PhotoModel> getPhotos() {
        return photos;
    }
}
