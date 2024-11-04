package com.example.ventz;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class LoginTela extends AppCompatActivity {

    private RequestQueue requestQueue;

    public static final String CHANNEL_1_ID = "channel1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login_tela);

        requestQueue = Volley.newRequestQueue(this);


        Button botao = findViewById(R.id.btnVoltar);
        Button btnLogin = findViewById(R.id.btnCadastrar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return false;
    }
}