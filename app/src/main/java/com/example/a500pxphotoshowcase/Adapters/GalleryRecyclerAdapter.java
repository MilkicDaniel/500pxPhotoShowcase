package com.example.a500pxphotoshowcase.Adapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.a500pxphotoshowcase.MainActivity;
import com.example.a500pxphotoshowcase.Models.PhotoModel;
import com.example.a500pxphotoshowcase.R;
import com.example.a500pxphotoshowcase.ViewModel.GalleryViewModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryRecyclerAdapter.ViewHolder>{

    private ArrayList<PhotoModel> photoList;
    private GalleryViewModel model;
    private Context context;


    public GalleryRecyclerAdapter(Context context, ArrayList<PhotoModel> photoList) {
        this.context = context;
        this.photoList = photoList;
        this.model = ViewModelProviders.of((MainActivity)context).get(GalleryViewModel.class);;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.gallery_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

        final PhotoModel photo = photoList.get(position);

        Picasso.get().load(photo.getThumbnailImageURL()).fit().centerCrop().into(viewHolder.image);

        model.loadPhotos(position);

        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.openExpandedImageGallery(context, viewHolder.getAdapterPosition());
            }
        });



    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        private ViewHolder(View rootView){
            super(rootView);

            image = rootView.findViewById(R.id.gallery_item_image);
        }
    }


}
