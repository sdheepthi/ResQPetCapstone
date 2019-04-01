package com.example.loginandregister.AccountActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class addpetactivity extends AppCompatActivity {

    private EditText pet_name, pet_age, pet_breed, pet_description;
    private Spinner pet_gender;
    private RadioButton btn_yes, btn_no;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpetactivity);

        FirebaseApp.initializeApp(this);
        databasepets = FirebaseDatabase.getInstance().getReference("pets");
        petStorage = FirebaseStorage.getInstance().getReference("petimages");
        userpetsDB = FirebaseDatabase.getInstance().getReference("userpets");

        pet_name = (EditText) findViewById(R.id.txt_petname);
        pet_age = (EditText) findViewById(R.id.txt_petage);
        pet_breed = (EditText) findViewById(R.id.txt_petbreed);
        pet_gender = (Spinner) findViewById(R.id.spr_petgender);
        pet_description = (EditText) findViewById(R.id.txt_petDescription);
        pet_picture = (ImageView) findViewById(R.id.img_petimage);
        btn_yes = (RadioButton) findViewById(R.id.btn_yes);
        btn_no = (RadioButton) findViewById(R.id.btn_no);


        upload_pet = (Button) findViewById(R.id.btn_uploadForAdoption);

        pet_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
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




//    private boolean validate(){
//        final String petname = pet_name.getText().toString().trim();
//       final String petage = pet_age.getText().toString().trim();
//       final String petbreed = pet_breed.getText().toString().trim();
//       final String petdescription = pet_description.getText().toString().trim();
//        final String petgender = pet_gender.getSelectedItem().toString();
//
//        boolean isValid=false;
//        if(petname.isEmpty())
//       {
//           makeText(getApplicationContext(), "Enter pet Name!", LENGTH_SHORT).show();
////           return;
//       }
//       else if(petage.isEmpty())
//       {
//           makeText(getApplicationContext(), "Enter pet age!", LENGTH_SHORT).show();
////           return;
//       }
//       else if(petbreed.isEmpty())
//       {
//           makeText(getApplicationContext(), "Enter pet breed!", LENGTH_SHORT).show();
////           return;
//       }
//       else if(petgender.isEmpty()){
//            makeText(getApplicationContext(), "Enter pet breed!", LENGTH_SHORT).show();
//        }
//       else if(petdescription.isEmpty())
//       {
//           makeText(getApplicationContext(), "Describe your pet!", LENGTH_SHORT).show();
////           return;
//       }
//      else if(uri == null)
//           Toast.makeText(this,"Please select pet image",Toast.LENGTH_LONG).show();
//        return isValid;
//    }


    private void registerPet()
    {
        final String petname = pet_name.getText().toString().trim();
        final String petage = pet_age.getText().toString().trim();
        final String petbreed = pet_breed.getText().toString().trim();
        final String petdescription = pet_description.getText().toString().trim();
        final String petgender = pet_gender.getSelectedItem().toString();
        boolean isValid=false;
        if(petname.isEmpty())
        {
            makeText(getApplicationContext(), "Enter pet Name!", LENGTH_SHORT).show();
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
        else {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] data = bytes.toByteArray();
            String id = databasepets.push().getKey();

            petStorage.child("pets").putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String url = petStorage.getDownloadUrl().toString();
                    String id = databasepets.push().getKey();
                    final String upID = userpetsDB.push().getKey();
                    boolean vac = false;
                    if (btn_yes.isChecked())
                        vac = true;
                    else if (btn_no.isChecked())
                        vac = false;
                    pet animal = new pet(id, petname, petage, petbreed, petdescription, url, petgender, vac);
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





