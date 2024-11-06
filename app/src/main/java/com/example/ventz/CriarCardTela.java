package com.example.ventz;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventz.model.Dados;

import org.json.JSONException;
import org.json.JSONObject;

public class CriarCardTela extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_criar_card_tela);


        Button btnAdicionar = findViewById(R.id.btnAdicionar);
        Button btnVoltar = findViewById(R.id.btnVoltarCriarCard);
        EditText txtPergunta = findViewById(R.id.txtPergunta);
        EditText txResposta = findViewById(R.id.txtResposta);




        // Obter ID do deck atual (o que foi clicado na listView
               int idDeckAtual = Dados.getInstance().getIdDeckAtual();




        btnAdicionar.setOnClickListener(v -> {
            // Salvar informações do card no deck

            String pergunta = txtPergunta.getText().toString();
            String resposta = txResposta.getText().toString();
// AQUI AQUI AQUI AQUI
           inserirCardNoDeck(pergunta, resposta, idDeckAtual);



//             Toast.makeText(this, "Carta adicionada!", Toast.LENGTH_SHORT).show();
        });

        btnVoltar.setOnClickListener(v -> {
            finish();
        });
    }

    // Função para adicionar um card ao deck
    private void inserirCardNoDeck(String pergunta, String resposta, int idDeckFk) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // URL base para a requisição
        String urlInserirCard = Dados.getInstance().getUrl() + "/cards/inserirCard";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("pergunta", pergunta);
            jsonBody.put("resposta", resposta);
            jsonBody.put("idDeckFk", idDeckFk); // ID do deck ao qual o card será associado
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest requestInserirCard = new JsonObjectRequest(
                Request.Method.POST, // Utilizando o método POST
                urlInserirCard,
                jsonBody,
                response -> Toast.makeText(this, "Response inutil 🤓!", Toast.LENGTH_SHORT).show(),
                error -> {
                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        if (statusCode == 404) {
                            Toast.makeText(this, "Deck não encontrado.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Erro ao inserir card no deck. Código: " + statusCode, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Card inserido no deck com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(requestInserirCard);
    }
}