package com.example.ventz;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventz.model.Card;
import com.example.ventz.model.Dados;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreinarTela extends AppCompatActivity {

    private CardView cardFront, cardBack;
    private Boolean isFront = true;
    private List<Card> cardsList = new ArrayList<>();
    private List<Integer> cardIdsList = new ArrayList<>();
    private TextView textBack, textFront;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_treinar_tela);

        cardFront = findViewById(R.id.cardFront);
        cardBack = findViewById(R.id.cardBack);
        textBack = findViewById(R.id.textBack);
        textFront = findViewById(R.id.textFront);

        Button btnAcertou = findViewById(R.id.btnAcertou);
        Button btnErrou = findViewById(R.id.btnErrou);

        RelativeLayout cardMain = findViewById(R.id.cardMain);

        // Configura o evento de clique para flip do cartão
        cardMain.setOnClickListener(v -> flipCardAnim());

        // Configura os eventos de clique para os botões "Acertou" e "Errou"
        btnAcertou.setOnClickListener(v -> atualizarCardAleatorio());
        btnErrou.setOnClickListener(v -> atualizarCardAleatorio());

        // Requisição para buscar os cards
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String urlBuscarCards = Dados.getInstance().getUrl() + "/cards/buscarTodos";

        StringRequest requestBuscarCards = new StringRequest(
                Request.Method.GET,
                urlBuscarCards,
                response -> {
                    try {
                        Log.d("API Response", response);

                        Pattern pattern = Pattern.compile("Card\\{([^}]+)\\}");
                        Matcher matcher = pattern.matcher(response);

                        while (matcher.find()) {
                            String cardString = matcher.group(1);

                            int idDeckFk = extractIdDeckFk(cardString);
                            String perguntaCard = extractPerguntaCard(cardString);
                            String respostaCard = extractRespostaCard(cardString);
                            int idCard = extractIdCard(cardString);

                            if (idDeckFk == Dados.getInstance().getIdDeckAtual()) {
                                cardsList.add(new Card(idCard, perguntaCard, respostaCard, idDeckFk));
                                cardIdsList.add(idCard);
                            }
                        }

                        // Inicializa o primeiro card ao carregar os dados
                        if (!cardIdsList.isEmpty()) {
                            atualizarCardAleatorio();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Erro ao processar a resposta dos cards.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "Erro ao buscar os cards. Verifique a conexão de rede.", Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(requestBuscarCards);
    }

    private void atualizarCardAleatorio() {
        if (!cardIdsList.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(cardIdsList.size());
            int randomId = cardIdsList.get(randomIndex);

            Log.d("Random Card ID", "ID Aleatório da Carta: " + randomId);

            Card randomCard = null;
            for (Card card : cardsList) {
                if (card.getIdCard() == randomId) {
                    randomCard = card;
                    break;
                }
            }

            if (randomCard != null) {
                Log.d("Random Card Pergunta", randomCard.getPergunta());
                Log.d("Random Card Resposta", randomCard.getResposta());

                textFront.setText(randomCard.getPergunta());
                textBack.setText(randomCard.getResposta());
            }
        }
    }

    private void flipCardAnim() {
        AnimatorSet setOut = (AnimatorSet) AnimatorInflater.loadAnimator(TreinarTela.this, R.animator.card_flip_out);
        AnimatorSet setIn = (AnimatorSet) AnimatorInflater.loadAnimator(TreinarTela.this, R.animator.card_flip_in);

        setOut.setTarget(isFront ? cardFront : cardBack);
        setIn.setTarget(isFront ? cardBack : cardFront);

        setOut.start();
        setIn.start();

        isFront = !isFront;
    }

    private int extractIdDeckFk(String cardString) {
        int idDeckFk = -1;
        Pattern pattern = Pattern.compile("idDeckFk=Deck\\{idDeck=(\\d+),");
        Matcher matcher = pattern.matcher(cardString);
        if (matcher.find()) {
            idDeckFk = Integer.parseInt(matcher.group(1));
            Log.d("Extracted ID", "idDeckFk extraído: " + idDeckFk);
        }
        return idDeckFk;
    }

    private String extractPerguntaCard(String cardString) {
        String perguntaCard = "";
        Pattern pattern = Pattern.compile("pergunta='([^']+)'");
        Matcher matcher = pattern.matcher(cardString);
        if (matcher.find()) {
            perguntaCard = matcher.group(1);
            Log.d("Extracted Pergunta", "Pergunta extraída: " + perguntaCard);
        }
        return perguntaCard;
    }

    private String extractRespostaCard(String cardString) {
        String respostaCard = "";
        Pattern pattern = Pattern.compile("resposta='([^']+)'");
        Matcher matcher = pattern.matcher(cardString);
        if (matcher.find()) {
            respostaCard = matcher.group(1);
            Log.d("Extracted Resposta", "Resposta extraída: " + respostaCard);
        }
        return respostaCard;
    }

    private int extractIdCard(String cardString) {
        int idCard = -1;
        Pattern pattern = Pattern.compile("idCard=(\\d+)");
        Matcher matcher = pattern.matcher(cardString);
        if (matcher.find()) {
            idCard = Integer.parseInt(matcher.group(1));
            Log.d("Extracted ID", "idCard extraído: " + idCard);
        }
        return idCard;
    }
}
