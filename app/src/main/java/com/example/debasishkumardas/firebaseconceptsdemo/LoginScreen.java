package com.example.debasishkumardas.firebaseconceptsdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.debasishkumardas.firebaseconceptsdemo.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen
        extends AppCompatActivity
        implements View.OnClickListener{

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup, buttonReg;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    String loggedinUserId;
    SharedPreferences srh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        srh = PreferenceManager.getDefaultSharedPreferences(LoginScreen.this);
        loggedinUserId = srh.getString("UserId", null);

        if(!TextUtils.isEmpty(loggedinUserId) && loggedinUserId != null){
            redirectUserToHomeScreen();
        }

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignup = (Button) findViewById(R.id.buttonLogin);
        buttonSignup.setText("Login");
        buttonReg = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);


        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, SignUpActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        //calling register method on click

        if(buttonSignup.getText().toString().equalsIgnoreCase("SIGNUP")){
            //registerUser();
            startActivity(new Intent(LoginScreen.this, SignUpActivity.class));
        }else{
            letUserIn();
        }
    }


    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(LoginScreen.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(LoginScreen.this,"Successfully Loggedin",Toast.LENGTH_LONG).show();

                            if(user != null){
                                String email = user.getEmail();
                                String Uid = user.getUid();
                                Log.d("User id", Uid);
                            }
                           // buttonSignup.setText("Login");
                            //clearData();
                        }else{
                            //display some message here
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(LoginScreen.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(LoginScreen.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(LoginScreen.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                            } catch(Exception e) {
                                Toast.makeText(LoginScreen.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    public void letUserIn(){
        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(LoginScreen.this,"Successfully Loggedin",Toast.LENGTH_LONG).show();
                            String Uid = null;
                            if(user != null){
                                String email = user.getEmail();
                                Uid = user.getUid();
                                SharedPreferences preferences = PreferenceManager
                                        .getDefaultSharedPreferences(LoginScreen.this);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("UserId", Uid);
                                editor.commit();
                            }

                            clearData();
                            redirectUserToHomeScreen();
                           // getUserData(Uid);

                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }
                        progressDialog.dismiss();

                        // ...
                    }
                });
    }

    public void redirectUserToHomeScreen(){
        startActivity(new Intent(LoginScreen.this, HomeScreen.class));
    }


    /**
     * CLearing the user logged in data
     */
    public void clearData(){
        editTextEmail.setText(null);
        editTextPassword.setText(null);
    }

    public void getUserData(final String uId){
        //Initializing the firebase instance
        Firebase.setAndroidContext(LoginScreen.this);
        Firebase postRef = new Firebase(Constants.BASE_URL_FIREBASE);

        //Initializing the child
        postRef = postRef.child(Constants.CHILD_NODE_USER).child(uId);
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Logic for success is here...
                String userEmail = (String) dataSnapshot.child("email").getValue();
                String userAboutMe = (String) dataSnapshot.child("aboutme").getValue();
                String userHobby = (String) dataSnapshot.child("hobby").getValue();
                String userUserName = (String) dataSnapshot.child("userName").getValue();


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }


        });
    }
}
