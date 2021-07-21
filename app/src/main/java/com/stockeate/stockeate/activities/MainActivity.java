package com.stockeate.stockeate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.stockeate.stockeate.R;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity{

    private Button btn_login;
    private TextView tv_registraraqui, tv_recuperar;
    private EditText et_email,et_password;
    private CheckBox recordarme;
    private ImageView ic_loginGoogle,ic_mostrarPass;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.et_email = findViewById(R.id.login_email_editText);
        this.et_password = findViewById(R.id.login_password_editText);
        this.btn_login = findViewById(R.id.btnIngresar);
        this.recordarme = findViewById(R.id.chkRecordarme);
        this.tv_registraraqui = findViewById(R.id.textRegistrate);
        this.tv_recuperar = findViewById(R.id.txtOlvidasteTuContraseña);
        this.ic_loginGoogle = findViewById(R.id.ic_googleLogin);
        this.ic_mostrarPass = findViewById(R.id.show_pass_btn);

        preferences = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        this.account = GoogleSignIn.getLastSignedInAccount(this);

        if (revisarSesion()){
            et_email.setText(preferences.getString("Email",""));
            et_password.setText(preferences.getString("Pass",""));
            recordarme.setChecked(true);

            /*cambiar de lado esto*/
            /*Intent i = new Intent(MainActivity.this, Activity_Menu.class);
            startActivity(i);*/
        }

        tv_registraraqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Activity_Registrar.class);
                startActivity(i);
            }
        });

        tv_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Activity_Recuperar.class);
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth = FirebaseAuth.getInstance();
                if (et_email.getText().toString().isEmpty() || et_password.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "eMail o Contraseña invalido", Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                        Toast.makeText(MainActivity.this, "Se ha iniciado sesión", Toast.LENGTH_SHORT).show();
                                        Log.w("Login Success", "signInWithEmail:Success", task.getException());

                                        guardarSesion(recordarme.isChecked());
                                        Intent login = new Intent(MainActivity.this, Activity_Menu.class);
                                        startActivity(login);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("Login Failed", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "eMail o Contraseña invalido", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                /*verificacionInicioSesion();*/


                /*if (recordarme.isChecked()){
                    editor.putString("Email",et_email.getText().toString());
                    editor.putString("Pass",et_password.getText().toString());
                    editor.apply();
                }*/
                /*guardarSesion(recordarme.isChecked());*/

                //cambiar esto y ponerlo dentro del if de verificacionInicioSesion()
            }
        });

        ic_loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleLogin();
            }
        });

        ic_mostrarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHidePass(ic_mostrarPass);
            }
        });
    }

    private void googleLogin(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 9001);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 9001) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            this.account = completedTask.getResult(ApiException.class);
            Toast.makeText(MainActivity.this, "Se ha iniciado sesión", Toast.LENGTH_SHORT).show();
            Log.w("Login Success", "signInWithGoogle:Success");

            Intent login = new Intent(MainActivity.this, Activity_Menu.class);
            startActivity(login);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Login Failed", "signInWithGoogle:failure");
            Log.w("Login Failed", e.toString());
            Toast.makeText(MainActivity.this, "El usuario no se encuentra logueado", Toast.LENGTH_SHORT).show();

        }
    }

    private void verificacionInicioSesion(){
        if(firebaseUser != null)
        {
            Toast.makeText(this, "Se ha iniciado sesión", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "El usuario no se encuentra logueado", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarSesion(boolean checked){
        if (checked){

            editor.putString("Email",et_email.getText().toString());
            editor.putString("Pass",et_password.getText().toString());

        }
        editor.putBoolean("Sesion", checked);
        editor.apply();
    }

    private boolean revisarSesion(){
        return this.preferences.getBoolean("Sesion", false);
    }

    private void ShowHidePass(View view){

        if(view.getId()==R.id.show_pass_btn){

            if(et_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                //((ImageView(view)).setImageResource(R.drawable.hide_password);

                //Show Password
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                //((ImageView)(view)).setImageResource(R.drawable.show_password);

                //Hide Password
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
}