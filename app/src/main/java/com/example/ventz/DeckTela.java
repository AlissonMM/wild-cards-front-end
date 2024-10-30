package com.example.ventz;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ventz.model.Card;
import com.example.ventz.model.CardAdapterFront;

import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class DeckTela extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deck_tela);


        ListView listViewCard = findViewById(R.id.listViewCard);

        Button btnSair = findViewById(R.id.btnDeckVoltar);
        // Add a on click event to this button to return to the previous screen
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


         // Mock data for testing
                List<Card> mockCard = new ArrayList<>();
//        int idCard, String pergunta, String resposta, int idDeckFk, String acertou
                mockCard.add(new Card(1, "No céu tem pão?", "E morreu", 1));
                mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));
        mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));
        mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));
        mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));
        mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));
        mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));
        mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));
        mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));
        mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));
        mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));
        mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));
        mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));
        mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));
        mockCard.add(new Card(2, "O céu tá preto?", "celta preto", 1));

                // Set up the adapter with mock data
                CardAdapterFront adapter = new CardAdapterFront(this, mockCard);
                listViewCard.setAdapter(adapter);






    }
}