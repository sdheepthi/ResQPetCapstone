package com.example.loginandregister.AccountActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.loginandregister.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.widget.Toast.*;

public class pet_profile_adapter extends RecyclerView.Adapter<pet_profile_adapter.PetViewHolder> {


    private Context mCxt; // to inflate the layout

    private List<pet> petList; // list of pets was petsearch

    public pet_profile_adapter(Context mCxt, List<pet> petList) {// was petsearch
        this.mCxt = mCxt;
        this.petList = petList;
    }


    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflate the view holder
//        LayoutInflater inflater = LayoutInflater.from(mCxt);
//        View view = inflater.inflate(R.layout.recycler_petview, null);

        View view = LayoutInflater.from(mCxt).inflate(R.layout.pet_profile_view, parent, false);
        PetViewHolder viewHolder = new PetViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PetViewHolder holder, int position) {

        // getting petPosition

        final pet pets = petList.get(position); //was petsearch

        // bind the respective text to the view for display
        holder.petName.setText(pets.petname);
        holder.petAge.setText(pets.petage);
        holder.petBreed.setText(pets.petBreed);
        holder.petFee.setText(pets.fee);
        holder.petGender.setText(pets.petgender);
        holder.petDescrip.setText(pets.petdesc);
        holder.petVacc.setText(String.valueOf(pets.vaccination));
        Picasso picassoInstance = new Picasso.Builder(mCxt)
                .addRequestHandler(new FirebaseRequestHandler())
                .build();

        picassoInstance.load("gs://resqpet-a4760.appspot.com/petimages/pets" + pets.getPetID())
                .fit().centerInside()
                .into(holder.petImg);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }


    public class PetViewHolder extends RecyclerView.ViewHolder {

        public TextView petName, petAge, petBreed, price,gender, petGender, petFee,petDescrip, petVacc;
        public ImageView petImg;

        public PetViewHolder(View itemView) {
            super(itemView);


            petName = itemView.findViewById(R.id.rec_petName);
            petAge = itemView.findViewById(R.id.rec_petAge);
            petBreed = itemView.findViewById(R.id.rec_petBreed);
            petImg = itemView.findViewById(R.id.rec_petimg);
            petGender = itemView.findViewById(R.id.gender);
            petFee = itemView.findViewById(R.id.adopt_fee);
            petDescrip = itemView.findViewById(R.id.pet_descrip);
            petVacc = itemView.findViewById(R.id.vaccinate);

        }


    }
}








