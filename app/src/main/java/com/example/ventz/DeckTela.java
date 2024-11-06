package com.example.ventz;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;
import com.example.ventz.model.Card;
import com.example.ventz.model.CardAdapterFront;
import java.util.ArrayList;
import java.util.List;

public class DeckTela extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_deck_tela);

        ListView listViewCard = findViewById(R.id.listViewCard);

        // Mock data for testing
        List<Card> cardsList = new ArrayList<>();
        cardsList.add(new Card(1, "No céu tem pão?", "E morreu", 1));
        cardsList.add(new Card(2, "O céu tá preto?", "Celta preto", 1));

        // 

        // Set up the adapter with mock data
        CardAdapterFront adapter = new CardAdapterFront(this, cardsList);
        listViewCard.setAdapter(adapter);
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
//            mostrarTexto();
            Intent intent = new Intent(this, CriarCardTela.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
