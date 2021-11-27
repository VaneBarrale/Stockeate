package com.stockeate.stockeate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stockeate.stockeate.R;
import com.stockeate.stockeate.clases.class_usuarios;

import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;
import java.util.Map;

public class Activity_Registrar extends AppCompatActivity {

    private class_usuarios userRegistered;
    private EditText et_email, et_password, et_nombre, et_confirm_password;
    private Button btn_registrar, btn_volver;
    private ImageView btn_gmail;
    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private static int RC_SIGN_IN = 1;
    String URL_SERVIDOR = "https://stockeateapp.com.ar/api/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.editTextTextEmailAddress, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this, R.id.login_password_editText, ".{6,}", R.string.invalid_password);


        this.btn_gmail = findViewById(R.id.btn_Login_gmail);
        this.btn_volver = findViewById(R.id.btnVolver);
        this.et_email = findViewById(R.id.editTextTextEmailAddress);
        this.et_password = findViewById(R.id.etxtpassword);
        this.et_confirm_password = findViewById(R.id.etxtconfirmpassword);
        this.et_nombre = findViewById(R.id.et_Nombre);
        this.btn_registrar = findViewById(R.id.btnRegistrar);

        setUpGoogleLogin();

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Registrar.this, MainActivity.class);
                startActivity(i);
            }
        });

        btn_gmail.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                loginGoogle();
            }
        });

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();

                /*comentado el 17/11
                Log.d("Entro al registrar", "Entro al registrar");

                String nombre = et_nombre.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                String password_confirmation = et_confirm_password.getText().toString();

                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Entro al reg Response", "Entro al registrar Response" + response);


                        try {
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok = jsonRespuesta.getBoolean("success");

                            Log.d("Entro al try", "Entro al registrar Response" + ok);

                            if (ok == true) {
                                Intent login = new Intent(Activity_Registrar.this, MainActivity.class);
                                startActivity(login);
                                Activity_Registrar.this.finish();
                            } else{
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Activity_Registrar.this);
                                alerta.setMessage("Fallo el registro").setNegativeButton("Reintentar", null).create().show();
                            }
                        }catch (JSONException e){
                            e.getMessage();
                            Log.d("Entro al reg catch", "Entro al registrar Response" + e);
                        }
                    }
                };

                class_registrar registrar = new class_registrar(nombre, email, password, password_confirmation, respuesta);
                RequestQueue cola = Volley.newRequestQueue(Activity_Registrar.this);
                cola.add(registrar); */

                /*Para firebase
                if (awesomeValidation.validate()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Activity_Registrar.this, "Usuario creado con exito", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                dameToastdeerror(errorCode);
                            }
                        }
                    });
                } else {
                    Toast.makeText(Activity_Registrar.this, "Completa todos los datos!", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    private void setUpGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1009564426156-bi6nm88hg48noio7g04mpeudl4lsj9us.apps.googleusercontent.com")
                .requestEmail()
                .build();
        this.googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void loginGoogle() {
        Intent signInIntent = this.googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            Log.d("Aca", "Por aca paso - onActivity " + task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        Log.d("Aca", "handleSignInResult:" + completedTask.isSuccessful());
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            Log.d("Aca", "Por aca paso - catch");
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(Activity_Registrar.this, "Login Failed "+e.getStatusCode(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d("Aca FirebaseAuthGoogle", "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), "null");
        this.firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String logedEmail = firebaseAuth.getCurrentUser().getEmail();
                    userRegistered(acct);
                }
                else {
                // If sign in fails, display a message to the user.
                Toast.makeText(Activity_Registrar.this, "Login Failed "+task.getException().getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e("Signup Error", "onCancelled", task.getException());
                }}
            });
    }

    private void signUp(String userEmail){
        userRegistered = new class_usuarios(userEmail);
        userRegistered.setMail(userEmail);

        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference("stockeate-3d9e6-default-rtdb/User");
        String key = firebaseReference.push().getKey();
        userRegistered.setId(key);
        firebaseReference.child(key).setValue(userRegistered);
            Intent login = new Intent(Activity_Registrar.this, Activity_Menu.class);
            startActivity(login);
    }

    private void userRegistered(final GoogleSignInAccount acct) {

        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("stockeate-3d9e6-default-rtdb/Usuarios");
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("mail").getValue().toString().equals(firebaseAuth.getCurrentUser().getEmail())) {
                        userRegistered = new class_usuarios(acct.getEmail());
                        userRegistered.setId(data.getKey());
                        userRegistered.setMail(data.child("mail").getValue().toString());
                    }
                }

                if (userRegistered != null) {
                    Intent mainMenu = new Intent(Activity_Registrar.this, Activity_Menu.class);
                    startActivity(mainMenu);

                } else {
                    signUp(firebaseAuth.getCurrentUser().getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Activity_Registrar.this, "User Error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dameToastdeerror(@NotNull String error) {

        switch (error) {
            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(Activity_Registrar.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(Activity_Registrar.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(Activity_Registrar.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(Activity_Registrar.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                et_email.setError("La dirección de correo electrónico está mal formateada.");
                et_email.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(Activity_Registrar.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                et_password.setError("la contraseña es incorrecta ");
                et_password.requestFocus();
                et_password.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(Activity_Registrar.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(Activity_Registrar.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(Activity_Registrar.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(Activity_Registrar.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                et_email.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                et_email.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(Activity_Registrar.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(Activity_Registrar.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(Activity_Registrar.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(Activity_Registrar.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(Activity_Registrar.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(Activity_Registrar.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(Activity_Registrar.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                et_password.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                et_password.requestFocus();
                break;
        }
    }

    public void registrar() {

        Log.d("Entro al registrar", "Entro al Registrar");
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, URL_SERVIDOR,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.d("Entro al registrar", "Entro al Registrar RESPONSE" + response);
                        // En este apartado se programa lo que deseamos hacer en caso de no haber errores

                        if(et_email.getText().toString().isEmpty() || et_password.getText().toString().isEmpty() || et_confirm_password.getText().toString().isEmpty() || et_nombre.getText().toString().isEmpty()) {
                            Toast.makeText(Activity_Registrar.this, "Se deben de llenar todos los campos.", Toast.LENGTH_SHORT).show();
                        } /*else if(et_password.getText().toString() != et_confirm_password.getText().toString()) {
                            Toast.makeText(Activity_Registrar.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        } */else {
                            Toast.makeText(Activity_Registrar.this, "Registro exitoso.", Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de tener algun error en la obtencion de los datos
                Toast.makeText(Activity_Registrar.this, "ERROR CON LA CONEXION", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // En este metodo se hace el envio de valores de la aplicacion al servidor
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("name", et_nombre.getText().toString().trim());
                parametros.put("email", et_email.getText().toString().trim());
                parametros.put("password", et_password.getText().toString().trim());
                parametros.put("password_confirmation", et_confirm_password.getText().toString().trim());
                parametros.put("device_name", "mobile");

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Activity_Registrar.this);
        requestQueue.add(stringRequest);
    }
}