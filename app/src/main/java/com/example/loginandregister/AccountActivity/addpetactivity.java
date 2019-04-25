package com.example.loginandregister.AccountActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.loginandregister.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class addpetactivity extends AppCompatActivity {

    private EditText pet_name, pet_description,pet_breed, fee;
    private Spinner pet_age, pet_gender;
    private RadioButton btn_yes, btn_no, dog, cat; //, male, female;

    private ImageView pet_picture;
    private Button upload_pet;
    Bitmap bitmap;
    Uri uri;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private StorageReference petStorage;
    DatabaseReference databasepets;
    DatabaseReference userpetsDB;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private String UID = currentUser.getUid();

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
                startActivity(new Intent(addpetactivity.this, Profile.class));
                break;

            case R.id.menuSearch:
                startActivity(new Intent(addpetactivity.this, searchpage.class));
                break;

            case R.id.menuAddPet:
                startActivity(new Intent(addpetactivity.this, addpetactivity.class));
                break;

            case R.id.menuFavorites:
                Toast.makeText(this, "You clicked for favorites", Toast.LENGTH_SHORT).show();
                break;



        }
        return true;
    }
  ////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpetactivity);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("ResQpet");

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

        FirebaseApp.initializeApp(this);
        databasepets = FirebaseDatabase.getInstance().getReference("pets");
        petStorage = FirebaseStorage.getInstance().getReference("petimages");
        userpetsDB = FirebaseDatabase.getInstance().getReference("userpets");

        pet_name = (EditText) findViewById(R.id.txt_petname);
        pet_age = (Spinner) findViewById(R.id.spr_petage);
        pet_breed = (EditText) findViewById(R.id.txt_petbreed);
        pet_gender = (Spinner) findViewById(R.id.spr_petgender);
        pet_description = (EditText) findViewById(R.id.txt_petDescription);
        pet_picture = (ImageView) findViewById(R.id.img_petimage);
        btn_yes = (RadioButton) findViewById(R.id.btn_yes);
        btn_no = (RadioButton) findViewById(R.id.btn_no);
        dog = (RadioButton) findViewById(R.id.btn_dog);
        cat = (RadioButton) findViewById(R.id.btn_cat);
        fee = (EditText) findViewById(R.id.txt_fee);
//        female = (RadioButton)findViewById(R.id.btn_female);
//        male = (RadioButton)findViewById(R.id.btn_male);




        upload_pet = (Button) findViewById(R.id.btn_uploadForAdoption);

        pet_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else

//                var uploadTask = storageRef.child('images/' + new Date().getTime() + file.name).put(file, metadata);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }

        });

        upload_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    registerPet();
            }
        });
    }





    private void registerPet()
    {
        final String petname = pet_name.getText().toString().trim();
        final String petbreed = pet_breed.getText().toString().trim();
        final String petage = pet_age.getSelectedItem().toString();
        final String petdescription = pet_description.getText().toString().trim();
        final String petgender = pet_gender.getSelectedItem().toString();
        final String petfee = fee.getText().toString().trim();
        String type = "";
        if(dog.isChecked())
            type = "dog";
        else if(cat.isChecked())
            type = "cat";
        boolean isValid=false;
        final String pettype = type;
        if(petname.isEmpty())
        {
            makeText(getApplicationContext(), "Enter pet Name!", LENGTH_SHORT).show();
           return;
        }
        else if(petfee.isEmpty())
        {
            makeText(getApplicationContext(), "Enter adoption fee!", LENGTH_SHORT).show();
            return;
        }
        else if(petage.isEmpty())
        {
            makeText(getApplicationContext(), "Enter pet age!", LENGTH_SHORT).show();
           return;
        }
        else if(petbreed.isEmpty())
        {
            makeText(getApplicationContext(), "Enter pet breed!", LENGTH_SHORT).show();
             return;
        }
        else if(petgender.isEmpty()){
            makeText(getApplicationContext(), "Enter pet breed!", LENGTH_SHORT).show();
        }
        else if(petdescription.isEmpty())
        {
            makeText(getApplicationContext(), "Describe your pet!", LENGTH_SHORT).show();
           return;
        }
        else if(uri == null) {
            Toast.makeText(this, "Please select pet image", Toast.LENGTH_LONG).show();
            return;
        }
        else if(type.isEmpty())
        {
            makeText(getApplicationContext(), "Select pet type!", LENGTH_SHORT).show();
            return;
        }
        else {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] data = bytes.toByteArray();
            final String id = databasepets.push().getKey();
//            var uploadTask = storageRef.child('images/' + new Date().getTime() + file.name).put(file, metadata);

            petStorage.child("pets"+ id).putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String url = petStorage.getDownloadUrl().toString();
//                    String id = databasepets.push().getKey();
                    final String upID = userpetsDB.push().getKey();
                    boolean vac = false;
                    if (btn_yes.isChecked())
                        vac = true;
                    else if (btn_no.isChecked())
                        vac = false;
                    pet animal = new pet(id, petname, petage, petbreed, petdescription, url, petgender, vac, pettype, petfee);
                    final userpets user_pets = new userpets(upID, UID, id, "available", "upload");
                    databasepets.child(id).setValue(animal).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                userpetsDB.child(upID).setValue(user_pets).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Successfully put for adoption!", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(addpetactivity.this, Profile.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Sorry! cannot put yout pet for adoption!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });


        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                Toast.makeText(this, "hey you selected image" + bitmap, Toast.LENGTH_SHORT).show();
                pet_picture.setImageBitmap(bitmap);
                //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





    }






