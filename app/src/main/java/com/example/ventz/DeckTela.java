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
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckTela extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_deck_tela);

        TextView qttCards = findViewById(R.id.qttCards);
        TextView labelNomeDeck = findViewById(R.id.labelNomeDeck);


        ListView listViewCard = findViewById(R.id.listViewCard);

        // Lista de cards para exibição (inicialmente vazia)
        List<Card> cardsList = new ArrayList<>();







        // Configurar o adaptador e associá-lo ao ListView
        CardAdapterFront adapter = new CardAdapterFront(this, cardsList);
        listViewCard.setAdapter(adapter);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String urlBuscarCards = Dados.getInstance().getUrl() + "/cards/buscarTodos";

        // Requisição para buscar os cards
        StringRequest requestBuscarCards = new StringRequest(
                Request.Method.GET,
                urlBuscarCards,
                response -> {
                    try {
                        // Log para ver a resposta da API
                        Log.d("API Response", response);

                        // Regex para capturar todos os objetos Card no formato 'Card{...}'
                        Pattern pattern = Pattern.compile("Card\\{([^}]+)\\}");
                        Matcher matcher = pattern.matcher(response);

                        while (matcher.find()) {
                            String cardString = matcher.group(1);

                            // Extrair o 'idDeckFk' e o 'nome' do card
                            int idDeckFk = extractIdDeckFk(cardString);
                            String perguntaCard = extractPerguntaCard(cardString);

                            // Verificar se o card pertence ao deck atual
                            if (idDeckFk == Dados.getInstance().getIdDeckAtual()) {
                                // Adicionar o card à lista filtrada
                                cardsList.add(new Card(perguntaCard, "", idDeckFk));  // Resposta vazia por enquanto
                            }

                            // quantity of total items from list view
                        }

                        // Atualizar o adaptador com os cards filtrados

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Erro ao processar a resposta dos cards.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "Erro ao buscar os cards. Verifique a conexão de rede.", Toast.LENGTH_SHORT).show();
                }
        );

        // Adiciona a requisição à fila de requisições
        requestQueue.add(requestBuscarCards);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            qttCards.setText("Quantidade de Cards: " + String.valueOf(cardsList.size()));

        }, 3000);
        labelNomeDeck.setText(Dados.getInstance().getNomeDeckAtual());

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
            finish();
            return true;
        }
        else if (item.getItemId() == R.id.itemPraticar) {
            Intent intent = new Intent(this, TreinarTela.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Método para extrair o 'idDeckFk' de uma string de card
    private int extractIdDeckFk(String cardString) {
        int idDeckFk = -1;
        // Regex para capturar o 'idDeckFk' que está no formato 'idDeckFk=Deck{idDeck=xx,...}'
        Pattern pattern = Pattern.compile("idDeckFk=Deck\\{idDeck=(\\d+),");
        Matcher matcher = pattern.matcher(cardString);
        if (matcher.find()) {
            idDeckFk = Integer.parseInt(matcher.group(1)); // Captura o 'idDeckFk'
        }
        return idDeckFk;
    }

    // Método para extrair o 'pergunta' do card
    private String extractPerguntaCard(String cardString) {
        String perguntaCard = "";
        // Regex para capturar a 'pergunta' que está no formato 'pergunta='alguma coisa''
        Pattern pattern = Pattern.compile("pergunta='([^']+)'");
        Matcher matcher = pattern.matcher(cardString);
        if (matcher.find()) {
            perguntaCard = matcher.group(1); // Captura a 'pergunta' do card
        }
        return perguntaCard;
    }
}
