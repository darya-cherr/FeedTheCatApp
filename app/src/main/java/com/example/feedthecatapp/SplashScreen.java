package com.example.feedthecatapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.feedthecatapp.Models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
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

            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();*/
            }
        }, 4000);
    }

    private void showRegisterWindow(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Register");

        LayoutInflater inflater = LayoutInflater.from(this);
        View registerWnd = inflater.inflate(R.layout.register_window,null);
        dialog.setView(registerWnd);

        EditText email = registerWnd.findViewById(R.id.emailField);
        EditText password = registerWnd.findViewById(R.id.passwordField);

        dialog.setPositiveButton("Sign up", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Login field is empty!", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(password.getText().toString().length() < 5){
                    Snackbar.make(root, "Password length must be more than 5 characters!", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User();
                        user.setEmail(email.getText().toString());
                        user.setPassword(password.getText().toString());

                        users.child(user.getEmail()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Snackbar.make(root, "You have successfully registered", Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });


        dialog.show();

    }
}