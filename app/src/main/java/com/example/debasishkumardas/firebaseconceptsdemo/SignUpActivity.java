package com.example.debasishkumardas.firebaseconceptsdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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


import com.example.debasishkumardas.firebaseconceptsdemo.model.UserModel;
import com.example.debasishkumardas.firebaseconceptsdemo.utils.Constants;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpActivity extends AppCompatActivity {


    @InjectView(R.id.input_name)
    EditText etName;
    @InjectView(R.id.input_email)
    EditText etEmail;
    @InjectView(R.id.input_password)
    EditText etPassword;
    @InjectView(R.id.input_hobby)
    EditText ethobby;
    @InjectView(R.id.input_about_me)
    EditText etAboutMe;
    @InjectView(R.id.btn_signup)
    Button btnSignUp;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    Activity signupActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        signupActivity = this;
        progressDialog = new ProgressDialog(this);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }


    private void registerUser(){

        //getting email and password from edit texts
        final String name = etName.getText().toString().trim();
        final String email  = etEmail.getText().toString().trim();
        final String password  = etPassword.getText().toString().trim();
        final String hobby  = ethobby.getText().toString().trim();
        final String aboutme  = etAboutMe.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter name",Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(hobby)){
            Toast.makeText(this,"Please enter hobby",Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(aboutme)){
            Toast.makeText(this,"Please enter about you",Toast.LENGTH_LONG).show();
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
                            Toast.makeText(signupActivity,"Successfully registered",Toast.LENGTH_LONG).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String Uid = null;

                            if(user != null){
                                Uid = user.getUid();
                                UserModel userModel = new UserModel();
                                userModel.setUserId(Uid);
                                userModel.setUserName(name);
                                userModel.setEmail(email);
                                userModel.setHobby(hobby);
                                userModel.setAboutme(aboutme);
                                Log.d("User id", Uid);

                                storeUser(Uid, userModel);
                            }

                            SharedPreferences preferences = PreferenceManager
                                    .getDefaultSharedPreferences(SignUpActivity.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("UserId", Uid);
                            editor.commit();

                            clearData();
                        }else{
                            //display some message here
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(signupActivity,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(signupActivity,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(signupActivity,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                            } catch(Exception e) {
                                Toast.makeText(signupActivity,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    /**
     * Storing User Data in the UserTable
     * @param userId
     * @param userModel
     */
    public void storeUser(String userId, UserModel userModel){
        Firebase.setAndroidContext(SignUpActivity.this);
        Firebase postRef = new Firebase(Constants.BASE_URL_FIREBASE);
        postRef = postRef.child("Users").child(userId);
        postRef.setValue(userModel);
        progressDialog.dismiss();
        redirectUserToHomeScreen();
    }

    public void redirectUserToHomeScreen(){
        startActivity(new Intent(SignUpActivity.this, HomeScreen.class));
    }

    public void clearData(){
        etName.setText(null);
        etEmail.setText(null);
        etPassword.setText(null);
        ethobby.setText(null);
        etAboutMe.setText(null);
    }
}
