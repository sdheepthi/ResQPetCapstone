package com.example.loginandregister.AccountActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.loginandregister.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

public class searchresults extends AppCompatActivity {

//    Intent intent = getIntent().getExtras();
//    Bundle extras = getIntent().getExtras();
//    String pet_type = extras.getString("PetType");
//    String pet_age = extras.getString("petage");
//    String pet_breed = extras.getString("PetBreed");
//    String pet_gender = extras.getString("Petgender");

    private RecyclerView recyclerView;
    private  petAdapter adapter;
    private List<pet> petlist; // was petsearch
    private List<userpets> availList;
    private Button view;
    private  Button fav;
    private HashSet<String> pid;

    private ProgressDialog progressDialog;

    // database references
    DatabaseReference dbpets;
    DatabaseReference dbuserpets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresults);
        FirebaseApp.initializeApp(this);
        Bundle extras = getIntent().getExtras();
        String pet_type = extras.getString("PetType");
        String pet_age = extras.getString("petage");
        String pet_breed = extras.getString("PetBreed");
        String pet_gender = extras.getString("Petgender");

        view = (Button) findViewById(R.id.rec_btnView);
        fav = (Button) findViewById(R.id.rec_btnfav);


        recyclerView = findViewById(R.id.search_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        progressDialog = new ProgressDialog(this);


        petlist =new ArrayList<>();
        availList = new ArrayList<>();
//        petID = new ArrayList<>();
        pid = new HashSet<String>();
//        adapter = new petAdapter(this, petlist);
//        recyclerView.setAdapter(adapter);





//        dbpets = FirebaseDatabase.getInstance().getReference("pets");
        dbuserpets = FirebaseDatabase.getInstance().getReference("userpers");
        dbpets = FirebaseDatabase.getInstance().getReference("pets");

//        if(pet_type==null && pet_age=="0-3" && pet_breed=="" && pet_gender==null)
//        {
            //display top 10 pets availabel
//            Query top = FirebaseDatabase.getInstance().getReference("userpets")
//                    .orderByChild("status").equalTo("available").limitToFirst(10);
//            top.addListenerForSingleValueEvent(userpetsListener);
//
//
//            //petID = availList.get(availList.indexOf());
//           ;
//           for(userpets x: availList)
//           {
//               pid.add(x.pid);
//           }
//           Query findPet;
//
//           for(String s:pid) {
               Query findPet = FirebaseDatabase.getInstance().getReference("pets").limitToFirst(6);
//                       .orderByChild("petID").equalTo();
               findPet.addListenerForSingleValueEvent(petlistListner);


         //  }
//        }

    }

    ValueEventListener userpetsListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            availList.clear();
            if(dataSnapshot.exists()) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    userpets avai = snapshot.getValue(userpets.class);
                    availList.add(avai);

                }
//                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    ValueEventListener petlistListner = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            petlist.clear();
            if(dataSnapshot.exists()) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                   pet pets = snapshot.getValue(pet.class) ; //was petsearch
                   petlist.add(pets);
//                    adapter.notifyDataSetChanged();
                }
//                adapter.notifyDataSetChanged();
                adapter = new petAdapter(searchresults.this, petlist);
                recyclerView.setAdapter(adapter);
            }
//            adapter.notifyDataSetChanged();

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
