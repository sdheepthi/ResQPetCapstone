package com.example.loginandregister.AccountActivity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.loginandregister.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class favPage extends AppCompatActivity
{

    private RecyclerView recyclerView;
    private  favAdapter adapter;
    private List<favouritePet> idlist;
    private List<pet> petlist;


    private ProgressDialog progressDialog;

    // database references
    DatabaseReference dbpets;
    DatabaseReference dbfavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresults);
        FirebaseApp.initializeApp(this);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();




        recyclerView = findViewById(R.id.search_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        progressDialog = new ProgressDialog(this);


        idlist = new ArrayList<>();
        petlist =new ArrayList<>();

        adapter = new favAdapter(this, idlist);
        recyclerView.setAdapter(adapter);





        dbfavourite = FirebaseDatabase.getInstance().getReference("favourites");
        dbpets = FirebaseDatabase.getInstance().getReference("pets");

        Query getID = dbfavourite.orderByChild("uid").equalTo(user.getEmail());
        getID.addListenerForSingleValueEvent(idlistener);

//        Query getPetList;
//        for(String x:idlist)
//        {
//            getPetList = dbpets.orderByChild("petID").equalTo(x);
//            getPetList.addListenerForSingleValueEvent(petlistListner);
//        }




        //querying
petlist.clear();


    }

    ValueEventListener idlistener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            idlist.clear();
            if(dataSnapshot.exists()) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    favouritePet avai = snapshot.getValue(favouritePet.class);
                    idlist.add(avai);

                }
                adapter.notifyDataSetChanged();

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



    ValueEventListener petlistListner = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            petlist.clear();
            if(dataSnapshot.exists()) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    pet pets = snapshot.getValue(pet.class) ; //was petsearch
                    petlist.add(pets);
                }
                adapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

}