package com.example.a500pxphotoshowcase.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a500pxphotoshowcase.Adapters.ImageShowcasePagerAdapter;
import com.example.a500pxphotoshowcase.MainActivity;
import com.example.a500pxphotoshowcase.Models.PhotoModel;
import com.example.a500pxphotoshowcase.R;
import com.example.a500pxphotoshowcase.ViewModel.GalleryViewModel;

import java.util.ArrayList;


public class ImageShowcasePagerFragment extends Fragment {


    private GalleryViewModel galleryViewModel;
    private ImageShowcasePagerAdapter imageShowcasePagerAdapter;
    private AppBarLayout appBar;

    public static final String TAG = "ImageShowcasePagerFragment";

    public ImageShowcasePagerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        appBar = getActivity().findViewById(R.id.appbar);
        appBar.setExpanded(true, false);
        galleryViewModel = ViewModelProviders.of((MainActivity)getContext()).get(GalleryViewModel.class);


        View root =  inflater.inflate(R.layout.fragment_image_showcase_pager, container, false);
        ViewPager viewPager = root.findViewById(R.id.image_showcase_pager);
        imageShowcasePagerAdapter = new ImageShowcasePagerAdapter(getChildFragmentManager(), galleryViewModel.getPhotoList().getValue());

        viewPager.setAdapter(imageShowcasePagerAdapter);
        viewPager.setCurrentItem(galleryViewModel.getShowcaseImagePosition().getValue());


        galleryViewModel.getPhotoList().observe(this, new Observer<ArrayList<PhotoModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<PhotoModel> photoModels) {
                imageShowcasePagerAdapter.notifyDataSetChanged();
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                galleryViewModel.setShowcaseImagePosition(position);
                galleryViewModel.loadPhotos(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        return root;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(appBar != null)
            appBar.setExpanded(true, false);
    }
}
