package com.stockeate.stockeate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Class_User userRegistered;
    private FirebaseAuth auth;
    private Button loginButton;
    private ImageButton googleLoginButton;
    private TextView registerHere;
    private TextView email;
    private TextView password;
    private GoogleSignInClient googleSignInClient;
    private static int RC_SIGN_IN = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}