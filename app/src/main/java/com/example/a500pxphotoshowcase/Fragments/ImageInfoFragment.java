package com.example.a500pxphotoshowcase.Fragments;


import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.example.a500pxphotoshowcase.MainActivity;
import com.example.a500pxphotoshowcase.Models.PhotoModel;
import com.example.a500pxphotoshowcase.Models.UserModel;
import com.example.a500pxphotoshowcase.R;
import com.example.a500pxphotoshowcase.ViewModel.GalleryViewModel;
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
        final ImageView downloadButton = root.findViewById(R.id.info_download_button);


        Bundle bundle = getArguments();
        final PhotoModel photo = bundle.getParcelable(BUNDLE_PHOTO);

        UserModel user = photo.getUser();
        username.setText(user.getUsername());
        name.setText(user.getFullname());

        final GalleryViewModel galleryViewModel = ViewModelProviders.of((MainActivity)getContext()).get(GalleryViewModel.class);

        int storagePermissionGranted = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (storagePermissionGranted != PackageManager.PERMISSION_GRANTED) {
            downloadButton.setVisibility(View.GONE);
        }


        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryViewModel.downloadPhoto(getContext(), photo);
                downloadButton.setClickable(false);
                downloadButton.setAlpha(0.5f);
            }
        });

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
