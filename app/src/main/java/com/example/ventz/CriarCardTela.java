package com.example.ventz;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CriarCardTela extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_criar_card_tela);

        // Select a button
        Button btnAdicionar = findViewById(R.id.btnAdicionar);
        Button btnVoltar = findViewById(R.id.btnVoltarCriarCard);


        btnAdicionar.setOnClickListener(v -> {
            Toast.makeText(this, "Carta adicionada!", Toast.LENGTH_SHORT).show();
        });

        btnVoltar.setOnClickListener(v -> {
            finish();
        });
    }
}