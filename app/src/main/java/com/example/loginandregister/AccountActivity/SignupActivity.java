package com.example.loginandregister.AccountActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loginandregister.MainActivity;
import com.example.loginandregister.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.*;

public class SignupActivity extends AppCompatActivity  {

    private EditText fname, lname, email, phno, addrl1, addrl2, pwd,postal, state;
    private ProgressBar progressBar;
    private  Button signUpButton;
    private FirebaseAuth mAuth;

    DatabaseReference databaseUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        fname = (EditText) findViewById(R.id.txt_fname);
        lname = (EditText) findViewById(R.id.txt_lname);
        email = (EditText) findViewById(R.id.txt_email);
        phno = (EditText) findViewById(R.id.txt_ph);
        addrl1 = (EditText) findViewById(R.id.txt_addrl1);
        addrl2 = (EditText) findViewById(R.id.txt_addrl2);
        pwd = (EditText) findViewById(R.id.txt_pwd);
        postal = (EditText) findViewById(R.id.txt_pc);
        state = (EditText) findViewById(R.id.txt_state);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        signUpButton = (Button) findViewById(R.id.sign_up_button);
        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

//    public void OnClick(View v)
//           {
//               switch (v.getId()) {
//                    case R.id.btn_sign_up:
//                        registerUser();
//                        break;
//                }
//
//           }



    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null){
            // for user with a login
        }
    }

    private void registerUser(){

        final String password = pwd.getText().toString().trim();
        final String firstName = fname.getText().toString().trim();
        final String lastName = lname.getText().toString().trim();
        final String userEmail = email.getText().toString().trim();
        final String phone = phno.getText().toString().trim();
        final String addr1 = addrl1.getText().toString().trim();
        final String addr2 = addrl2.getText().toString().trim();
        final String postal_code = postal.getText().toString().trim();
        final String state_value = state.getText().toString().trim();

        if(firstName.isEmpty())
        {
            makeText(getApplicationContext(), "Enter First Name!", LENGTH_SHORT).show();
            return;
        }
        if(lastName.isEmpty())
        {
            makeText(getApplicationContext(), "Enter Last Name!", LENGTH_SHORT).show();
            return;
        }

        if(userEmail.isEmpty())
        {
            makeText(getApplicationContext(), "Enter email address!", LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty())
        {
            makeText(getApplicationContext(), "Enter Password!", LENGTH_SHORT).show();
            return;
        }

        if(phone.isEmpty())
        {
            makeText(getApplicationContext(), "Enter Phone Number!", LENGTH_SHORT).show();
            return;
        }
        if(addr1.isEmpty())
        {
            makeText(getApplicationContext(), "Enter address!", LENGTH_SHORT).show();
            return;
        }
        if(postal_code.isEmpty())
        {
            makeText(getApplicationContext(), "Enter Postal code!", LENGTH_SHORT).show();
            return;
        }

        if(state_value.isEmpty())
        {
            makeText(getApplicationContext(), "Enter state of residence!", LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
        {
            makeText(getApplicationContext(), "Enter valid email format!", LENGTH_SHORT).show();
            return;
        }

        if(phone.length() !=10)
        {
            makeText(getApplicationContext(), "Enter 10-digit phone number!", LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(userEmail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    String id = databaseUsers.push().getKey();
                    User user = new User(id,
                            firstName,
                            lastName,
                            password,
                            userEmail,
                            phone,
                            addr1,
                            postal_code,
                            state_value

                    ) ;
                    databaseUsers.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "RegistrationSuccess", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Registration Failure", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


//                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    progressBar.setVisibility(View.GONE);
//                                    if(task.isSuccessful()){
//                                        Toast.makeText(getApplicationContext(), "Registration success!", Toast.LENGTH_SHORT).show();
//                                    }
//                                    else
//                                    {
//                                        Toast.makeText(getApplicationContext(), "Registration Failure!", Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }
//                            });
                }
                else {
                    makeText(getApplicationContext(), task.getException().getMessage(), LENGTH_LONG).show();
                }
            }
        });
    }


//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId())
//        {
//        case R.id.btn_sign_up:
//            registerUser();
//            break;
//        }
//
//
//    }
}
//----------------------------------------------------------------------------------------------------------------------------
//
//public class SignupActivity extends AppCompatActivity {
//
//    private EditText txt_fname, txt_lname, txt_email, txt_password, txt_addrl1, txt_state, txt_pc, txt_phno;
//    private Button btnSignUp, btnSignIn;
//    private ProgressBar progressBar;
//    private FirebaseAuth auth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//        //Get Firebase auth instance
//        auth = FirebaseAuth.getInstance();
//
//        btnSignIn = (Button) findViewById(R.id.sign_in_button);
//        btnSignUp = (Button) findViewById(R.id.sign_up_button);
//        txt_fname = (EditText) findViewById(R.id.txt_fname);
//        txt_lname = (EditText) findViewById(R.id.txt_lname);
//        txt_email = (EditText) findViewById(R.id.email);
//        txt_password = (EditText) findViewById(R.id.txt_pwd);
//        txt_addrl1 = (EditText) findViewById(R.id.txt_addrl1);
//        txt_pc = (EditText) findViewById(R.id.txt_pc);
//        txt_phno = (EditText) findViewById(R.id.txt_ph);
//        txt_state = (EditText) findViewById(R.id.txt_state);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
////        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
//
////        btnResetPassword.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
////            }
////        });
//
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                switch (v.getId()) {
//                    case R.id.btn_sign_up:
//                        registerUser();
//                        break;
//                }
//
//            }
//        });
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        progressBar.setVisibility(View.GONE);
//    }
//
//
//
//}
//
//    public void registerUser() {
//        final String fname = txt_fname.getText().toString().trim();
//        final String lname = txt_lname.getText().toString().trim();
//        final String email = txt_email.getText().toString().trim();
//        final String password = txt_password.getText().toString().trim();
//        final String addrl1 = txt_addrl1.getText().toString().trim();
//        final String postal = txt_pc.getText().toString().trim();
//        final String state = txt_state.getText().toString().trim();
//        final String phno = txt_phno.getText().toString().trim();
//
//        if (TextUtils.isEmpty(fname)) {
//            Toast.makeText(getApplicationContext(), "Enter First Name!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(lname)) {
//            Toast.makeText(getApplicationContext(), "Enter Last Name!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(email)) {
//            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(password)) {
//            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(addrl1)) {
//            Toast.makeText(getApplicationContext(), "Enter address!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(postal)) {
//            Toast.makeText(getApplicationContext(), "Enter postal code!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(state)) {
//            Toast.makeText(getApplicationContext(), "Enter State!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(phno)) {
//            Toast.makeText(getApplicationContext(), "Enter Phone Number!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (password.length() < 6) {
//            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//        progressBar.setVisibility(View.VISIBLE);
//        //create user
//        auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//
//                        if (task.isSuccessful()) {
//                            User user = new User(
//                                    fname,
//                                    lname,
//                                    password,
//                                    email,
//                                    phno,
//                                    addrl1,
//                                    postal,
//                                    state
//
//                            );
//
//                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            progressBar.setVisibility(View.GONE);
//                                            if (task.isSuccessful()) {
//                                                Toast.makeText(getApplicationContext(), "Registration success!", Toast.LENGTH_SHORT).show();
//                                            } else {
//                                                Toast.makeText(getApplicationContext(), "Registration Failure!", Toast.LENGTH_SHORT).show();
//
//                                            }
//                                        }
//                                    });
//                        }
//
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
//                            finish();
//                        }
//                    }
//                });
//
//    }
