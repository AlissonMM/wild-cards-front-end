package com.example.ventz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginTela extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_tela);

        Button botao = findViewById(R.id.btnVoltar);
        Button btnLogin = findViewById(R.id.btnLogin);

        botao.setOnClickListener(v -> {
            Intent intent = new Intent(this, CadastroTela.class);
            // Feche activity atual
            startActivity(intent);
            finish();
        });

        btnLogin.setOnClickListener(v -> {
           // add text box pop up
            Toast.makeText(getApplicationContext(),"login", Toast.LENGTH_SHORT).show();
            // fazer login
            Intent intent = new Intent(this, MainActivity.class);
            // Feche activity atual
            startActivity(intent);
            finish();
        });

    }
}