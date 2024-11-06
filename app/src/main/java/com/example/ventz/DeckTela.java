package com.example.ventz;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

//import   androidx.activity.EdgeToEdge;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventz.model.Card;

import com.example.ventz.model.CardAdapterFront;
import com.example.ventz.model.Dados;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class DeckTela extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_deck_tela);

        ListView listViewCard = findViewById(R.id.listViewCard);

        // List of cards to display (initially empty)
        List<Card> cardsList = new ArrayList<>();

        // Configurar o adaptador e associá-lo ao ListView
        CardAdapterFront adapter = new CardAdapterFront(this, cardsList);
        listViewCard.setAdapter(adapter);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String urlBuscarCards = Dados.getInstance().getUrl() + "/cards/buscarTodos";

//        StringRequest requestBuscarCards = new StringRequest(
//                Request.Method.GET,
//                urlBuscarCards,
//                response -> {
//                    try {
//                        // Use Jackson ObjectMapper to parse JSON
//                        ObjectMapper mapper = new ObjectMapper();
//                        List<Card> cards = mapper.readValue(response, new TypeReference<List<Card>>() {});
//
//                        // Filter cards by idDeck (assuming Dados.getInstance().getIdDeckAtual() is your current deck ID)
//                        List<Card> filteredCards = cards.stream()
//                                .filter(card -> card.getIdDeckFk().getIdDeck() == Dados.getInstance().getIdDeckAtual())
//                                .collect(Collectors.toList());
//
//                        // Update the adapter with filtered cards
//                        adapter.setCards(filteredCards);
//                        adapter.notifyDataSetChanged();
//
//                    } catch (Exception e) { // Catch various exceptions
//                        e.printStackTrace();
//                        Toast.makeText(this, "Erro ao processar a resposta dos cards.", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                error -> {
//                    Toast.makeText(this, "Erro ao buscar os cards. Verifique a conexão de rede.", Toast.LENGTH_SHORT).show();
//                }
//        );

        // Adiciona a requisição à fila de requisições
//        requestQueue.add(requestBuscarCards);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.deck_menu, menu);
        return true;
    }

    public void mostrarTexto() {
        Toast.makeText(getApplicationContext(), "Card adicionado!!!", Toast.LENGTH_LONG).show();
    }

    public void sair(MenuItem item) {
        finishAffinity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemVoltar) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.itemAdicionar) {
            Intent intent = new Intent(this, CriarCardTela.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
