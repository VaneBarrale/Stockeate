package com.stockeate.stockeate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private TextView tv_registraraqui, tv_recuperar;
    private EditText et_email, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.et_email = findViewById(R.id.login_email_editText);
        this.et_password = findViewById(R.id.login_password_editText);

        this.btn_login = findViewById(R.id.btnIngresar);
        this.tv_registraraqui = findViewById(R.id.textRegistrate);
        this.tv_recuperar = findViewById(R.id.txtOlvidasteTuContraseña);

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
                Intent login = new Intent(MainActivity.this, Activity_Menu.class);
                startActivity(login);
            }
        });
    }
}