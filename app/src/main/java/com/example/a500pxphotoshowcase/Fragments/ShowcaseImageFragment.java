package com.example.a500pxphotoshowcase.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.a500pxphotoshowcase.Models.PhotoModel;
import com.example.a500pxphotoshowcase.R;
import com.squareup.picasso.Picasso;

public class ShowcaseImageFragment extends Fragment {

    public static final String TAG = "ShowcaseImageFragment";
    public static final String BUNDLE_PHOTO = "BUNDLE_PHOTO";

    public ShowcaseImageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_showcase_image, container, false);

        ImageView expandedImage = root.findViewById(R.id.showcase_image_image_view);


        Bundle bundle = getArguments();
        PhotoModel photo = bundle.getParcelable(BUNDLE_PHOTO);


        Picasso.get().load(photo.getExpandedImageURL()).fit().centerInside().into(expandedImage);


        expandedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return root;
    }

}
