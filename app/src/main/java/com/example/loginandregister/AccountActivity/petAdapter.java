package com.example.loginandregister.AccountActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.loginandregister.R;

import java.util.List;

public class petAdapter extends RecyclerView.Adapter<petAdapter.PetViewHolder> {


    private Context mCxt; // to inflate the layout

    private List<pet> petList; // list of pets was petsearch

    public petAdapter(Context mCxt, List<pet> petList) {// was petsearch
        this.mCxt = mCxt;
        this.petList = petList;
    }

    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflate the view holder
//        LayoutInflater inflater = LayoutInflater.from(mCxt);
//        View view = inflater.inflate(R.layout.recycler_petview, null);

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
//      holder.petImg.setURI(mCtx.getResources()) something....
//        Picasso.get().load(pets.getImage()).into(petImg);
        Glide.with(mCxt).load("petimages/pets").into(holder.petImg);

//        Picasso.get().load("com.google.android.gms.tasks.zzu@5d446f5").fit().into(holder.petImg);
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