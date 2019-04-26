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

public class petAdapter extends RecyclerView.Adapter<petAdapter.PetViewHolder> {


    private Context mCxt; // to inflate the layout

    private List<pet> petList; // list of pets was petsearch

    public  List<favouritePet> favlist;

    DatabaseReference favdb = FirebaseDatabase.getInstance().getReference("Favorite");
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseAuth auth = FirebaseAuth.getInstance();


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
    public void onBindViewHolder(final PetViewHolder holder, int position) {

        // getting petPosition

        final pet pets = petList.get(position); //was petsearch

        // bind the respective text to the view for display
        holder.petName.setText(pets.petname);
        holder.petAge.setText(pets.petage);
        holder.petBreed.setText(pets.petBreed);
//      holder.petImg.setURI(mCtx.getResources()) something....
//        Picasso.get().load(pets.getImage()).into(petImg);
//        Glide.with(mCxt).load("petimages/pets").into(holder.petImg);
        Query query = FirebaseDatabase.getInstance().getReference("favourites").orderByChild("pid_user").equalTo(pets.petID+user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                  holder.fav.setText("saved");
                   holder.fav.setEnabled(false);
                }
                else {
                    holder.fav.setText("Favourite");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(mCxt, petprofileview.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("petid",pets.getPetID());
                        bundle.putString("petName",pets.getPetname());
                        bundle.putString("petAge",pets.getPetage());
                        bundle.putString("petBreed",pets.getPetBreed());
                        bundle.putString("petGender",pets.getPetgender());

                        intent.putExtras(bundle);
                        mCxt.startActivity(intent);
                                        }


        });
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = favdb.push().getKey();
                favouritePet favPet = new favouritePet(id, user.getEmail(),pets.getPetID(), pets.getPetBreed(), pets.getPetname(), pets.getPetgender(), pets.getPetage());
                favdb.child(id).setValue(favPet);
                holder.fav.setText("saved");
                holder.fav.setEnabled(false);



            }
        });

        Picasso picassoInstance = new Picasso.Builder(mCxt)
                .addRequestHandler(new FirebaseRequestHandler())
                .build();

        picassoInstance.load("gs://resqpet-a4760.appspot.com/petimages/pets"+pets.getPetID())
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
        public Button view;
        public Button fav;
        public PetViewHolder( View itemView) {
            super(itemView);

            petName = itemView.findViewById(R.id.rec_petName);
            petAge = itemView.findViewById(R.id.rec_petAge);
            petBreed = itemView.findViewById(R.id.rec_petBreed);
            petImg = itemView.findViewById(R.id.rec_petimg);
            view = itemView.findViewById(R.id.rec_btnView);
            fav = itemView.findViewById(R.id.rec_btnfav);



        }



    }
    ValueEventListener petlistListner = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            favlist.clear();
            if(dataSnapshot.exists()) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    favouritePet pets = snapshot.getValue(favouritePet.class) ; //was petsearch
//                    favlist.add(pets);
                }
//                adapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };







}