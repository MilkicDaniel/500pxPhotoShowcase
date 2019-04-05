package com.example.a500pxphotoshowcase.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.a500pxphotoshowcase.Fragments.ShowcaseImageFragment;
import com.example.a500pxphotoshowcase.Models.PhotoModel;
import java.util.ArrayList;

public class ImageShowcasePagerAdapter extends FragmentPagerAdapter {


    private ArrayList<PhotoModel> photoList;


    public ImageShowcasePagerAdapter(FragmentManager fm, ArrayList<PhotoModel> photoList) {
        super(fm);
        this.photoList = photoList;
    }


    @Override
    public Fragment getItem(int position) {
        ShowcaseImageFragment imageFragment = new ShowcaseImageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ShowcaseImageFragment.BUNDLE_PHOTO, photoList.get(position));
        imageFragment.setArguments(bundle);
        return imageFragment;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }
}
