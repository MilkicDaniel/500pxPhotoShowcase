package com.example.a500pxphotoshowcase.Fragments;


import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a500pxphotoshowcase.Models.PhotoModel;
import com.example.a500pxphotoshowcase.Models.UserModel;
import com.example.a500pxphotoshowcase.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class ImageInfoFragment extends Fragment {

    public static final String TAG = "ImageInfoFragment";
    public static final String BUNDLE_PHOTO = "BUNDLE_PHOTO";


    public ImageInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_image_info, container, false);
        TextView username = root.findViewById(R.id.info_user_profile_username);
        TextView name = root.findViewById(R.id.info_user_profile_name);
        final ImageView profileImage = root.findViewById(R.id.info_user_profile_image);
        TextView imageName = root.findViewById(R.id.info_image_name);
        TextView imageDescription = root.findViewById(R.id.info_image_description);


        Bundle bundle = getArguments();
        PhotoModel photo = bundle.getParcelable(BUNDLE_PHOTO);

        UserModel user = photo.getUser();

        username.setText(user.getUsername());
        name.setText(user.getFullname());
        Picasso.get().load(user.getUserpic_url()).fit().centerInside().into(profileImage, new Callback() {
            @Override
            public void onSuccess() {

                if(getContext() == null)
                    return;

                Bitmap imageBitmap = ((BitmapDrawable) profileImage.getDrawable()).getBitmap();
                RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                imageDrawable.setCircular(true);
                imageDrawable.setCornerRadius(90);
                profileImage.setImageDrawable(imageDrawable);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        imageName.setText(photo.getName());

        if(photo.getDescription() != null) {
            imageDescription.setMovementMethod(new ScrollingMovementMethod());
            imageDescription.setMovementMethod(LinkMovementMethod.getInstance());
            imageDescription.setText(Html.fromHtml(photo.getDescription()));
        }


        return root;
    }

}
