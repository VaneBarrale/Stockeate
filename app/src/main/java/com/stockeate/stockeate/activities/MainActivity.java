package com.stockeate.stockeate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private Button btn_login;
    private TextView tv_registraraqui, tv_recuperar;
    private EditText et_email,et_password;
    private CheckBox recordarme;
    private ImageView ic_loginGoogle;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String URL_SERVIDOR = "https://stockeateapp.com.ar/api/login";

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
        this.tv_recuperar = findViewById(R.id.txtOlvidasteTuContrase??a);
        this.ic_loginGoogle = findViewById(R.id.ic_googleLogin);


        preferences = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        this.account = GoogleSignIn.getLastSignedInAccount(this);

        //Log.w("signedInAccountFromIntent", signedInAccountFromIntent.toString());
        /*
        if (this.account != null){
            googleLogin();
        }
        */


        if (revisarRecordar()){
            et_email.setText(preferences.getString("Email",""));
            et_password.setText(preferences.getString("Pass",""));
            recordarme.setChecked(true);

            /*cambiar de lado esto*/
            /*Intent i = new Intent(MainActivity.this, Activity_Menu.class);
            startActivity(i);*/
        }

        if (revisarSesion()){
            firebaseAuth = FirebaseAuth.getInstance();
            if (et_email.getText().toString().isEmpty() || et_password.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "eMail o Contrase??a invalido", Toast.LENGTH_SHORT).show();
            }else {
                firebaseAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    Toast.makeText(MainActivity.this, "Se ha iniciado sesi??n", Toast.LENGTH_SHORT).show();
                                    Log.w("Login Success", "signInWithEmail:Success", task.getException());

                                    guardarSesion(recordarme.isChecked());
                                    Intent login = new Intent(MainActivity.this, Activity_Menu.class);
                                    startActivity(login);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Login Failed", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "eMail o Contrase??a invalido", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }


        if(revisarSesionGoogle()){
            googleLogin();
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
                login();
                /*firebaseAuth = FirebaseAuth.getInstance();
                if (et_email.getText().toString().isEmpty() || et_password.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "eMail o Contrase??a invalido", Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                        Toast.makeText(MainActivity.this, "Se ha iniciado sesi??n", Toast.LENGTH_SHORT).show();
                                        Log.w("Login Success", "signInWithEmail:Success", task.getException());

                                        guardarSesion(recordarme.isChecked());
                                        Intent login = new Intent(MainActivity.this, Activity_Menu.class);
                                        startActivity(login);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("Login Failed", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "eMail o Contrase??a invalido", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }*/
            }
        });

        ic_loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleLogin();
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
            Toast.makeText(MainActivity.this, "Se ha iniciado sesi??n", Toast.LENGTH_SHORT).show();
            Log.w("Login Success", "signInWithGoogle:Success");
            editor.putBoolean("SesionGoogle", true);
            editor.commit();
            Intent login = new Intent(MainActivity.this, Activity_Menu.class);
            //login.putExtra("GoogleSignInClient", mGoogleSignInClient.toString());
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
            Toast.makeText(this, "Se ha iniciado sesi??n", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "El usuario no se encuentra logueado", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarSesion(boolean checked){
        if (checked){

            editor.putString("Email",et_email.getText().toString());
            editor.putString("Pass",et_password.getText().toString());
            editor.putBoolean("RememberMe", checked);
        }
        editor.putBoolean("Sesion", checked);
        editor.commit();
    }

    private boolean revisarSesion(){
        return this.preferences.getBoolean("Sesion", false);
    }

    private boolean revisarRecordar(){
        return this.preferences.getBoolean("RememberMe", false);
    }

    private boolean revisarSesionGoogle(){
        return this.preferences.getBoolean("SesionGoogle", false);
    }

    public void login() {
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, URL_SERVIDOR, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    // En este apartado se programa lo que deseamos hacer en caso de no haber errores

                    Log.d("Entro al login", "Entro al Login " + response);
                    if(et_email.getText().toString().isEmpty() || et_password.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Se deben de llenar todos los campos.", Toast.LENGTH_SHORT).show();
                        Log.d("Entro al login", "Entro al Login if " + response);
                    } else if(response.equals("The provided credentials are incorrect.")){
                        Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(MainActivity.this, "Inicio de Sesion exitoso.", Toast.LENGTH_LONG).show();
                        Intent login = new Intent(MainActivity.this, Activity_Menu.class);
                        startActivity(login); }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de tener algun error en la obtencion de los datos
                Toast.makeText(MainActivity.this, "ERROR AL INICIAR SESION", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // En este metodo se hace el envio de valores de la aplicacion al servidor
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("email", et_email.getText().toString().trim());
                parametros.put("password", et_password.getText().toString().trim());
                parametros.put("device_name", "mobile");

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}