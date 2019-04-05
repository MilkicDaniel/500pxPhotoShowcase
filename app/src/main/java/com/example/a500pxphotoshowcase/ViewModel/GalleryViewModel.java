package com.example.a500pxphotoshowcase.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.a500pxphotoshowcase.AsyncTasks.LoadPageAsyncTask;
import com.example.a500pxphotoshowcase.Models.PageModel;
import com.example.a500pxphotoshowcase.Models.PhotoModel;
import java.util.ArrayList;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<ArrayList<PhotoModel>> photoList = new MutableLiveData<>();


    private String key;
    private String filter = "popular";
    private int numberOfResults = 20;
    private int nextImageLoadPosition = -1;
    private int currentPageNumber = 1;

    public GalleryViewModel(){
        photoList.setValue(new ArrayList<PhotoModel>());
    }


    public void loadPhotos(){
        loadPhotos(-1);
    }

    public void loadPhotos(int position){


        if(position != nextImageLoadPosition)
            return;


        nextImageLoadPosition += numberOfResults-5;
        currentPageNumber = currentPageNumber + 1;

        new LoadPageAsyncTask(key, filter, numberOfResults, currentPageNumber, new LoadPageAsyncTask.TaskListener() {

            @Override
            public void onComplete(PageModel tmpPageModel) {

                if(tmpPageModel == null || tmpPageModel.getPhotos() == null) {
                    return;
                }

                photoList.getValue().addAll(tmpPageModel.getPhotos());
                photoList.setValue(photoList.getValue());

            }

        }).execute();

    }


    public void setKey(String key) {
        this.key = key;
    }

    public MutableLiveData<ArrayList<PhotoModel>> getPhotoList() {
        return photoList;
    }
}
