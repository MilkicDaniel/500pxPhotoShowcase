package com.example.a500pxphotoshowcase.ViewModel;


import android.Manifest;
import android.app.DownloadManager;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import com.example.a500pxphotoshowcase.AsyncTasks.LoadPageAsyncTask;
import com.example.a500pxphotoshowcase.Fragments.ImageInfoFragment;
import com.example.a500pxphotoshowcase.Fragments.ImageShowcasePagerFragment;
import com.example.a500pxphotoshowcase.MainActivity;
import com.example.a500pxphotoshowcase.Models.PageModel;
import com.example.a500pxphotoshowcase.Models.PhotoModel;
import com.example.a500pxphotoshowcase.R;

import java.io.File;
import java.util.ArrayList;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<ArrayList<PhotoModel>> photoList = new MutableLiveData<>();
    private MutableLiveData<Integer> showcaseImagePosition = new MutableLiveData<>();


    private String key;
    private String filter = "popular";
    private int numberOfResults = 20;
    private int nextImageLoadPosition = -1;
    private int currentPageNumber = 1;
    long downloadReference = 0;

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

    public void openImageInfoPage(Context context, PhotoModel photo){


        ImageInfoFragment imageInfoFragment = new ImageInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ImageInfoFragment.BUNDLE_PHOTO, photo);
        imageInfoFragment.setArguments(bundle);

        ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.overlay_container, imageInfoFragment, ImageInfoFragment.TAG)
                .commit();

    }
    public void openExpandedImageGallery(Context context, int position){

        ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new ImageShowcasePagerFragment(), ImageShowcasePagerFragment.TAG)
                .addToBackStack(null)
                .commit();

        showcaseImagePosition.setValue(position);

    }

    public void downloadPhoto(Context context, PhotoModel photo) {

        int storagePermissionGranted = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (storagePermissionGranted == PackageManager.PERMISSION_GRANTED) {

            File dir = new File(Environment.getExternalStorageDirectory() + "/500px");

            if (!dir.exists())
                dir.mkdirs();

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            Uri downloadURI = Uri.parse(photo.getExpandedImageURL());
            DownloadManager.Request request = new DownloadManager.Request(downloadURI);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setDescription(photo.getName());
            request.setTitle(photo.getName());
            request.setVisibleInDownloadsUi(true);
            request.setMimeType("image/jpeg");
            request.setDestinationInExternalPublicDir("/500px/", photo.getName() + ".jpg");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            downloadReference = downloadManager.enqueue(request);
        }
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MutableLiveData<ArrayList<PhotoModel>> getPhotoList() {
        return photoList;
    }


    public MutableLiveData<Integer> getShowcaseImagePosition() {
        return showcaseImagePosition;
    }

    public void setShowcaseImagePosition(int position) {
        showcaseImagePosition.setValue(position);
    }
}
