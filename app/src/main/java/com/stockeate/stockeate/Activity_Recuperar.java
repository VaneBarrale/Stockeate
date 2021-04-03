package com.stockeate.stockeate;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.nio.file.Files;

public class Activity_Recuperar extends AppCompatActivity{

    private EditText et_email, et_password;
    private Button btn_recuperar;
    private TextView txt_volver;

    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.etxtMail, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this, R.id.etxtpassword, ".{6,}", R.string.invalid_password);

        et_email = findViewById(R.id.etxtMail);
        et_password = findViewById(R.id.etxtpassword);
        btn_recuperar = findViewById(R.id.btnRecuperar);
        txt_volver = findViewById(R.id.txtVolver);

        btn_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String pass = et_password.getText().toString();

                if (awesomeValidation.validate())
                {
                    firebaseAuth.confirmPasswordReset(email, pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Activity_Recuperar.this, "Listo! tu contraseña se actualizo. Ya podes volver a ingresar!", Toast.LENGTH_SHORT).show();
                                Intent regresar = new Intent(Activity_Recuperar.this, MainActivity.class);
                                startActivity(regresar);
                                finish();
                            } else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                dameToastdeerror(errorCode);
                            }
                        }
                    });
                }else{
                    Toast.makeText(Activity_Recuperar.this, "Ingresa una contraseña!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(Activity_Recuperar.this, MainActivity.class);
                startActivity(volver);
                finish();
            }
        });
    }

    private void dameToastdeerror(String error) {

        switch (error) {
            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(Activity_Recuperar.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(Activity_Recuperar.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(Activity_Recuperar.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(Activity_Recuperar.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                et_email.setError("La dirección de correo electrónico está mal formateada.");
                et_email.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(Activity_Recuperar.this, "La contraseña no es válida", Toast.LENGTH_LONG).show();
                et_password.setError("la contraseña es incorrecta ");
                et_password.requestFocus();
                et_password.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(Activity_Recuperar.this, "El mail no corresponden a un usuario logueado", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(Activity_Recuperar.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(Activity_Recuperar.this, "No hay ningún usuario con ese mail", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(Activity_Recuperar.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(Activity_Recuperar.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                et_password.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                et_password.requestFocus();
                break;
        }
    }

}
