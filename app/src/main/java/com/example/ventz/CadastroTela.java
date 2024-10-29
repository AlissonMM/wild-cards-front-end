package com.example.ventz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CadastroTela extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_tela);

        Button btnVoltar = findViewById(R.id.btnVoltar);

        // Add event listener to btnVoltar to return to LoginTela
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginTela.class);
            // Feche activity atual
            startActivity(intent);
            finish();
        });


    }
}