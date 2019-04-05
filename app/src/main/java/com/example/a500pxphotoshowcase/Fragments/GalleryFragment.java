package com.example.a500pxphotoshowcase.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.a500pxphotoshowcase.Adapters.GalleryRecyclerAdapter;
import com.example.a500pxphotoshowcase.MainActivity;
import com.example.a500pxphotoshowcase.Models.PhotoModel;
import com.example.a500pxphotoshowcase.R;
import com.example.a500pxphotoshowcase.ViewModel.GalleryViewModel;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class GalleryFragment extends Fragment {


    public static final String TAG = "GalleryFragment";
    private GalleryViewModel galleryViewModel;
    private GalleryRecyclerAdapter galleryRecyclerAdapter;

    public GalleryFragment() {
    }


    public int getNumberOfColumns(){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float width = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (width / ((int) getContext().getResources().getDimension(R.dimen.gallery_item_width) / displayMetrics.density));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        galleryViewModel = ViewModelProviders.of((MainActivity)getContext()).get(GalleryViewModel.class);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getContext().getString(R.string.saved_settings), MODE_PRIVATE);
        galleryViewModel.setKey(sharedPreferences.getString(getContext().getString(R.string.saved_key), ""));

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.gallery_recycler_view);


        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getNumberOfColumns()));
        galleryRecyclerAdapter = (new GalleryRecyclerAdapter(getContext(), galleryViewModel.getPhotoList().getValue()));

        recyclerView.setAdapter(galleryRecyclerAdapter);


        if(galleryViewModel.getPhotoList().getValue().size() == 0)
            galleryViewModel.loadPhotos();


        galleryViewModel.getPhotoList().observe(this, new Observer<ArrayList<PhotoModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<PhotoModel> photoModels) {
                galleryRecyclerAdapter.notifyDataSetChanged();
            }
        });

        galleryViewModel.getShowcaseImagePosition().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer position) {
                recyclerView.scrollToPosition(position);
            }
        });



        return root;
    }

}
