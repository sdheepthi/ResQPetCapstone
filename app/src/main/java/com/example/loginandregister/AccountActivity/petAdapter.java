package com.example.loginandregister.AccountActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


import com.example.loginandregister.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;


import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.squareup.picasso.Picasso.LoadedFrom.NETWORK;

public class petAdapter extends RecyclerView.Adapter<petAdapter.PetViewHolder>{

    private StorageReference ref;
    private Context mCxt; // to inflate the layout


    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("petimages").child("-LcO1rZC4lIoPjyxjk2c");


    private List<pet> petList; // list of pets was petsearch

    public petAdapter(Context mCxt, List<pet> petList) {// was petsearch
        this.mCxt = mCxt;
        this.petList = petList;
    }



    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View view = LayoutInflater.from(mCxt).inflate(R.layout.recycler_petview,parent,false);
       PetViewHolder viewHolder = new PetViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PetViewHolder holder, int position) {

        // getting petPosition

        pet pets = petList.get(position); //was petsearch
        // bind the respective text to the view for display
        holder.petName.setText(pets.petname);
        holder.petAge.setText(pets.petage);
        holder.petBreed.setText(pets.petBreed);

        ref = FirebaseStorage.getInstance().getReference("petimages");
        String location = "gs://resqpet-a4760.appspot.com/petimages/"+pets.getPetID();


        Picasso picassoInstance= new  Picasso.Builder(mCxt)
                .addRequestHandler(new FirebaseRequestHandler())
                .build();
        picassoInstance.load(location)
                .fit().centerInside()
                .into(holder.petImg);
}
    @Override
    public int getItemCount() {
        return petList.size();
    }


    public class PetViewHolder extends RecyclerView.ViewHolder {

        public TextView petName, petAge, petBreed;
        public ImageView petImg;

        public PetViewHolder( View itemView) {
            super(itemView);

            petName = itemView.findViewById(R.id.rec_petName);
            petAge = itemView.findViewById(R.id.rec_petAge);
            petBreed = itemView.findViewById(R.id.rec_petBreed);
            petImg = itemView.findViewById(R.id.rec_petimg);



        }




    }










}