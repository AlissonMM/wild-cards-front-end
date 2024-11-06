package com.example.ventz;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventz.model.Dados;

import org.json.JSONException;
import org.json.JSONObject;

public class CadastroTela extends AppCompatActivity {
    private String url;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_cadastro_tela);

        Button btnVoltar = findViewById(R.id.btnTelaCadastro);
        Button btnCadastrar = findViewById(R.id.btnLogin);
        EditText txtNome = findViewById(R.id.txtNome);
        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtSenha = findViewById(R.id.txtSenha);

        // Configura o RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Define a URL base do servidor

        url = Dados.getInstance().getUrl() + "/usuarios/inserirUsuario";

        // Evento de clique para o botão "Voltar"
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginTela.class);
            startActivity(intent);
            finish();
        });

        // Evento de clique para o botão "Cadastrar"
        btnCadastrar.setOnClickListener(v -> {
            // Verifica se algum dos campos está vazio
            if (txtNome.getText().toString().isEmpty() ||
                txtEmail.getText().toString().isEmpty() ||
                txtSenha.getText().toString().isEmpty()) {

                // Mostra uma mensagem de erro para o usuário
                Toast.makeText(CadastroTela.this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return; // Interrompe a execução se algum campo estiver vazio
            }

            // Cria o corpo JSON apenas se todos os campos estiverem preenchidos
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("nome", txtNome.getText().toString());
                jsonBody.put("email", txtEmail.getText().toString());
                jsonBody.put("senha", txtSenha.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Cria a requisição JsonObjectRequest
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonBody,
                    response -> {

                    },
                    error -> {
                        // Por algum motivo o codigo so funciona assim 🤓👍
                        if (error.networkResponse != null) {
                            String errorMessage = new String(error.networkResponse.data);
                            Toast.makeText(CadastroTela.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CadastroTela.this, "Usuário Cadastrado!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CadastroTela.this, LoginTela.class);
                            startActivity(intent);
                            finish();
                        }
                    }
            );

            // Adiciona a requisição à fila
            requestQueue.add(request);
        });
    }
}
