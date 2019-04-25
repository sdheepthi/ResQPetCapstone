package com.example.loginandregister.AccountActivity;

import android.annotation.SuppressLint;
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
import com.example.loginandregister.MainActivity;
import com.example.loginandregister.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.widget.Toast.*;

public class favAdapter extends RecyclerView.Adapter<favAdapter.PetViewHolder> {


    private Context mCxt; // to inflate the layout

    private List<favouritePet> petList; // list of pets was petsearch

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseAuth auth = FirebaseAuth.getInstance();


    public favAdapter(Context mCxt, List<favouritePet> petList) {// was petsearch
        this.mCxt = mCxt;
        this.petList = petList;
    }



    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflate the view holder
//        LayoutInflater inflater = LayoutInflater.from(mCxt);
//        View view = inflater.inflate(R.layout.recycler_petview, null);

        View view = LayoutInflater.from(mCxt).inflate(R.layout.fav_row,parent,false);
        PetViewHolder viewHolder = new PetViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PetViewHolder holder, final int position) {

        // getting petPosition

        final favouritePet pets = petList.get(position); //was petsearch

        // bind the respective text to the view for display
        holder.petName.setText(pets.petName);
        holder.petAge.setText(pets.age);
        holder.petBreed.setText(pets.breed);
//      holder.petImg.setURI(mCtx.getResources()) something....
//        Picasso.get().load(pets.getImage()).into(petImg);
//        Glide.with(mCxt).load("petimages/pets").into(holder.petImg);


        Picasso picassoInstance = new Picasso.Builder(mCxt)
                .addRequestHandler(new FirebaseRequestHandler())
                .build();

        picassoInstance.load("gs://resqpet-a4760.appspot.com/petimages/pets"+pets.getPid())
                .fit().centerInside()
                .into(holder.petImg);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference("favourites").child(pets.id).removeValue();
                petList.remove(position);

                notifyItemRemoved(position);

            }
        });
    }
    @Override
    public int getItemCount() {
        return petList.size();
    }


    public class PetViewHolder extends RecyclerView.ViewHolder {

        public TextView petName, petAge, petBreed;
        public ImageView petImg;
        public Button delete;

        public PetViewHolder( View itemView) {
            super(itemView);

            petName = itemView.findViewById(R.id.recFav_petName);
            petAge = itemView.findViewById(R.id.recFav_petAge);
            petBreed = itemView.findViewById(R.id.recFav_petBreed);
            petImg = itemView.findViewById(R.id.recFav_petimg);
            delete = itemView.findViewById(R.id.recFav_btndelete);



        }



    }







}