package com.example.feedthecatapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.feedthecatapp.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SplashScreen extends AppCompatActivity {

    Animation zoom;
    Animation authBtnMove;
    Animation regBtnMove;

    ImageView circle;
    Button authButton;
    Button regButton;

    ConstraintLayout root;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        zoom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom);
        authBtnMove = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.auth_button_anim);
        regBtnMove = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.register_button_anim);

        circle= findViewById(R.id.circle);
        circle.startAnimation(zoom);

        authButton = findViewById(R.id.authButton);
        authButton.startAnimation(authBtnMove);

        regButton = findViewById(R.id.regButton);
        regButton.startAnimation(regBtnMove);

        root = findViewById(R.id.rootElement);

        FirebaseApp.initializeApp(this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterWindow();
            }
        });

        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAuthorizationWindow();
            }
        });

    }

    private void showAuthorizationWindow(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Authorization");

        LayoutInflater inflater = LayoutInflater.from(this);
        View authorizationWnd = inflater.inflate(R.layout.authorization_window,null);
        dialog.setView(authorizationWnd);

        EditText email = authorizationWnd.findViewById(R.id.emailFieldAuth);
        EditText password = authorizationWnd.findViewById(R.id.passwordFieldAuth);

        dialog.setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Email field is empty!", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(password.getText().toString().length() < 5){
                    Snackbar.make(root, "Password length must be more than 5 characters!", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root,"Sign In failed. " + e.getMessage(), Snackbar.LENGTH_SHORT);
                    }
                });


            }
        });


        dialog.show();
    }

    private void showRegisterWindow(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Register");

        LayoutInflater inflater = LayoutInflater.from(this);
        View registerWnd = inflater.inflate(R.layout.register_window,null);
        dialog.setView(registerWnd);

        EditText email = registerWnd.findViewById(R.id.emailFieldReg);
        EditText password = registerWnd.findViewById(R.id.passwordFieldReg);
        EditText name = registerWnd.findViewById(R.id.nameField);

        dialog.setPositiveButton("Sign up", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Email field is empty!", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name.getText().toString())){
                    Snackbar.make(root, "Name field is empty!", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(password.getText().toString().length() < 6){
                    Snackbar.make(root, "Password length must be more than 5 characters!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User();
                        System.out.print("user created");
                        user.setEmail(email.getText().toString());
                        user.setName(name.getText().toString());
                        user.setPassword(password.getText().toString());

                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Snackbar.make(root, "You have successfully registered", Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });


        dialog.show();

    }
}