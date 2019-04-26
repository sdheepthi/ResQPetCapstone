package com.example.loginandregister.AccountActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.loginandregister.MainActivity;
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



    //Nav bar menu/////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.menuHome:
                startActivity(new Intent(favPage.this, MainActivity.class));
                break;

            case R.id.menuSearch:
                startActivity(new Intent(favPage.this, searchpage.class));
                break;

            case R.id.menuAddPet:
                startActivity(new Intent(favPage.this, addpetactivity.class));
                //Toast.makeText(this, "You clicked to add pet", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuFavorites:
                startActivity(new Intent(favPage.this, favPage.class));
                break;

        }
        return true;
    }
    ////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresults);
        FirebaseApp.initializeApp(this);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();


        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("ResQpet");

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

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