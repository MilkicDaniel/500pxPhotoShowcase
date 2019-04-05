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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
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
    private boolean isShowingInfo = false;
    private FrameLayout overlayConatiner;

    public static final String TAG = "ImageShowcasePagerFragment";


    public ImageShowcasePagerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        appBar = getActivity().findViewById(R.id.appbar);
        appBar.setExpanded(true, false);
        final ImageView infoButton = getActivity().findViewById(R.id.toolbar_info_button);
        final TextView title = getActivity().findViewById(R.id.toolbar_title);
        overlayConatiner =  ((MainActivity) getContext()).findViewById(R.id.overlay_container);


        galleryViewModel = ViewModelProviders.of((MainActivity)getContext()).get(GalleryViewModel.class);
        final Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        final Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);


        final View root =  inflater.inflate(R.layout.fragment_image_showcase_pager, container, false);
        final ViewPager viewPager = root.findViewById(R.id.image_showcase_pager);
        imageShowcasePagerAdapter = new ImageShowcasePagerAdapter(getChildFragmentManager(), galleryViewModel.getPhotoList().getValue());

        viewPager.setAdapter(imageShowcasePagerAdapter);
        viewPager.setCurrentItem(galleryViewModel.getShowcaseImagePosition().getValue());

        title.setText(galleryViewModel.getPhotoList().getValue().get(0).getName());
        infoButton.setImageResource(R.drawable.info_icon);

        galleryViewModel.openImageInfoPage(getContext(),
                galleryViewModel.getPhotoList().getValue().get(galleryViewModel.getShowcaseImagePosition().getValue()));


        overlayConatiner.setVisibility(View.GONE);
        infoButton.setVisibility(View.VISIBLE);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isShowingInfo) {
                    infoButton.setImageResource(R.drawable.close_icon);

                    if(overlayConatiner != null) {
                        overlayConatiner.setVisibility(View.VISIBLE);
                        overlayConatiner.startAnimation(slideDown);
                    }
                }
                else {
                    infoButton.setImageResource(R.drawable.info_icon);

                    if(overlayConatiner != null) {
                        overlayConatiner.setVisibility(View.GONE);
                        overlayConatiner.startAnimation(slideUp);
                    }
                }

                isShowingInfo = !isShowingInfo;

            }
        });

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
                title.setText(galleryViewModel.getPhotoList().getValue().get(position).getName());
                galleryViewModel.setShowcaseImagePosition(position);
                galleryViewModel.loadPhotos(position);

                galleryViewModel.openImageInfoPage(getContext(),
                        galleryViewModel.getPhotoList().getValue().get(galleryViewModel.getShowcaseImagePosition().getValue()));
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

        getActivity().findViewById(R.id.toolbar_info_button).setVisibility(View.GONE);
        ((TextView) getActivity().findViewById(R.id.toolbar_title)).setText(getContext().getResources().getString(R.string.title));
        overlayConatiner.setVisibility(View.GONE);

    }
}
